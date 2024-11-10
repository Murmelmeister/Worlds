package de.murmelmeister.worlds.utils.configs;

public enum Configs {
    PREFIX_ENABLED("Prefix.Enabled", true),
    COMMAND_ENABLE_WORLDS_COMMAND("Commands.Enable.Worlds.Command", true),
    COMMAND_ENABLE_WORLDS_CREATE("Commands.Enable.Worlds.Create", true),
    COMMAND_ENABLE_WORLDS_DELETE("Commands.Enable.Worlds.Delete", true),
    COMMAND_ENABLE_WORLDS_TELEPORT("Commands.Enable.Worlds.Teleport", true),
    COMMAND_ENABLE_WORLDS_IMPORT("Commands.Enable.Worlds.Import", true),
    COMMAND_ENABLE_WORLDS_GAMERULE("Commands.Enable.Worlds.GameRule", true),
    SAVING_PLAYER_INVENTORY("Saving.PlayerInventory", true),
    SAVING_PLAYER_ENDER_CHEST("Saving.PlayerEnderChest", true);
    public static final Configs[] VALUES = values();

    private final String path;
    private final Object value;

    Configs(final String path, final Object value) {
        this.path = path;
        this.value = value;
    }

    public String getPath() {
        return path;
    }

    public Object getValue() {
        return value;
    }
}
