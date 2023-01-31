package de.murmelmeister.worlds.api;

import de.murmelmeister.worlds.Main;
import de.murmelmeister.worlds.utils.InfoUtil;
import org.bukkit.configuration.file.YamlConfiguration;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WorldID {
    private final Logger logger;

    private File file;
    private YamlConfiguration config;

    private final List<String> listID = new ArrayList<>();

    public WorldID(Main main) {
        this.logger = main.getInstance().getSLF4JLogger();
    }

    public void create() {
        String pathName = String.format("plugins//%s//", InfoUtil.pluginName());
        String fileName = "WorldID.yml";
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

    public void addID(String id) {
        if (!(listID.contains(id))) listID.add(id);
    }

    public void addListID(String id) {
        if (listID.contains(id)) return;
        create();
        listID.add(id);
        set("IDs", listID);
        save();
    }

    public void removeID(String id) {
        listID.remove(id);
    }

    public void removeListID(String id) {
        if (!(listID.contains(id))) return;
        create();
        listID.remove(id);
        set("IDs", listID);
        save();
    }

    public void loadListID() {
        create();
        for (String id : getStringList("IDs")) if (!(listID.contains(id))) listID.add(id);
    }

    private void set(String path, Object value) {
        config.set(path, value);
    }

    private List<String> getStringList(String path) {
        return config.getStringList(path);
    }

    public List<String> getListID() {
        return listID;
    }
}
