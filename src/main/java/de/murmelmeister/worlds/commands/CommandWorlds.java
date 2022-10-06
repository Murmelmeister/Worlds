package de.murmelmeister.worlds.commands;

import de.murmelmeister.worlds.InitPlugin;
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

    // TODO: Add this all messages in the configs

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender.hasPermission("worlds.command.worlds.use"))) {
            sendMessage(sender, "§cYou do not have permission to do that.");
            return true;
        }

        /*
             /worlds create <worldName> <environment>
             /worlds <delete|tp|import> <worldName>
         */

        if (args.length == 0 || args.length == 1 || (args.length == 2 && (args[0].equalsIgnoreCase("create")))) {
            sendMessage(sender, "§7Syntax: §c/worlds <create | delete | tp | import> <worldName>");
            //sendMessage();(sender, sender.getServer().getWorlds().toString()); // Test-Code
        } else if (args.length <= 3) {

            if (args[0].equalsIgnoreCase("create")) {

                if (!(sender.hasPermission("worlds.command.worlds.create"))) {
                    sendMessage(sender, "§cYou do not have permission to do that.");
                    return true;
                }

                String worldName = args[1];

                if (sender.getServer().getWorld(worldName) != null) {
                    sendMessage(sender, String.format("§7This world §e%s §7does§c exist§7.", worldName));
                    return true;
                }

                if (args[2].equalsIgnoreCase("normal")) {

                    getWorldManager().createWorld(worldName, World.Environment.NORMAL);
                    sendMessage(sender, String.format("§7The world §e%s §7was§a created§7.", worldName));

                } else if (args[2].equalsIgnoreCase("nether")) {

                    getWorldManager().createWorld(worldName, World.Environment.NETHER);
                    sendMessage(sender, String.format("§7The world §e%s §7was§a created§7.", worldName));

                } else if (args[2].equalsIgnoreCase("end")) {

                    getWorldManager().createWorld(worldName, World.Environment.THE_END);
                    sendMessage(sender, String.format("§7The world §e%s §7was§a created§7.", worldName));

                } else {
                    sendMessage(sender, "§7Syntax: §c/worlds create <worldName> <normal | nether | end>");
                }

            } else if (args[0].equalsIgnoreCase("delete")) {

                if (!(sender.hasPermission("worlds.command.worlds.delete"))) {
                    sendMessage(sender, "§cYou do not have permission to do that.");
                    return true;
                }

                String worldName = args[1];
                World world = sender.getServer().getWorld(worldName);

                if (world == null) {
                    sendMessage(sender, String.format("§7This world §e%s §7does not§c exist§7.", args[1]));
                    return true;
                }

                Player player = sender instanceof Player ? (Player) sender : null;

                if (player == null) {
                    getWorldManager().deleteWorld(worldName);
                    sendMessage(sender, String.format("§7The world §e%s §7was§a deleted§7.", worldName));
                    return true;
                }

                if (player.getWorld().equals(world))
                    player.kick(Component.text("§cThe world will be deleted.").append(Component.text(" §cRejoin the server, please.")));

                getWorldManager().deleteWorld(worldName);
                sendMessage(sender, String.format("§7The world §e%s §7was§a deleted§7.", worldName));

            } else if (args[0].equalsIgnoreCase("tp")) {

                if (!(sender.hasPermission("worlds.command.worlds.tp"))) {
                    sendMessage(sender, "§cYou do not have permission to do that.");
                    return true;
                }

                World world = sender.getServer().getWorld(args[1]);

                if (world == null) {
                    sendMessage(sender, String.format("§7This world §e%s §7does not§c exist§7.", args[1]));
                    return true;
                }

                Player player = sender instanceof Player ? (Player) sender : null;

                if (player == null) {
                    sendMessage(sender, "§cThis command does not work in the console.");
                    return true;
                }

                player.teleport(world.getSpawnLocation());

            } else if (args[0].equalsIgnoreCase("import")) {

                if (!(sender.hasPermission("worlds.command.worlds.import"))) {
                    sendMessage(sender, "§cYou do not have permission to do that.");
                    return true;
                }

                String worldName = args[1];

                if (sender.getServer().getWorld(worldName) != null) {
                    sendMessage(sender, String.format("§7This world §e%s §7does§c exist§7.", worldName));
                    return true;
                }

                getWorldManager().importWorld(worldName);
                sendMessage(sender, String.format("§7The world §e%s §7was§a created§7.", worldName));

            } else {
                sendMessage(sender, "§7Syntax: §c/worlds <create | delete | tp | import> <worldName>");
            }

        } else {
            sendMessage(sender, "§7Syntax: §c/worlds <create | delete | tp | import> <worldName>");
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

                if (StringUtil.startsWithIgnoreCase(worldCommand, lastWord)) {
                    tabComplete.add(worldCommand);
                }

                tabComplete.sort(String.CASE_INSENSITIVE_ORDER);
            }
        }

        if (args.length == 2) {

            String lastWord = args[args.length - 1];

            for (String world : getWorldManager().getWorldList()) {

                if (StringUtil.startsWithIgnoreCase(world, lastWord)) {
                    tabComplete.add(world);
                }

                tabComplete.sort(String.CASE_INSENSITIVE_ORDER);
            }
        }

        if (args.length == 3 && (args[0].equalsIgnoreCase("create"))) {

            String[] worldsEnvironment = {"normal", "nether", "end"};

            String lastWord = args[args.length - 1];

            for (String worldEnvironment : worldsEnvironment) {

                if (StringUtil.startsWithIgnoreCase(worldEnvironment, lastWord)) {
                    tabComplete.add(worldEnvironment);
                }

                tabComplete.sort(String.CASE_INSENSITIVE_ORDER);
            }
        }

        return tabComplete;
    }
}
