package de.murmelmeister.worlds.utils.configs;

public enum Messages {
    PREFIX("Prefix", "&3Worlds &8» &r"),
    COMMAND_DISABLE("Commands.Disable", "&cThis command is disable."),
    COMMAND_NO_PERMISSION("Commands.NoPermission", "&cYou do not have permission to do that."),
    COMMAND_NO_CONSOLE("Commands.NoConsole", "&cThis command does not work in the console."),
    COMMAND_WORLDS_DOES_EXIST("Commands.Worlds.DoesExist", "&7This world &e[WORLD] &7does&c exist&7."),
    COMMAND_WORLDS_DOES_NOT_EXIST("Commands.Worlds.DoesNotExist", "&7This world &e[WORLD] &7does not&c exist&7."),
    COMMAND_WORLDS_SUCCESSFUL_CREATE("Commands.Worlds.Successful.Create", "&7The world &e[WORLD] &7was&a created&7."),
    COMMAND_WORLDS_SUCCESSFUL_DELETE("Commands.Worlds.Successful.Delete", "&7The world &e[WORLD] &7was&a deleted&7."),
    COMMAND_WORLDS_SYNTAX_COMMAND("Commands.Worlds.Syntax.Command", "&7Syntax: &c/worlds <create | delete | tp | import | gamerule> <worldName>"),
    COMMAND_WORLDS_SYNTAX_CREATE("Commands.Worlds.Syntax.Create", "&7Syntax: &c/worlds create <worldName> <normal | nether | end>"),
    COMMAND_WORLDS_TELEPORT("Commands.Worlds.Teleport", "&7You have&a teleported &7into the world &e[WORLD]&7."),
    COMMAND_WORLDS_DELETE_KICKED_PLAYER("Commands.Worlds.Delete.KickedPlayer", "&cThe world is deleted.\n&cRejoin the server, please."),
    COMMAND_WORLDS_SYNTAX_GAMERULE("Commands.Worlds.Syntax.GameRule", "&7Syntax: &c/worlds gamerule <get | set> <gamerule> <value>"),
    COMMAND_WORLDS_GAMERULE_WRONG_GAMERULE("Commands.Worlds.GameRule.Wrong.GameRule", "&7This GameRule &e[GAMERULE] &7does not&c exist&7."),
    COMMAND_WORLDS_GAMERULE_GET("Commands.Worlds.GameRule.Get", "&7This GameRule &a[GAMERULE] &7has the value &e[VALUE]&7."),
    COMMAND_WORLDS_GAMERULE_SET("Commands.Worlds.GameRule.Set", "&7This GameRule &a[GAMERULE] &7was set to the value &e[VALUE]&7.");
    public static final Messages[] VALUES = values();

    private final String path;
    private final String message;

    Messages(final String path, final String message) {
        this.path = path;
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public String getMessage() {
        return message;
    }
}
