package de.murmelmeister.worlds.command.commands;

import de.murmelmeister.worlds.Main;
import de.murmelmeister.worlds.command.CommandManager;
import de.murmelmeister.worlds.utils.configs.Configs;
import de.murmelmeister.worlds.utils.configs.Messages;
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

public class WorldsCommand extends CommandManager {
    public WorldsCommand(Main main) {
        super(main);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(config.getBoolean(Configs.COMMAND_WORLDS_COMMAND))) {
            sendMessage(sender, message.getString(Messages.COMMAND_DISABLE));
            return true;
        }

        if (!(sender.hasPermission(config.getString(Configs.PERMISSION_WORLDS_COMMAND)))) {
            sendMessage(sender, message.getString(Messages.COMMAND_NO_PERMISSION));
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
            sendMessage(sender, message.getString(Messages.COMMAND_WORLDS_SYNTAX_COMMAND));
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
                    sendMessage(sender, message.getString(Messages.COMMAND_WORLDS_SYNTAX_COMMAND));
                    break;
            }

        } else {
            sendMessage(sender, message.getString(Messages.COMMAND_WORLDS_SYNTAX_COMMAND));
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
            for (String world : worldManager.getWorldID().getListID()) {
                if (StringUtil.startsWithIgnoreCase(world, lastWord))
                    tabComplete.add(world);
                tabComplete.sort(String.CASE_INSENSITIVE_ORDER);
            }
        }

        if (args.length == 3 && args[0].equalsIgnoreCase("gamerule")) {
            String lastWord = args[args.length - 1];
            for (String world : worldManager.getWorldID().getListID()) {
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
        if (!(config.getBoolean(Configs.COMMAND_WORLDS_CREATE))) {
            sendMessage(sender, message.getString(Messages.COMMAND_DISABLE));
            return;
        }

        if (!(sender.hasPermission(config.getString(Configs.PERMISSION_WORLDS_CREATE)))) {
            sendMessage(sender, message.getString(Messages.COMMAND_NO_PERMISSION));
            return;
        }

        String worldName = args[1];

        if (sender.getServer().getWorld(worldName) != null) {
            sendMessage(sender, message.getString(Messages.COMMAND_WORLDS_DOES_EXIST).replace("[WORLD]", worldName));
            return;
        }

        String worldEnvironment = args[2];
        switch (worldEnvironment) {
            case "normal":
                worldManager.createWorld(worldName, World.Environment.NORMAL);
                sendMessage(sender, message.getString(Messages.COMMAND_WORLDS_SUCCESSFUL_CREATE).replace("[WORLD]", worldName));
                break;
            case "nether":
                worldManager.createWorld(worldName, World.Environment.NETHER);
                sendMessage(sender, message.getString(Messages.COMMAND_WORLDS_SUCCESSFUL_CREATE).replace("[WORLD]", worldName));
                break;
            case "end":
                worldManager.createWorld(worldName, World.Environment.THE_END);
                sendMessage(sender, message.getString(Messages.COMMAND_WORLDS_SUCCESSFUL_CREATE).replace("[WORLD]", worldName));
                break;
            default:
                sendMessage(sender, message.getString(Messages.COMMAND_WORLDS_SYNTAX_CREATE));
                break;
        }
    }

    private void worldsDeleteCommand(CommandSender sender, String[] args) {
        if (!(config.getBoolean(Configs.COMMAND_WORLDS_DELETE))) {
            sendMessage(sender, message.getString(Messages.COMMAND_DISABLE));
            return;
        }

        if (!(sender.hasPermission(config.getString(Configs.PERMISSION_WORLDS_DELETE)))) {
            sendMessage(sender, message.getString(Messages.COMMAND_NO_PERMISSION));
            return;
        }

        World world = sender.getServer().getWorld(args[1]);

        if (world == null) {
            sendMessage(sender, message.getString(Messages.COMMAND_WORLDS_DOES_NOT_EXIST).replace("[WORLD]", args[1]));
            return;
        }

        Player player = sender instanceof Player ? (Player) sender : null;

        if (player != null)
            if (player.getWorld().equals(world))
                player.kick(Component.text(message.getString(Messages.COMMAND_WORLDS_DELETE_KICKED_PLAYER)));

        worldManager.deleteWorld(world.getName());
        sendMessage(sender, message.getString(Messages.COMMAND_WORLDS_SUCCESSFUL_DELETE).replace("[WORLD]", world.getName()));
    }

    private void worldsTeleportCommand(CommandSender sender, String[] args) {
        if (!(config.getBoolean(Configs.COMMAND_WORLDS_TELEPORT))) {
            sendMessage(sender, message.getString(Messages.COMMAND_DISABLE));
            return;
        }

        if (!(sender.hasPermission(config.getString(Configs.PERMISSION_WORLDS_TELEPORT)))) {
            sendMessage(sender, message.getString(Messages.COMMAND_NO_PERMISSION));
            return;
        }

        World world = sender.getServer().getWorld(args[1]);

        if (world == null) {
            sendMessage(sender, message.getString(Messages.COMMAND_WORLDS_DOES_NOT_EXIST).replace("[WORLD]", args[1]));
            return;
        }

        Player player = sender instanceof Player ? (Player) sender : null;

        if (player == null) {
            sendMessage(sender, message.getString(Messages.COMMAND_NO_CONSOLE));
            return;
        }

        player.teleport(world.getSpawnLocation());
        sendMessage(player, message.getString(Messages.COMMAND_WORLDS_TELEPORT).replace("[WORLD]", world.getName()));
    }

    private void worldsImportCommand(CommandSender sender, String[] args) {
        if (!(config.getBoolean(Configs.COMMAND_WORLDS_IMPORT))) {
            sendMessage(sender, message.getString(Messages.COMMAND_DISABLE));
            return;
        }

        if (!(sender.hasPermission(config.getString(Configs.PERMISSION_WORLDS_IMPORT)))) {
            sendMessage(sender, message.getString(Messages.COMMAND_NO_PERMISSION));
            return;
        }

        String worldName = args[1];

        if (sender.getServer().getWorld(worldName) != null) {
            sendMessage(sender, message.getString(Messages.COMMAND_WORLDS_DOES_EXIST).replace("[WORLD]", worldName));
            return;
        }

        worldManager.importWorld(worldName);
        sendMessage(sender, message.getString(Messages.COMMAND_WORLDS_SUCCESSFUL_CREATE).replace("[WORLD]", worldName));
    }

    @SuppressWarnings("deprecation")
    private void worldsGameRuleCommand(CommandSender sender, String[] args) {
        if (!(config.getBoolean(Configs.COMMAND_WORLDS_GAMERULE))) {
            sendMessage(sender, message.getString(Messages.COMMAND_DISABLE));
            return;
        }

        if (!(sender.hasPermission(config.getString(Configs.PERMISSION_WORLDS_GAMERULE)))) {
            sendMessage(sender, message.getString(Messages.COMMAND_NO_PERMISSION));
            return;
        }

        World world = sender.getServer().getWorld(args[2]);

        if (world == null) {
            sendMessage(sender, message.getString(Messages.COMMAND_WORLDS_DOES_NOT_EXIST).replace("[WORLD]", args[2]));
            return;
        }

        GameRule<?> gameRule = GameRule.getByName(args[3]);

        if (gameRule == null) {
            sendMessage(sender, message.getString(Messages.COMMAND_WORLDS_GAMERULE_WRONG_GAMERULE).replace("[GAMERULE]", args[3]));
            return;
        }
        String path = String.format("Worlds.World.%s.GameRules.%s", world.getName(), gameRule.getName());

        switch (args[1]) {
            case "get":
                sendMessage(sender, message.getString(Messages.COMMAND_WORLDS_GAMERULE_GET).replace("[GAMERULE]", gameRule.getName()).replace("[VALUE]", worldManager.getString(path)));
                break;
            case "set":
                Object value = args[4];
                worldManager.set(path, value);
                worldManager.save();
                world.setGameRuleValue(gameRule.getName(), value.toString()); // deprecated Method
                sendMessage(sender, message.getString(Messages.COMMAND_WORLDS_GAMERULE_SET).replace("[GAMERULE]", gameRule.getName()).replace("[VALUE]", value.toString()));
                break;
            default:
                sendMessage(sender, message.getString(Messages.COMMAND_WORLDS_SYNTAX_GAMERULE));
                break;
        }

    }
}
