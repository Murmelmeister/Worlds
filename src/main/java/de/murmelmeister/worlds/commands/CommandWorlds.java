package de.murmelmeister.worlds.commands;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CommandWorlds extends Commands {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(!(sender.hasPermission("worlds.command.worlds.use"))) {
            setSendMessage(sender, "§cYou do not have permission to do that.");
            return true;
        }

        /*
             /worlds <create|delete|tp|import> <worldName>
         */

        if(args.length <= 2) {

            if(args[0].equalsIgnoreCase("create")) {

            } else if(args[0].equalsIgnoreCase("delete")) {

            } else if (args[0].equalsIgnoreCase("tp")) {

                if(!(sender.hasPermission("worlds.command.worlds.tp"))) {
                    setSendMessage(sender, "§cYou do not have permission to do that.");
                    return true;
                }

                World world = sender.getServer().getWorld(args[1]);

                if(world == null) {
                    setSendMessage(sender, "§cThis world does not exist.");
                    return true;
                }

                Player player = sender instanceof Player ? (Player) sender : null;

                if(player == null) {
                    setSendMessage(sender, "§cThis command does not work in the console.");
                    return true;
                }

                player.teleport(world.getSpawnLocation());

            } else if(args[0].equalsIgnoreCase("import")) {

                if(!(sender.hasPermission("worlds.command.worlds.import"))) {
                    setSendMessage(sender, "§cYou do not have permission to do that.");
                    return true;
                }

                String worldName = args[1];
                this.worldManager.importWorld(worldName);
                setSendMessage(sender, "§aWorld was created.");

            }

        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return null;
    }
}
