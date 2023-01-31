package de.murmelmeister.worlds.configs;

import de.murmelmeister.worlds.Main;
import de.murmelmeister.worlds.utils.InfoUtil;
import de.murmelmeister.worlds.utils.configs.Configs;
import org.bukkit.configuration.file.YamlConfiguration;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;

public class DefaultConfig {
    private final Logger logger;

    private File file;
    private YamlConfiguration config;

    public DefaultConfig(Main main) {
        this.logger = main.getInstance().getSLF4JLogger();
    }

    public void register() {
        create();
        load();
        save();
    }

    public void create() {
        String pathName = String.format("plugins//%s//", InfoUtil.pluginName());
        String fileName = "Config.yml";
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

    public void load() {
        for (Configs configs : Configs.values())
            if (getObject(configs) == null) set(configs);
            else getObject(configs);
    }

    public void set(Configs configs) {
        config.set(configs.getPath(), configs.getValue());
    }

    public Object getObject(Configs configs) {
        return config.get(configs.getPath());
    }

    public boolean getBoolean(Configs configs) {
        return config.getBoolean(configs.getPath());
    }

    public String getString(Configs configs) {
        return config.getString(configs.getPath());
    }
}
