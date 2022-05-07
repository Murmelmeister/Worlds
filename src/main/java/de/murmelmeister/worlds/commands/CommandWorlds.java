package de.murmelmeister.worlds.commands;

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

public class CommandWorlds extends Commands {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender.hasPermission("worlds.command.worlds.use"))) {
            setSendMessage(sender, "§cYou do not have permission to do that.");
            return true;
        }

        /*
             /worlds create <worldName> <environment>
             /worlds <delete|tp|import> <worldName>
         */

        if (args.length == 0 || args.length == 1 || (args.length == 2 && (args[0].equalsIgnoreCase("create")))) {
            setSendMessage(sender, "§7Syntax: §c/worlds <create | delete | tp | import> <worldName>");
        } else if (args.length <= 3) {

            if (args[0].equalsIgnoreCase("create")) {

                if (!(sender.hasPermission("worlds.command.worlds.create"))) {
                    setSendMessage(sender, "§cYou do not have permission to do that.");
                    return true;
                }

                String worldName = args[1];

                if (sender.getServer().getWorld(worldName) != null) {
                    setSendMessage(sender, String.format("§7This world §e%s §7does§c exist§7.", worldName));
                    return true;
                }

                if (args[2].equalsIgnoreCase("normal")) {

                    this.worldManager.createWorld(worldName, World.Environment.NORMAL);
                    setSendMessage(sender, String.format("§7The world §e%s §7was§a created§7.", worldName));

                } else if (args[2].equalsIgnoreCase("nether")) {

                    this.worldManager.createWorld(worldName, World.Environment.NETHER);
                    setSendMessage(sender, String.format("§7The world §e%s §7was§a created§7.", worldName));

                } else if (args[2].equalsIgnoreCase("end")) {

                    this.worldManager.createWorld(worldName, World.Environment.THE_END);
                    setSendMessage(sender, String.format("§7The world §e%s §7was§a created§7.", worldName));

                } else {
                    setSendMessage(sender, "§7Syntax: §c/worlds create <worldName> <normal | nether | end>");
                }

            } else if (args[0].equalsIgnoreCase("delete")) {

                if (!(sender.hasPermission("worlds.command.worlds.delete"))) {
                    setSendMessage(sender, "§cYou do not have permission to do that.");
                    return true;
                }

                String worldName = args[1];
                World world = sender.getServer().getWorld(worldName);

                if (world == null) {
                    setSendMessage(sender, String.format("§7This world §e%s §7does not§c exist§7.", args[1]));
                    return true;
                }

                Player player = sender instanceof Player ? (Player) sender : null;

                if (player == null) return true; // Player can not be null
                if (player.getWorld().equals(world))
                    player.kick(Component.text("§cThe world will be deleted.").append(Component.text(" §cRejoin the server, please.")));

                this.worldManager.deleteWorld(worldName);
                setSendMessage(sender, String.format("§7The world §e%s §7was§a deleted§7.", worldName));

            } else if (args[0].equalsIgnoreCase("tp")) {

                if (!(sender.hasPermission("worlds.command.worlds.tp"))) {
                    setSendMessage(sender, "§cYou do not have permission to do that.");
                    return true;
                }

                World world = sender.getServer().getWorld(args[1]);

                if (world == null) {
                    setSendMessage(sender, String.format("§7This world §e%s §7does not§c exist§7.", args[1]));
                    return true;
                }

                Player player = sender instanceof Player ? (Player) sender : null;

                if (player == null) {
                    setSendMessage(sender, "§cThis command does not work in the console.");
                    return true;
                }

                player.teleport(world.getSpawnLocation());

            } else if (args[0].equalsIgnoreCase("import")) {

                if (!(sender.hasPermission("worlds.command.worlds.import"))) {
                    setSendMessage(sender, "§cYou do not have permission to do that.");
                    return true;
                }

                String worldName = args[1];

                if (sender.getServer().getWorld(worldName) != null) {
                    setSendMessage(sender, String.format("§7This world §e%s §7does§c exist§7.", worldName));
                    return true;
                }

                this.worldManager.importWorld(worldName);
                setSendMessage(sender, String.format("§7The world §e%s §7was§a created§7.", worldName));

            } else {
                setSendMessage(sender, "§7Syntax: §c/worlds <create | delete | tp | import> <worldName>");
            }

        } else {
            setSendMessage(sender, "§7Syntax: §c/worlds <create | delete | tp | import> <worldName>");
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

            for (String world : this.worldManager.getWorldList()) {

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
