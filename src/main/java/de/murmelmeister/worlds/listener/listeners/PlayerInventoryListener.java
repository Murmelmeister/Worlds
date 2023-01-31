package de.murmelmeister.worlds.listener.listeners;

import de.murmelmeister.worlds.Main;
import de.murmelmeister.worlds.listener.Listeners;

public class PlayerInventoryListener extends Listeners {
    public PlayerInventoryListener(Main main) {
        super(main);
    }
/*
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
*/
}
