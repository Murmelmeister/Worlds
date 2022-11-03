package de.murmelmeister.worlds.api.config;

import de.murmelmeister.worlds.InitPlugin;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WorldManager {

    private InitPlugin init;

    private File folder;
    private File file;
    private YamlConfiguration config;

    private List<String> worldList;
    private World world;

    public WorldManager(InitPlugin init) {
        setInit(init);
        setWorldList(new ArrayList<>());
        createConfig();
        saveConfig();
    }

    public void createConfig() {
        String fileName = "worlds.yml";
        setFolder(new File(String.format("plugins//%s//", getInit().getInstance().getPluginName())));
        if (!(getFolder().exists())) {
            boolean aBoolean = getFolder().mkdir();
            if (!(aBoolean))
                getInit().getInstance().getSLF4JLogger().warn("The plugin can not create a second folder.");
        }
        setFile(new File(getFolder(), fileName));
        if (!(getFile().exists())) {
            try {
                boolean aBoolean = getFile().createNewFile();
                if (!(aBoolean))
                    getInit().getInstance().getSLF4JLogger().warn(String.format("The plugin can not create the file '%s'.", fileName));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        setConfig(YamlConfiguration.loadConfiguration(getFile()));
    }

    public void saveConfig() {
        try {
            getConfig().save(getFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadDefaultWorlds() {
        for (World worlds : getInit().getInstance().getServer().getWorlds()) {
            if (getConfigPath("Worlds.World." + worlds.getName()) != null) return;
            setWorld(worlds);
            setWorldOptions(worlds.getName());
            this.getWorldList().add(getWorld().getName());
            updateWorldList(this.getWorldList());
            saveConfig();
        }
    }

    public void createWorld(String worldName) {
        if (getInit().getInstance().getServer().getWorld(worldName) != null)
            return;
        WorldCreator worldCreator = new WorldCreator(worldName);
        setWorld(worldCreator.createWorld());
        setWorldOptions(worldName);
        saveConfig();
    }

    public void createWorld(String worldName, World.Environment environment) {
        if (getInit().getInstance().getServer().getWorld(worldName) != null)
            loadWorld(worldName, environment);
        WorldCreator worldCreator = new WorldCreator(worldName);
        worldCreator.environment(environment);
        setWorld(worldCreator.createWorld()); // TODO: If environment is "normal" then send the console a chunks error <- but I don't know why
        setWorldOptions(worldName);
        this.getWorldList().add(getWorld().getName());
        updateWorldList(this.getWorldList());
        saveConfig();
    }

    public void deleteWorld(String worldName) {
        setWorld(getInit().getInstance().getServer().getWorld(worldName));
        File worldFile = getWorld().getWorldFolder();
        getInit().getInstance().getServer().unloadWorld(getWorld(), false);
        deleteFile(worldFile);
        this.getWorldList().remove(getWorld().getName());
        updateWorldList(this.getWorldList());
        setConfigValue("Worlds.World." + worldName, null);
        this.saveConfig();
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
                    if (!(aBoolean)) getInit().getInstance().getSLF4JLogger().warn("Could not delete the same files.");
                }
            }
        }
        return file.delete();
    }

    public void importWorld(String worldName) {
        if (getInit().getInstance().getServer().getWorld(worldName) == null)
            createWorld(worldName);
        setWorld(getInit().getInstance().getServer().getWorld(worldName));
        setWorldOptions(worldName);
        if (!(this.getWorldList().contains(getWorld().getName()))) {
            this.getWorldList().add(getWorld().getName());
            updateWorldList(this.getWorldList());
        }
        saveConfig();
    }

    public void loadWorld(String worldName, World.Environment environment) {
        if (getInit().getInstance().getServer().getWorld(worldName) == null)
            createWorld(worldName, environment);
        setWorld(getInit().getInstance().getServer().getWorld(worldName));
        setWorldOptions(worldName);
        if (!(this.getWorldList().contains(getWorld().getName())))
            this.getWorldList().add(getWorld().getName());
        saveConfig();
    }

    public void unloadWorld(String worldName, World.Environment environment) {
        if (getInit().getInstance().getServer().getWorld(worldName) == null)
            createWorld(worldName, environment);
        setWorld(getInit().getInstance().getServer().getWorld(worldName));
        setWorldOptions(worldName);
        this.getWorldList().remove(getConfig().getName());
    }

    private void setWorldOptions(String worldName) {
        String path = "Worlds.World." + worldName;
        setWorldOption(path + ".Name", getWorld().getName());
        setWorldOption(path + ".Environment", getWorld().getEnvironment().name());
        setWorldOption(path + ".AllowAnimals", getWorld().getAllowAnimals());
        setWorldOption(path + ".AllowMonsters", getWorld().getAllowMonsters());
        setWorldOption(path + ".Difficulty", getWorld().getDifficulty().name());
        setWorldOption(path + ".PVP", getWorld().getPVP());
        for (GameRule<?> gameRule : GameRule.values())
            setWorldOption(path + ".GameRules." + gameRule.getName(), getWorld().getGameRuleValue(gameRule));
        saveConfig();
    }

    private void setWorldOption(String path, Object value) {
        if (getConfigPath(path) == null) setConfigValue(path, value);
    }

    public void loadWorlds() {
        for (String worldName : getConfig().getStringList("Worlds.List"))
            loadWorld(worldName, World.Environment.NORMAL);
    }

    public void unloadWorlds() {
        for (String worldName : getConfig().getStringList("Worlds.List"))
            unloadWorld(worldName, World.Environment.NORMAL);
    }

    private void updateWorldList(List<String> worldList) {
        String path = "Worlds.List";
        setConfigValue(path, worldList);
    }

    public void setConfigValue(String path, Object value) {
        getConfig().set(path, value);
    }

    public String getConfigPath(String path) {
        return getConfig().getString(path);
    }

    public InitPlugin getInit() {
        return init;
    }

    public void setInit(InitPlugin init) {
        this.init = init;
    }

    public File getFolder() {
        return folder;
    }

    public void setFolder(File folder) {
        this.folder = folder;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public YamlConfiguration getConfig() {
        return config;
    }

    public void setConfig(YamlConfiguration config) {
        this.config = config;
    }

    public List<String> getWorldList() {
        return worldList;
    }

    public void setWorldList(List<String> worldList) {
        this.worldList = worldList;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }
}
