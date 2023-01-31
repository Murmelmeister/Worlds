package de.murmelmeister.worlds.listener.listeners;

import de.murmelmeister.worlds.Main;
import de.murmelmeister.worlds.listener.Listeners;

public class PlayerEnderChestListener extends Listeners {
    public PlayerEnderChestListener(Main main) {
        super(main);
    }
/*
    public ListenerPlayerEnderChest(InitPlugin init) {
        super(init);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void handleOpenInventory(InventoryOpenEvent event) {
        if (event.getPlayer().getInventory().getType() == InventoryType.ENDER_CHEST) return;
        getInit().getPlayerManager().loadEnderChest(event.getPlayer().getUniqueId());
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void handleCloseInventory(InventoryCloseEvent event) {
        if (event.getPlayer().getInventory().getType() == InventoryType.ENDER_CHEST) return;
        getInit().getPlayerManager().saveEnderChest(event.getPlayer().getUniqueId());
    }
*/
}
