package de.murmelmeister.worlds.files;

import de.murmelmeister.worlds.Worlds;
import de.murmelmeister.worlds.utils.FileUtil;
import de.murmelmeister.worlds.utils.configs.Messages;
import org.bukkit.configuration.file.YamlConfiguration;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;

public final class MessageFile {
    private final Logger logger;
    private File file;
    private YamlConfiguration config;

    public MessageFile(final Logger logger) {
        this.logger = logger;
        loadFile();
    }

    public void reloadFile() {
        createFile();
    }

    private void createFile() {
        this.file = FileUtil.createFile(logger, Worlds.getMainPath(), "message.yml");
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    private void loadFile() {
        createFile();
        for (Messages messages : Messages.VALUES) if (getMessage(messages) == null) set(messages);
        saveFile();
    }

    private void saveFile() {
        try {
            this.config.save(file);
        } catch (IOException e) {
            logger.error("Error while saving config file", e);
        }
    }

    private void set(Messages messages) {
        config.set(messages.getPath(), messages.getMessage());
    }

    public String getMessage(Messages messages) {
        return config.getString(messages.getPath());
    }

    public String getPrefix() {
        return getMessage(Messages.PREFIX);
    }
}
