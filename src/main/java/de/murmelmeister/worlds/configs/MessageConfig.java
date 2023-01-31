package de.murmelmeister.worlds.configs;

import de.murmelmeister.worlds.Main;
import de.murmelmeister.worlds.utils.HexColor;
import de.murmelmeister.worlds.utils.InfoUtil;
import de.murmelmeister.worlds.utils.configs.Messages;
import org.bukkit.configuration.file.YamlConfiguration;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;

public class MessageConfig {
    private final Logger logger;

    private File file;
    private YamlConfiguration config;

    public MessageConfig(Main main) {
        this.logger = main.getInstance().getSLF4JLogger();
    }

    public void register() {
        create();
        load();
        save();
    }

    public void create() {
        String pathName = String.format("plugins//%s//", InfoUtil.pluginName());
        String fileName = "Message.yml";
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
        for (Messages messages : Messages.values())
            if (getString(messages) == null) set(messages);
            else getString(messages);
    }

    public void set(Messages messages) {
        config.set(messages.getPath(), messages.getValue());
    }

    public String getString(Messages messages) {
        return config.getString(HexColor.format(messages.getPath()));
    }

    public String prefix() {
        return getString(Messages.PREFIX);
    }
}
