package de.murmelmeister.worlds.listeners;

import de.murmelmeister.worlds.InitPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;

public class ListenerPlayerEnderChest extends Listeners {

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

}
