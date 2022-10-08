package de.murmelmeister.worlds.utils.configs;

public enum Messages {

    PREFIX("Prefix", "&3Worlds &8» &r"),
    COMMANDS_DISABLE("Commands.Disable", "&cThis command is disable."),
    COMMANDS_NO_PERMISSION("Commands.NoPermission", "&cYou do not have permission to do that."),
    COMMAND_NO_CONSOLE("Commands.NoConsole", "&cThis command does not work in the console."),
    COMMANDS_WORLDS_DOES_EXIST("Commands.Worlds.DoesExist", "&7This world &e[WORLD] &7does&c exist&7."),
    COMMANDS_WORLDS_DOES_NOT_EXIST("Commands.Worlds.DoesNotExist", "&7This world &e[WORLD] &7does not&c exist&7."),
    COMMANDS_WORLDS_SUCCESSFUL_CREATE("Commands.Worlds.Successful.Create", "&7The world &e[WORLD] &7was&a created&7."),
    COMMANDS_WORLDS_SUCCESSFUL_DELETE("Commands.Worlds.Successful.Delete", "&7The world &e[WORLD] &7was&a deleted&7."),
    COMMANDS_WORLDS_SYNTAX_COMMAND("Commands.Worlds.Syntax.Command", "&7Syntax: &c/worlds <create | delete | tp | import> <worldName>"),
    COMMANDS_WORLDS_SYNTAX_CREATE("Commands.Worlds.Syntax.Create", "&7Syntax: &c/worlds create <worldName> <normal | nether | end>");

    private String path;
    private String value;

    Messages(String path, String value) {
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
