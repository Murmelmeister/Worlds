package de.murmelmeister.worlds.utils;

import org.bukkit.Server;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;

public class InfoUtil {
    public static void disableMessage(Server server) {
        server.getConsoleSender().sendMessage(String.format("§3%s §c» §7Plugin is§c disabled§7! §7Version: §b%s §7by §b%s", pluginName(), version(), author()));
    }

    public static void enableMessage(Server server) {
        server.getConsoleSender().sendMessage(String.format("§3%s §a» §7Plugin is§a enabled§7! §7Version: §b%s §7by §b%s", pluginName(), version(), author()));
    }

    public static String author() {
        return "Murmelmeister";
    }

    public static String version() {
        return "0.0.1-ALPHA-SNAPSHOT";
    }

    public static String pluginName() {
        return "Worlds";
    }

    public static void fileExists(Logger logger, File file, String fileName) {
        if (!(file.getParentFile().exists())) {
            boolean aBoolean = file.getParentFile().mkdir();
            if (!(aBoolean)) logger.warn("The plugin can not create a second folder.");
        }
        if (!(file.exists())) {
            try {
                boolean aBoolean = file.createNewFile();
                if (!(aBoolean)) logger.warn(String.format("The plugin can not create the file '%s'.", fileName));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
