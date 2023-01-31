package de.murmelmeister.worlds.api;

import de.murmelmeister.worlds.Main;
import de.murmelmeister.worlds.utils.InfoUtil;
import org.bukkit.GameRule;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.YamlConfiguration;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class WorldManager {
    private final Logger logger;
    private final Server server;
    private final WorldID worldID;

    private File file;
    private YamlConfiguration config;

    public WorldManager(Main main) {
        this.logger = main.getInstance().getSLF4JLogger();
        this.server = main.getInstance().getServer();
        this.worldID = main.getWorldID();
    }

    public void create(String worldName) {
        String pathName = String.format("plugins//%s//Worlds//", InfoUtil.pluginName());
        String fileName = worldName + ".yml";
        this.file = new File(pathName, fileName);
        InfoUtil.fileExists(logger, file, fileName);
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadDefaultWorlds() {
        server.getWorlds().forEach(this::worldOptions);
        server.getWorlds().forEach(world -> worldID.addListID(world.getName()));
    }

    public void createWorld(String worldName, World.Environment environment) {
        World world = new WorldCreator(worldName).environment(environment).createWorld();
        if (world == null) return;
        worldOptions(world);
        worldID.addListID(worldName);
    }

    public void deleteWorld(String worldName) {
        World world = server.getWorld(worldName);
        if (world == null) return;
        File worldFile = world.getWorldFolder();
        server.unloadWorld(world, false);
        boolean aBoolean = deleteFileWorld(worldFile);
        if (!(aBoolean)) logger.error(String.format("The plugin can not delete the file '%s'", worldFile.getName()));
        boolean bBoolean = file.delete();
        if (!(bBoolean)) logger.error(String.format("The plugin can not delete the file '%s'", file.getName()));
        worldID.removeListID(worldName);
    }

    private boolean deleteFileWorld(File file) {
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files == null) return false;
            for (File value : files) {
                if (value.isDirectory()) {
                    deleteFileWorld(value);
                } else {
                    boolean aBoolean = value.delete();
                    if (!(aBoolean)) logger.warn("Could not delete the same files.");
                }
            }
        }
        return file.delete();
    }

    public void importWorld(String worldName) {
        // TODO: Fix import worlds
        /*World world = server.getWorld(worldName);
        if (world == null) createWorld(worldName, World.Environment.NORMAL);
        worldOptions(world);*/
        worldOptions(server.getWorld(worldName));
        worldID.addListID(worldName);
    }

    private void loadWorld(String worldName) {
        World world = server.getWorld(worldName);
        if (world == null) return;
        worldOptions(world);
        worldID.addID(worldName);
    }

    private void unloadWorld(String worldName) {
        worldID.removeID(worldName); // Error
    }

    public void loadWorlds() {
        worldID.getListID().forEach(this::loadWorld);
    }

    public void unloadWorlds() {
        for (String world : worldID.getListID()) unloadWorld(world);
    }

    private void worldOptions(World world) {
        create(world.getName());
        setOption("Name", world.getName());
        setOption("Environment", world.getEnvironment().name());
        setOption("AllowAnimals", world.getAllowAnimals());
        setOption("AllowMonsters", world.getAllowMonsters());
        setOption("Difficulty", world.getDifficulty().name());
        setOption("PVP", world.getPVP());
        for (GameRule<?> gameRule : GameRule.values())
            setOption("GameRules." + gameRule.getName(), world.getGameRuleValue(gameRule));
        save();
    }

    private void setOption(String path, Object value) {
        if (getString(path) == null) set(path, value);
        else getString(path);
    }

    public void set(String path, Object value) {
        config.set(path, value);
    }

    public String getString(String path) {
        return config.getString(path);
    }

    public WorldID getWorldID() {
        return worldID;
    }

    /*
    private final Logger logger;
    private final Server server;

    private File file;
    private YamlConfiguration config;

    private final List<String> worldList = new ArrayList<>();

    public WorldManager(Main main) {
        this.logger = main.getInstance().getSLF4JLogger();
        this.server = main.getInstance().getServer();
    }

    public void register() {
        create();
        save();
    }

    public void create() {
        String pathName = String.format("plugins//%s//", InfoUtil.pluginName());
        String fileName = "worlds.yml";
        this.file = new File(pathName, fileName);
        if (!(file.getParentFile().exists())) {
            boolean aBoolean = file.getParentFile().mkdir();
            if (!(aBoolean)) logger.warn("The plugin can not create a second folder.");
        }
        if (!(file.exists())) {
            try {
                boolean aBoolean = file.createNewFile();
                if (!(aBoolean)) logger.warn(String.format("The plugin can not create the file '%s'.", fileName));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadDefaultWorlds() {
        for (World worlds : server.getWorlds()) {
            if (getString("Worlds.World." + worlds.getName()) != null) return;
            setWorldOptions(worlds);
            worldList.add(worlds.getName());
            updateWorldList(worldList);
            save();
        }
    }

    public void createWorld(World world, World.Environment environment) {
        // TODO: If environment is "normal" then send the console a chunks error <- but I don't know why

        WorldCreator.name(world.getName()).environment(environment).createWorld();
        setWorldOptions(world);
        worldList.add(world.getName());
        updateWorldList(worldList);
        save();
    }

    public void deleteWorld(World world) {
        File worldFile = world.getWorldFolder();
        server.unloadWorld(world, false);
        deleteFile(worldFile);
        worldList.remove(world.getName());
        updateWorldList(worldList);
        set("Worlds.World." + world.getName(), null);
        save();
    }

    private boolean deleteFile(File file) { // I don't know if it needs a boolean
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files == null) return false;
            // I don't know if it needs for-loop
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    deleteFile(files[i]);
                } else {
                    boolean aBoolean = files[i].delete();
                    if (!(aBoolean)) logger.warn("Could not delete the same files.");
                }
            }
        }
        return file.delete();
    }

    public void importWorld(World world) {
        if (server.getWorld(world.getName()) == null)
            new WorldCreator(world.getName()).createWorld();
        setWorldOptions(world);
        if (!(worldList.contains(world.getName()))) {
            worldList.add(world.getName());
            updateWorldList(worldList);
        }
        save();
    }

    public void loadWorld(String worldName, World.Environment environment) {
        World world = server.getWorld(worldName);
        if (world == null) createWorld(server.getWorld(worldName), environment);
        else {
            setWorldOptions(world);
            if (!(worldList.contains(world.getName())))
                worldList.add(world.getName());
            save();
        }
    }

    public void unloadWorld(World world, World.Environment environment) {
        if (server.getWorld(world.getName()) == null)
            createWorld(world, environment);
        setWorldOptions(world);
        worldList.remove(world.getName());
    }

    private void setWorldOptions(World world) {
        String path = "Worlds.World." + world.getName();
        worldOption(path + ".Name", world.getName());
        worldOption(path + ".Environment", world.getEnvironment().name());
        worldOption(path + ".AllowAnimals", world.getAllowAnimals());
        worldOption(path + ".AllowMonsters", world.getAllowMonsters());
        worldOption(path + ".Difficulty", world.getDifficulty().name());
        worldOption(path + ".PVP", world.getPVP());
        for (GameRule<?> gameRule : GameRule.values())
            worldOption(path + ".GameRules." + gameRule.getName(), world.getGameRuleValue(gameRule));
        save();
    }

    private void worldOption(String path, Object value) {
        if (getString(path) == null) set(path, value);
    }

    public void loadWorlds() {
        for (String worldName : getStringList("Worlds.List"))
            loadWorld(worldName, World.Environment.NORMAL);
    }

    public void unloadWorlds() {
        for (String worldName : getStringList("Worlds.List"))
            unloadWorld(worldName, World.Environment.NORMAL);
    }

    private void updateWorldList(List<String> worldList) {
        String path = "Worlds.List";
        set(path, worldList);
    }

    private void set(String path, Object value) {
        config.set(path, value);
    }

    private String getString(String path) {
        return config.getString(path);
    }

    private List<String> getStringList(String path) {
        return config.getStringList(path);
    }

    public YamlConfiguration getConfig() {
        return config;
    }

    public List<String> getWorldList() {
        return worldList;
    }*/
}
