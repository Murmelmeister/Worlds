package de.murmelmeister.worlds.api.config;

import de.murmelmeister.worlds.InitPlugin;
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

    private List<String> worldList; // TODO: Set the default world in this list
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

    public void createWorld(String worldName) {
        createConfig(); // TODO: Useless?
        if (getInit().getInstance().getServer().getWorld(worldName) != null)
            return; // TODO: Useless?
        WorldCreator worldCreator = new WorldCreator(worldName);
        world = getInit().getInstance().getServer().createWorld(worldCreator);
        setWorldOptions(worldName);
        saveConfig();
    }

    public void createWorld(String worldName, World.Environment environment) {
        createConfig(); // TODO: Useless?

        if (getInit().getInstance().getServer().getWorld(worldName) != null)
            loadWorld(worldName, environment);

        WorldCreator worldCreator = new WorldCreator(worldName);
        worldCreator.environment(environment);

        world = getInit().getInstance().getServer().createWorld(worldCreator); // TODO: Error?

        setWorldOptions(worldName);

        this.getConfig().set("Worlds.List", this.getWorldList());
        this.getWorldList().add(world.getName());

        saveConfig();
    }

    public void deleteWorld(String worldName) {
        World world = getInit().getInstance().getServer().getWorld(worldName);
        if (world == null) return;
        File worldFile = world.getWorldFolder();
        getInit().getInstance().getServer().unloadWorld(world, false);
        getInit().getInstance().getServer().getWorlds().remove(world); // TODO: Useless?
        deleteFile(worldFile);
        this.getWorldList().remove(world.getName());
        this.getConfig().set("Worlds.List", this.getWorldList());
        this.getConfig().set("Worlds.World." + worldName, null);
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

        world = getInit().getInstance().getServer().getWorld(worldName);

        if (!(this.getConfig().contains("Worlds.World." + worldName)))
            setWorldOptions(worldName);

        if (!(this.getWorldList().contains(world.getName()))) {
            this.getConfig().set("Worlds.List", this.getWorldList());
            this.getWorldList().add(world.getName());
        }

        saveConfig();
    }

    public void loadWorld(String worldName, World.Environment environment) {
        if (getInit().getInstance().getServer().getWorld(worldName) == null)
            createWorld(worldName, environment);

        world = getInit().getInstance().getServer().getWorld(worldName);

        if (!(this.getConfig().contains("Worlds.World." + worldName)))
            setWorldOptions(worldName);

        if (!(this.getWorldList().contains(world.getName())))
            this.getWorldList().add(world.getName());

        saveConfig();
    }

    public void unloadWorld(String worldName, World.Environment environment) {
        if (getInit().getInstance().getServer().getWorld(worldName) == null)
            createWorld(worldName, environment);

        world = getInit().getInstance().getServer().getWorld(worldName);

        if (!(this.getConfig().contains("Worlds.World." + worldName)))
            setWorldOptions(worldName);

        this.getWorldList().remove(world.getName());
    }

    private void setWorldOptions(String worldName) {
        this.getConfig().set("Worlds.World." + worldName + ".Name", world.getName());
        this.getConfig().set("Worlds.World." + worldName + ".Environment", world.getEnvironment().name());
        this.getConfig().set("Worlds.World." + worldName + ".AllowAnimals", world.getAllowAnimals());
        this.getConfig().set("Worlds.World." + worldName + ".AllowMonsters", world.getAllowMonsters());
        this.getConfig().set("Worlds.World." + worldName + ".Difficulty", world.getDifficulty().name());
        this.getConfig().set("Worlds.World." + worldName + ".PVP", world.getPVP()); // TODO: More options
        saveConfig();
    }

    public void loadWorlds() {
        for (String worldName : getConfig().getStringList("Worlds.List")) {
            loadWorld(worldName, World.Environment.NORMAL);
        }
    }

    public void unloadWorlds() {
        for (String worldName : getConfig().getStringList("Worlds.List")) {
            unloadWorld(worldName, World.Environment.NORMAL);
        }
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
}
