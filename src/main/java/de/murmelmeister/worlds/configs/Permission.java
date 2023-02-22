package de.murmelmeister.worlds.configs;

import de.murmelmeister.worlds.InitPlugin;
import de.murmelmeister.worlds.utils.configs.Permissions;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Permission {

    private InitPlugin init;

    private File folder;
    private File file;
    private YamlConfiguration config;

    public Permission(InitPlugin init) {
        setInit(init);
        createFile();
        saveFile();
    }

    public void createFile() {
        String fileName = "permission.yml";
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
        createConfig();
        loadConfig();
    }

    public void saveFile() {
        try {
            getConfig().save(getFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createConfig() {
        for (Permissions permissions : Permissions.values())
            if (getConfigPermission(permissions) == null) setConfigPermission(permissions);
    }

    private void loadConfig() {
        for (Permissions permissions : Permissions.values()) getConfigPermission(permissions);
    }

    public void setConfigPermission(Permissions permissions) {
        getConfig().set(permissions.getPath(), permissions.getValue());
    }

    public String getConfigPermission(Permissions permissions) {
        return getConfig().getString(permissions.getPath());
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
}
