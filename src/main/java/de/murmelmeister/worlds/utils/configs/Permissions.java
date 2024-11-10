package de.murmelmeister.worlds.utils.configs;

public enum Permissions {
    COMMAND_WORLDS_COMMAND("Commands.Worlds.Command", "worlds.command.worlds.command"),
    COMMAND_WORLDS_CREATE("Commands.Worlds.Create", "worlds.command.worlds.create"),
    COMMAND_WORLDS_DELETE("Commands.Worlds.Delete", "worlds.command.worlds.delete"),
    COMMAND_WORLDS_TELEPORT("Commands.Worlds.Teleport", "worlds.command.worlds.teleport"),
    COMMAND_WORLDS_IMPORT("Commands.Worlds.Import", "worlds.command.worlds.import"),
    COMMAND_WORLDS_GAMERULE("Commands.Worlds.GameRule", "worlds.command.worlds.gamerule");
    public static final Permissions[] VALUES = values();

    private final String path;
    private final String permission;

    Permissions(final String path, final String permission) {
        this.path = path;
        this.permission = permission;
    }

    public String getPath() {
        return path;
    }

    public String getPermission() {
        return permission;
    }
}
