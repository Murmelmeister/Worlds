package de.murmelmeister.worlds.commands;

import de.murmelmeister.worlds.Worlds;
import de.murmelmeister.worlds.api.config.WorldManager;
import de.murmelmeister.worlds.files.ConfigFile;
import de.murmelmeister.worlds.files.MessageFile;
import de.murmelmeister.worlds.files.PermissionFile;
import de.murmelmeister.worlds.utils.configs.Configs;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.Plugin;

public abstract class CommandManager implements TabExecutor {
    protected final ConfigFile config;
    protected final MessageFile message;
    protected final PermissionFile permission;
    protected final WorldManager worldManager;

    public CommandManager(Worlds plugin) {
        this.config = plugin.getConfigFile();
        this.message = plugin.getMessageFile();
        this.permission = plugin.getPermissionFile();
        this.worldManager = plugin.getWorldManager();
    }

    public void sendMessage(CommandSender sender, String message) {
        if (config.getBoolean(Configs.PREFIX_ENABLED)) sender.sendMessage(MiniMessage.miniMessage().deserialize(this.message.getPrefix() + message));
        else sender.sendMessage(MiniMessage.miniMessage().deserialize(message));
    }

    public static void register(Worlds plugin) {
        addCommand(plugin, "worlds", new WorldsCommand(plugin));
    }

    private static void addCommand(Plugin plugin, String name, TabExecutor executor) {
        PluginCommand command = plugin.getServer().getPluginCommand(name);
        if (command == null) throw new NullPointerException("Command '" + name + "' not found");
        command.setExecutor(executor);
        command.setTabCompleter(executor);
    }
}
