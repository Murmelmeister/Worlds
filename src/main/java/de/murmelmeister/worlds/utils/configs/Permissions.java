package de.murmelmeister.worlds.utils.configs;

public enum Permissions {

    COMMANDS_WORLDS_COMMAND("Commands.Worlds.Command", "worlds.command.worlds.command"),
    COMMANDS_WORLDS_CREATE("Commands.Worlds.Create", "worlds.command.worlds.create"),
    COMMANDS_WORLDS_DELETE("Commands.Worlds.Delete", "worlds.command.worlds.delete"),
    COMMANDS_WORLDS_TELEPORT("Commands.Worlds.Teleport", "worlds.command.worlds.teleport"),
    COMMANDS_WORLDS_IMPORT("Commands.Worlds.Import", "worlds.command.worlds.import");

    private String path;
    private String value;

    Permissions(String path, String value) {
        setPath(path);
        setValue(value);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
