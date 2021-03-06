package de.murmelmeister.worlds.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ListenerPlayerInventory extends Listeners {

    @EventHandler(priority = EventPriority.HIGH)
    public void handlePlayerJoin(PlayerJoinEvent event) {
        if (!(this.playerManager.hasAccount(event.getPlayer().getUniqueId())))
            this.playerManager.createNewAccount(event.getPlayer().getUniqueId());
        this.playerManager.loadInventory(event.getPlayer().getUniqueId());
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void handlePlayerQuit(PlayerQuitEvent event) {
        this.playerManager.saveInventory(event.getPlayer().getUniqueId());
    }

}
