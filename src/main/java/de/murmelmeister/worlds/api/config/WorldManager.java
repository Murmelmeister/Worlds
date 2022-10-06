package de.murmelmeister.worlds.api.config;

import de.murmelmeister.worlds.Worlds;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.YamlConfiguration;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WorldManager {

    private final File folder = new File("plugins//Worlds//");
    private File file;
    private YamlConfiguration config;

    private final Logger logger = Worlds.getInstance().getSLF4JLogger();
    private final Worlds instance = Worlds.getInstance();

    private List<String> worldList; // TODO: Set the default world in this list
    private World world;

    public WorldManager() {
        createConfig();
        saveConfig();
        setWorldList(new ArrayList<>());
    }

    public void createConfig() {
        if (!(getFolder().exists())) {
            boolean aBoolean = getFolder().mkdir();
            if (!(aBoolean)) logger.warn("Could not create the same folder.");
        }

        setFile(new File(getFolder(), "worlds.yml"));

        if (!(file.exists())) {
            try {
                boolean aBoolean = file.createNewFile();
                if (!(aBoolean)) logger.warn("Could not create the same worlds.yml.");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        setConfig(YamlConfiguration.loadConfiguration(file));
    }

    public void saveConfig() {
        try {
            getConfig().save(getFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void createWorld(String worldName) {
        createConfig();
        if (this.instance.getServer().getWorld(worldName) != null)
            return; // TODO: Useless?
        WorldCreator worldCreator = new WorldCreator(worldName);
        world = this.instance.getServer().createWorld(worldCreator);
        setWorldOptions(worldName);
        saveConfig();
    }

    public void createWorld(String worldName, World.Environment environment) {
        createConfig();

        if (this.instance.getServer().getWorld(worldName) != null)
            loadWorld(worldName, environment);

        WorldCreator worldCreator = new WorldCreator(worldName);
        worldCreator.environment(environment);

        world = this.instance.getServer().createWorld(worldCreator); // Error?

        setWorldOptions(worldName);

        this.getConfig().set("Worlds.List", this.getWorldList());
        this.getWorldList().add(world.getName());

        saveConfig();
    }

    public void deleteWorld(String worldName) {
        World world = this.instance.getServer().getWorld(worldName);
        if (world == null) return;
        File worldFile = world.getWorldFolder();
        this.instance.getServer().unloadWorld(world, false);
        this.instance.getServer().getWorlds().remove(world); // TODO: Useless?
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
                    if (!(aBoolean)) logger.warn("Could not delete the same files.");
                }
            }
        }
        return file.delete();
    }

    public void importWorld(String worldName) {
        if (this.instance.getServer().getWorld(worldName) == null)
            createWorld(worldName);

        world = this.instance.getServer().getWorld(worldName);

        if (!(this.getConfig().contains("Worlds.World." + worldName)))
            setWorldOptions(worldName);

        if (!(this.getWorldList().contains(world.getName()))) {
            this.getConfig().set("Worlds.List", this.getWorldList());
            this.getWorldList().add(world.getName());
        }

        saveConfig();
    }

    public void loadWorld(String worldName, World.Environment environment) {
        if (this.instance.getServer().getWorld(worldName) == null)
            createWorld(worldName, environment);

        world = this.instance.getServer().getWorld(worldName);

        if (!(this.getConfig().contains("Worlds.World." + worldName)))
            setWorldOptions(worldName);

        if (!(this.getWorldList().contains(world.getName())))
            this.getWorldList().add(world.getName());

        saveConfig();
    }

    public void unloadWorld(String worldName, World.Environment environment) {
        if (this.instance.getServer().getWorld(worldName) == null)
            createWorld(worldName, environment);

        world = this.instance.getServer().getWorld(worldName);

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

    public File getFolder() {
        return folder;
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
