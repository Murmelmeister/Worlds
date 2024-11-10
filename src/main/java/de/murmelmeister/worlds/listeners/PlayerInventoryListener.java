package de.murmelmeister.worlds.listeners;

import de.murmelmeister.worlds.Worlds;
import de.murmelmeister.worlds.utils.configs.Configs;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public final class PlayerInventoryListener extends Listeners {
    public PlayerInventoryListener(Worlds plugin) {
        super(plugin);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void handlePlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (config.getBoolean(Configs.SAVING_PLAYER_INVENTORY))
            playerManager.loadInventory(player.getUniqueId());
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void handlePlayerQuit(PlayerQuitEvent event) {
        if (config.getBoolean(Configs.SAVING_PLAYER_INVENTORY))
            playerManager.saveInventory(event.getPlayer().getUniqueId());
    }

}
