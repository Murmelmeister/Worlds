package de.murmelmeister.worlds.utils.configs;

public enum Configs {

    COMMANDS_WORLDS_COMMAND("Commands.Worlds.Command", true),
    COMMANDS_WORLDS_CREATE("Commands.Worlds.Create", true),
    COMMANDS_WORLDS_DELETE("Commands.Worlds.Delete", true),
    COMMANDS_WORLDS_TELEPORT("Commands.Worlds.Teleport", true),
    COMMANDS_WORLDS_IMPORT("Commands.Worlds.Import", true);

    private String path;
    private Object value;

    Configs(String path, Object value) {
        setPath(path);
        setValue(value);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
