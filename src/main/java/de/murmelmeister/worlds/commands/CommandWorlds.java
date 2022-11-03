package de.murmelmeister.worlds.commands;

import de.murmelmeister.worlds.InitPlugin;
import de.murmelmeister.worlds.utils.configs.Configs;
import de.murmelmeister.worlds.utils.configs.Messages;
import de.murmelmeister.worlds.utils.configs.Permissions;
import net.kyori.adventure.text.Component;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CommandWorlds extends CommandManager {
    public CommandWorlds(InitPlugin init) {
        super(init);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(getConfig().isConfigValue(Configs.COMMANDS_WORLDS_COMMAND))) {
            sendMessage(sender, getMessage().getConfigMessage(Messages.COMMANDS_DISABLE));
            return true;
        }

        if (!(sender.hasPermission(getPermission().getConfigPermission(Permissions.COMMANDS_WORLDS_COMMAND)))) {
            sendMessage(sender, getMessage().getConfigMessage(Messages.COMMANDS_NO_PERMISSION));
            return true;
        }

        /*
                - This need a update -
             /worlds create <worldName> <environment>
             /worlds <delete|tp|import> <worldName>
             /worlds gameRules get <worldName> <gameRules>
             /worlds gameRules set <worldName> <gameRules> <value>
         */

        if (args.length == 0 || (args.length == 1 && !args[0].equalsIgnoreCase("test")) || (args.length == 2 && (args[0].equalsIgnoreCase("create")))) { // TODO: Gives args errors?
            sendMessage(sender, getMessage().getConfigMessage(Messages.COMMANDS_WORLDS_SYNTAX_COMMAND));
        } else if (args.length <= 5) {

            switch (args[0]) {
                case "test":
                    sendMessage(sender, sender.getServer().getWorlds().toString()); // Test-Code
                    break;
                case "create":
                    worldsCreateCommand(sender, args);
                    break;
                case "delete":
                    worldsDeleteCommand(sender, args);
                    break;
                case "tp":
                    worldsTeleportCommand(sender, args);
                    break;
                case "import":
                    worldsImportCommand(sender, args);
                    break;
                case "gamerule":
                    worldsGameRuleCommand(sender, args); // TODO: Add config settings, that is only 'gamerule'
                    break;
                default:
                    sendMessage(sender, getMessage().getConfigMessage(Messages.COMMANDS_WORLDS_SYNTAX_COMMAND));
                    break;
            }

        } else {
            sendMessage(sender, getMessage().getConfigMessage(Messages.COMMANDS_WORLDS_SYNTAX_COMMAND));
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> tabComplete = new ArrayList<>();

        if (args.length == 1) {
            String[] worldsCommands = {"create", "delete", "tp", "import", "gamerule", "test"};
            String lastWord = args[args.length - 1];
            for (String worldCommand : worldsCommands) {
                if (StringUtil.startsWithIgnoreCase(worldCommand, lastWord))
                    tabComplete.add(worldCommand);
                tabComplete.sort(String.CASE_INSENSITIVE_ORDER);
            }
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("gamerule")) {
            String[] worldsCommands = {"get", "set"};
            String lastWord = args[args.length - 1];
            for (String worldCommand : worldsCommands) {
                if (StringUtil.startsWithIgnoreCase(worldCommand, lastWord))
                    tabComplete.add(worldCommand);
                tabComplete.sort(String.CASE_INSENSITIVE_ORDER);
            }
        }

        if (args.length == 2 && !args[0].equalsIgnoreCase("gamerule")) {
            String lastWord = args[args.length - 1];
            for (String world : getWorldManager().getWorldList()) {
                if (StringUtil.startsWithIgnoreCase(world, lastWord))
                    tabComplete.add(world);
                tabComplete.sort(String.CASE_INSENSITIVE_ORDER);
            }
        }

        if (args.length == 3 && args[0].equalsIgnoreCase("gamerule")) {
            String lastWord = args[args.length - 1];
            for (String world : getWorldManager().getWorldList()) {
                if (StringUtil.startsWithIgnoreCase(world, lastWord))
                    tabComplete.add(world);
                tabComplete.sort(String.CASE_INSENSITIVE_ORDER);
            }
        }

        if (args.length == 3 && (args[0].equalsIgnoreCase("create"))) {
            String[] worldsEnvironment = {"normal", "nether", "end"};
            String lastWord = args[args.length - 1];
            for (String worldEnvironment : worldsEnvironment) {
                if (StringUtil.startsWithIgnoreCase(worldEnvironment, lastWord))
                    tabComplete.add(worldEnvironment);
                tabComplete.sort(String.CASE_INSENSITIVE_ORDER);
            }
        }

        if (args.length == 4 && args[0].equalsIgnoreCase("gamerule")) {
            String lastWord = args[args.length - 1];
            for (GameRule<?> gameRule : GameRule.values()) {
                if (StringUtil.startsWithIgnoreCase(gameRule.getName(), lastWord))
                    tabComplete.add(gameRule.getName());
                tabComplete.sort(String.CASE_INSENSITIVE_ORDER);
            }
        }

        return tabComplete;
    }

    private void worldsCreateCommand(CommandSender sender, String[] args) {
        if (!(getConfig().isConfigValue(Configs.COMMANDS_WORLDS_CREATE))) {
            sendMessage(sender, getMessage().getConfigMessage(Messages.COMMANDS_DISABLE));
            return;
        }

        if (!(sender.hasPermission(getPermission().getConfigPermission(Permissions.COMMANDS_WORLDS_CREATE)))) {
            sendMessage(sender, getMessage().getConfigMessage(Messages.COMMANDS_NO_PERMISSION));
            return;
        }

        String worldName = args[1];

        if (sender.getServer().getWorld(worldName) != null) {
            sendMessage(sender, getMessage().getConfigMessage(Messages.COMMANDS_WORLDS_DOES_EXIST).replace("[WORLD]", worldName));
            return;
        }

        String worldEnvironment = args[2];
        switch (worldEnvironment) {
            case "normal":
                getWorldManager().createWorld(worldName, World.Environment.NORMAL);
                sendMessage(sender, getMessage().getConfigMessage(Messages.COMMANDS_WORLDS_SUCCESSFUL_CREATE).replace("[WORLD]", worldName));
                break;
            case "nether":
                getWorldManager().createWorld(worldName, World.Environment.NETHER);
                sendMessage(sender, getMessage().getConfigMessage(Messages.COMMANDS_WORLDS_SUCCESSFUL_CREATE).replace("[WORLD]", worldName));
                break;
            case "end":
                getWorldManager().createWorld(worldName, World.Environment.THE_END);
                sendMessage(sender, getMessage().getConfigMessage(Messages.COMMANDS_WORLDS_SUCCESSFUL_CREATE).replace("[WORLD]", worldName));
                break;
            default:
                sendMessage(sender, getMessage().getConfigMessage(Messages.COMMANDS_WORLDS_SYNTAX_CREATE));
                break;
        }
    }

    private void worldsDeleteCommand(CommandSender sender, String[] args) {
        if (!(getConfig().isConfigValue(Configs.COMMANDS_WORLDS_DELETE))) {
            sendMessage(sender, getMessage().getConfigMessage(Messages.COMMANDS_DISABLE));
            return;
        }

        if (!(sender.hasPermission(getPermission().getConfigPermission(Permissions.COMMANDS_WORLDS_DELETE)))) {
            sendMessage(sender, getMessage().getConfigMessage(Messages.COMMANDS_NO_PERMISSION));
            return;
        }

        World world = sender.getServer().getWorld(args[1]);

        if (world == null) {
            sendMessage(sender, getMessage().getConfigMessage(Messages.COMMANDS_WORLDS_DOES_NOT_EXIST).replace("[WORLD]", args[1]));
            return;
        }

        Player player = sender instanceof Player ? (Player) sender : null;

        if (player != null)
            if (player.getWorld().equals(world))
                player.kick(Component.text(getMessage().getConfigMessage(Messages.COMMANDS_WORLDS_DELETE_KICKED_PLAYER)));

        getWorldManager().deleteWorld(world.getName());
        sendMessage(sender, getMessage().getConfigMessage(Messages.COMMANDS_WORLDS_SUCCESSFUL_DELETE).replace("[WORLD]", world.getName()));
    }

    private void worldsTeleportCommand(CommandSender sender, String[] args) {
        if (!(getConfig().isConfigValue(Configs.COMMANDS_WORLDS_TELEPORT))) {
            sendMessage(sender, getMessage().getConfigMessage(Messages.COMMANDS_DISABLE));
            return;
        }

        if (!(sender.hasPermission(getPermission().getConfigPermission(Permissions.COMMANDS_WORLDS_TELEPORT)))) {
            sendMessage(sender, getMessage().getConfigMessage(Messages.COMMANDS_NO_PERMISSION));
            return;
        }

        World world = sender.getServer().getWorld(args[1]);

        if (world == null) {
            sendMessage(sender, getMessage().getConfigMessage(Messages.COMMANDS_WORLDS_DOES_NOT_EXIST).replace("[WORLD]", args[1]));
            return;
        }

        Player player = sender instanceof Player ? (Player) sender : null;

        if (player == null) {
            sendMessage(sender, getMessage().getConfigMessage(Messages.COMMAND_NO_CONSOLE));
            return;
        }

        player.teleport(world.getSpawnLocation());
        sendMessage(player, getMessage().getConfigMessage(Messages.COMMANDS_WORLDS_TELEPORT).replace("[WORLD]", world.getName()));
    }

    private void worldsImportCommand(CommandSender sender, String[] args) {
        if (!(getConfig().isConfigValue(Configs.COMMANDS_WORLDS_IMPORT))) {
            sendMessage(sender, getMessage().getConfigMessage(Messages.COMMANDS_DISABLE));
            return;
        }

        if (!(sender.hasPermission(getPermission().getConfigPermission(Permissions.COMMANDS_WORLDS_IMPORT)))) {
            sendMessage(sender, getMessage().getConfigMessage(Messages.COMMANDS_NO_PERMISSION));
            return;
        }

        String worldName = args[1];

        if (sender.getServer().getWorld(worldName) != null) {
            sendMessage(sender, getMessage().getConfigMessage(Messages.COMMANDS_WORLDS_DOES_EXIST).replace("[WORLD]", worldName));
            return;
        }

        getWorldManager().importWorld(worldName);
        sendMessage(sender, getMessage().getConfigMessage(Messages.COMMANDS_WORLDS_SUCCESSFUL_CREATE).replace("[WORLD]", worldName));
    }

    @SuppressWarnings("deprecation")
    private void worldsGameRuleCommand(CommandSender sender, String[] args) {
        if (!(getConfig().isConfigValue(Configs.COMMANDS_WORLDS_GAMERULE))) {
            sendMessage(sender, getMessage().getConfigMessage(Messages.COMMANDS_DISABLE));
            return;
        }

        if (!(sender.hasPermission(getPermission().getConfigPermission(Permissions.COMMANDS_WORLDS_GAMERULE)))) {
            sendMessage(sender, getMessage().getConfigMessage(Messages.COMMANDS_NO_PERMISSION));
            return;
        }

        World world = sender.getServer().getWorld(args[2]);

        if (world == null) {
            sendMessage(sender, getMessage().getConfigMessage(Messages.COMMANDS_WORLDS_DOES_NOT_EXIST).replace("[WORLD]", args[2]));
            return;
        }

        GameRule<?> gameRule = GameRule.getByName(args[3]);

        if (gameRule == null) {
            sendMessage(sender, getMessage().getConfigMessage(Messages.COMMANDS_WORLDS_GAMERULE_WRONG_GAMERULE).replace("[GAMERULE]", args[3]));
            return;
        }
        String path = String.format("Worlds.World.%s.GameRules.%s", world.getName(), gameRule.getName());

        switch (args[1]) {
            case "get":
                sendMessage(sender, getMessage().getConfigMessage(Messages.COMMANDS_WORLDS_GAMERULE_GET).replace("[GAMERULE]", gameRule.getName()).replace("[VALUE]", getWorldManager().getConfigPath(path)));
                break;
            case "set":
                Object value = args[4];
                getWorldManager().setConfigValue(path, value);
                getWorldManager().saveConfig();
                world.setGameRuleValue(gameRule.getName(), value.toString()); // deprecated Method
                sendMessage(sender, getMessage().getConfigMessage(Messages.COMMANDS_WORLDS_GAMERULE_SET).replace("[GAMERULE]", gameRule.getName()).replace("[VALUE]", value.toString()));
                break;
            default:
                sendMessage(sender, getMessage().getConfigMessage(Messages.COMMANDS_WORLDS_SYNTAX_GAMERULE));
                break;
        }

    }
}
