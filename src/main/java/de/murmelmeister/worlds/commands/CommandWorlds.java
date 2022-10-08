package de.murmelmeister.worlds.commands;

import de.murmelmeister.worlds.InitPlugin;
import de.murmelmeister.worlds.utils.configs.Configs;
import de.murmelmeister.worlds.utils.configs.Messages;
import de.murmelmeister.worlds.utils.configs.Permissions;
import net.kyori.adventure.text.Component;
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

    // TODO: Add command /worlds gameRules <gameRules> <boolean | int>

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
             /worlds create <worldName> <environment>
             /worlds <delete|tp|import> <worldName>
             /worlds gameRules <gameRules> <boolean | int>
         */

        if (args.length == 0 || args.length == 1 || (args.length == 2 && (args[0].equalsIgnoreCase("create")))) {
            sendMessage(sender, getMessage().getConfigMessage(Messages.COMMANDS_WORLDS_SYNTAX_COMMAND));
            sendMessage(sender, sender.getServer().getWorlds().toString()); // Test-Code
        } else if (args.length <= 3) {

            switch (args[0]) {
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
            String[] worldsCommands = {"create", "delete", "tp", "import"};
            String lastWord = args[args.length - 1];
            for (String worldCommand : worldsCommands) {
                if (StringUtil.startsWithIgnoreCase(worldCommand, lastWord))
                    tabComplete.add(worldCommand);
                tabComplete.sort(String.CASE_INSENSITIVE_ORDER);
            }
        }

        if (args.length == 2) {
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

        String worldName = args[1];
        World world = sender.getServer().getWorld(worldName);

        if (world == null) {
            sendMessage(sender, getMessage().getConfigMessage(Messages.COMMANDS_WORLDS_DOES_NOT_EXIST).replace("[WORLD]", args[1]));
            return;
        }

        Player player = sender instanceof Player ? (Player) sender : null;

        if (player != null)
            if (player.getWorld().equals(world))
                player.kick(Component.text("§cThe world will be deleted.").append(Component.text(" §cRejoin the server, please."))); // TODO: Add message.yml

        getWorldManager().deleteWorld(worldName);
        sendMessage(sender, getMessage().getConfigMessage(Messages.COMMANDS_WORLDS_SUCCESSFUL_DELETE).replace("[WORLD]", worldName));
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
        // TODO: Add send message
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
}
