package de.murmelmeister.worlds.files;

import de.murmelmeister.worlds.Worlds;
import de.murmelmeister.worlds.utils.FileUtil;
import de.murmelmeister.worlds.utils.configs.Permissions;
import org.bukkit.configuration.file.YamlConfiguration;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;

public final class PermissionFile {
    private final Logger logger;
    private File file;
    private YamlConfiguration config;

    public PermissionFile(final Logger logger) {
        this.logger = logger;
        loadFile();
    }

    public void reloadFile() {
        createFile();
    }

    private void createFile() {
        this.file = FileUtil.createFile(logger, Worlds.getMainPath(), "permission.yml");
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    private void loadFile() {
        createFile();
        for (Permissions permissions : Permissions.VALUES) if (getPermission(permissions) == null) set(permissions);
        saveFile();
    }

    private void saveFile() {
        try {
            this.config.save(file);
        } catch (IOException e) {
            logger.error("Error while saving config file", e);
        }
    }

    private void set(Permissions permissions) {
        config.set(permissions.getPath(), permissions.getPermission());
    }

    public String getPermission(Permissions permissions) {
        return config.getString(permissions.getPath());
    }
}
