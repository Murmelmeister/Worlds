package de.murmelmeister.worlds.api.config;

import de.murmelmeister.worlds.Worlds;
import de.murmelmeister.worlds.utils.FileUtil;
import org.bukkit.GameRule;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.YamlConfiguration;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class WorldManager {
    private final Logger logger;
    private final Server server;

    private File file;
    private YamlConfiguration config;
    private List<String> worldList;

    public WorldManager(Logger logger, Server server) {
        this.logger = logger;
        this.server = server;
        createFile();
    }

    public void createFile() {
        this.file = FileUtil.createFile(logger, Worlds.getMainPath(), "worlds.yml");
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public void saveFile() {
        try {
            this.config.save(file);
        } catch (IOException e) {
            logger.error("Error while saving config file", e);
        }
    }

    public void loadDefaultWorlds() {
        for (World worlds : server.getWorlds()) {
            if (config.getString("Worlds." + worlds.getName()) != null) return;
            setWorldOptions(worlds.getName());
            worldList = getWorldList();
            worldList.add(worlds.getName());
            config.set("WorldList", worldList);
            saveFile();
        }
    }

    private void createWorld(String worldName) {
        if (server.getWorld(worldName) != null)
            return;
        WorldCreator worldCreator = new WorldCreator(worldName);
        worldCreator.createWorld();
        setWorldOptions(worldName);
        saveFile();
    }

    public void createWorld(String worldName, World.Environment environment) {
        if (server.getWorld(worldName) != null)
            loadWorld(worldName);
        WorldCreator worldCreator = new WorldCreator(worldName);
        worldCreator.environment(environment);
        worldCreator.createWorld(); // TODO: If environment is "normal" then send the console a chunks error <- but I don't know why
        setWorldOptions(worldName);
        worldList = getWorldList();
        worldList.add(worldName);
        config.set("WorldList", worldList);
        saveFile();
    }

    public void deleteWorld(String worldName) {
        World world = server.getWorld(worldName);
        if (world == null) return;
        File worldFile = world.getWorldFolder();
        server.unloadWorld(world, false);
        boolean success = deleteFile(worldFile);
        if (!success) logger.error("Could not delete the world folder.");
        worldList.remove(world.getName());
        config.set("WorldList", worldList);
        config.set("Worlds." + worldName, null);
        saveFile();
    }

    private boolean deleteFile(File file) {
        if (!file.exists()) return false;

        File[] files = file.listFiles();
        if (files != null) {
            for (File value : files) {
                if (value.isDirectory()) deleteFile(value);
                else if (!value.delete()) logger.error("Could not delete file: {}", value.getAbsolutePath());
            }
        }
        return file.delete();
    }

    public void importWorld(String worldName) {
        WorldCreator worldCreator = new WorldCreator(worldName);
        server.createWorld(worldCreator);
        setWorldOptions(worldName);
        this.worldList = getWorldList();
        if (!(worldList.contains(worldName))) {
            worldList.add(worldName);
            config.set("WorldList", worldList);
        }
        saveFile();
    }

    public void loadWorld(String worldName) {
        if (server.getWorld(worldName) == null) return;
        setWorldOptions(worldName);
        worldList = getWorldList();
        if (!(worldList.contains(worldName)))
            worldList.add(worldName);
        saveFile();
    }

    public void unloadWorld(String worldName) {
        if (server.getWorld(worldName) == null) return;
        setWorldOptions(worldName);
        worldList.remove(worldName);
        saveFile();
    }

    private void setWorldOptions(String worldName) {
        World world = server.getWorld(worldName);
        if (world == null) return;
        String path = "Worlds." + worldName;
        setWorldOption(path + ".Name", world.getName());
        setWorldOption(path + ".Environment", world.getEnvironment().name());
        setWorldOption(path + ".AllowAnimals", world.getAllowAnimals());
        setWorldOption(path + ".AllowMonsters", world.getAllowMonsters());
        setWorldOption(path + ".Difficulty", world.getDifficulty().name());
        setWorldOption(path + ".PvP", world.getPVP());
        for (GameRule<?> gameRule : GameRule.values())
            setWorldOption(path + ".GameRules." + gameRule.getName(), world.getGameRuleValue(gameRule));
        saveFile();
    }

    private void setWorldOption(String path, Object value) {
        if (config.getString(path) == null) config.set(path, value);
    }

    public void loadWorlds() {
        for (String worldName : config.getStringList("WorldList"))
            loadWorld(worldName);
    }

    public void unloadWorlds() {
        for (String worldName : config.getStringList("WorldList"))
            unloadWorld(worldName);
    }

    public List<String> getWorldList() {
        createFile();
        List<String> worldList = new ArrayList<>();
        if (config.contains("WorldList")) worldList = config.getStringList("WorldList");
        return worldList;
    }

    public void set(String path, Object value) {
        config.set(path, value);
        saveFile();
    }

    public String getSting(String path) {
        return config.getString(path);
    }
}
