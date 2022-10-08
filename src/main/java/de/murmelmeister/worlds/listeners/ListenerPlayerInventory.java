package de.murmelmeister.worlds.listeners;

import de.murmelmeister.worlds.InitPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ListenerPlayerInventory extends Listeners {

    public ListenerPlayerInventory(InitPlugin init) {
        super(init);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void handlePlayerJoin(PlayerJoinEvent event) {
        getInit().getPlayerManager().createAccount(event.getPlayer().getUniqueId());
        getInit().getPlayerManager().loadInventory(event.getPlayer().getUniqueId());
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void handlePlayerQuit(PlayerQuitEvent event) {
        getInit().getPlayerManager().saveInventory(event.getPlayer().getUniqueId());
    }

}
