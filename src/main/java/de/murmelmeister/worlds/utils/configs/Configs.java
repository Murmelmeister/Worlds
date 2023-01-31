package de.murmelmeister.worlds.utils.configs;

public enum Configs {

    PREFIX_ENABLE("Prefix.Enable", true),
    COMMAND_WORLDS_COMMAND("Commands.Worlds.Command", true),
    COMMAND_WORLDS_CREATE("Commands.Worlds.Create", true),
    COMMAND_WORLDS_DELETE("Commands.Worlds.Delete", true),
    COMMAND_WORLDS_TELEPORT("Commands.Worlds.Teleport", true),
    COMMAND_WORLDS_IMPORT("Commands.Worlds.Import", true),
    COMMAND_WORLDS_GAMERULE("Commands.Worlds.GameRule", true),
    PERMISSION_WORLDS_COMMAND("Permissions.Worlds.Command", "worlds.command.worlds.command"),
    PERMISSION_WORLDS_CREATE("Permissions.Worlds.Create", "worlds.command.worlds.create"),
    PERMISSION_WORLDS_DELETE("Permissions.Worlds.Delete", "worlds.command.worlds.delete"),
    PERMISSION_WORLDS_TELEPORT("Permissions.Worlds.Teleport", "worlds.command.worlds.teleport"),
    PERMISSION_WORLDS_IMPORT("Permissions.Worlds.Import", "worlds.command.worlds.import"),
    PERMISSION_WORLDS_GAMERULE("Permissions.Worlds.GameRule", "worlds.command.worlds.gamerule");

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
