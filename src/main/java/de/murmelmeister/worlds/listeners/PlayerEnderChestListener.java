package de.murmelmeister.worlds.listeners;

import de.murmelmeister.worlds.Worlds;
import de.murmelmeister.worlds.utils.configs.Configs;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;

public final class PlayerEnderChestListener extends Listeners {
    public PlayerEnderChestListener(Worlds plugin) {
        super(plugin);
    }

    @EventHandler
    public void handleOpenInventory(InventoryOpenEvent event) {
        Player player = (Player) event.getPlayer();
        if (config.getBoolean(Configs.SAVING_PLAYER_ENDER_CHEST))
            if (player.getInventory().getType() == InventoryType.ENDER_CHEST)
                playerManager.loadEnderChest(player.getUniqueId());
    }

    @EventHandler
    public void handleCloseInventory(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        if (config.getBoolean(Configs.SAVING_PLAYER_ENDER_CHEST))
            if (player.getInventory().getType() == InventoryType.ENDER_CHEST)
                playerManager.saveEnderChest(player.getUniqueId());
    }
}
