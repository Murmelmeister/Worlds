package de.murmelmeister.worlds.listeners;

import de.murmelmeister.worlds.Worlds;
import de.murmelmeister.worlds.api.config.PlayerManager;
import de.murmelmeister.worlds.files.ConfigFile;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public abstract class Listeners implements Listener {
    protected final ConfigFile config;
    protected final PlayerManager playerManager;

    public Listeners(Worlds plugin) {
        this.config = plugin.getConfigFile();
        this.playerManager = plugin.getPlayerManager();
    }

    public static void register(Worlds plugin) {
        addListener(plugin, new PlayerInventoryListener(plugin));
        addListener(plugin, new PlayerEnderChestListener(plugin));
    }

    private static void addListener(Plugin plugin, Listener listener) {
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
    }
}
