package de.murmelmeister.worlds.files;

import de.murmelmeister.worlds.Worlds;
import de.murmelmeister.worlds.utils.FileUtil;
import de.murmelmeister.worlds.utils.configs.Configs;
import org.bukkit.configuration.file.YamlConfiguration;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;

public final class ConfigFile {
    private final Logger logger;
    private File file;
    private YamlConfiguration config;

    public ConfigFile(final Logger logger) {
        this.logger = logger;
        loadFile();
    }

    public void reloadFile() {
        createFile();
    }

    private void createFile() {
        this.file = FileUtil.createFile(logger, Worlds.getMainPath(), "config.yml");
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    private void loadFile() {
        createFile();
        for (Configs configs : Configs.VALUES) if (get(configs) == null) set(configs);
        saveFile();
    }

    private void saveFile() {
        try {
            this.config.save(file);
        } catch (IOException e) {
            logger.error("Error while saving config file", e);
        }
    }

    public void set(Configs configs) {
        config.set(configs.getPath(), configs.getValue());
    }

    public Object get(Configs configs) {
        return config.get(configs.getPath());
    }

    public boolean getBoolean(Configs configs) {
        return config.getBoolean(configs.getPath());
    }
}
