package de.murmelmeister.worlds.utils;

import org.slf4j.Logger;

import java.io.File;

public final class FileUtil {
    public static File createFile(Logger logger, String path, String name) {
        final File file = new File(path, name);
        final File parent = file.getParentFile();
        if (!parent.exists()) {
            boolean success = parent.mkdirs();
            if (!success) logger.error("Could not create parent directories for file: {}", file);
        }

        if (!file.exists()) {
            try {
                boolean success = file.createNewFile();
                if (!success) logger.error("Could not create file: {}", file);
            } catch (Exception e) {
                logger.error("Could not create file: {}", file, e);
            }
        }
        return file;
    }
}
