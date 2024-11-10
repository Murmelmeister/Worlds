package de.murmelmeister.worlds.api.config;

import de.murmelmeister.worlds.Worlds;
import de.murmelmeister.worlds.utils.FileUtil;
import org.bukkit.Server;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public final class PlayerManager {
    private final Logger logger;
    private final Server server;
    private File file;
    private YamlConfiguration config;

    public PlayerManager(Logger logger, Server server) {
        this.logger = logger;
        this.server = server;
    }

    private void create(UUID uuid) {
        this.file = FileUtil.createFile(logger, Worlds.getMainPath() + "/PlayerData/", uuid.toString() + ".yml");
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    private void save() {
        try {
            this.config.save(file);
        } catch (IOException e) {
            logger.error("Error while saving config file", e);
        }
    }

    public void createAccount(UUID uuid) {
        create(uuid);
        if (config.getString("Inventory") == null) setInventoryConfig(uuid);
        if (config.getString("EnderChest") == null) setEnderChestConfig(uuid);
        save();
    }

    @SuppressWarnings("unchecked")
    public void loadEnderChest(UUID uuid) {
        createAccount(uuid);
        Player player = server.getPlayer(uuid);
        if (player == null) return;
        player.getEnderChest().setContents(((List<ItemStack>) Objects.requireNonNull(config.get("EnderChest.Contents"))).toArray(ItemStack[]::new));
        player.getEnderChest().setStorageContents(((List<ItemStack>) Objects.requireNonNull(config.get("EnderChest.StorageContents"))).toArray(ItemStack[]::new));
    }

    public void saveEnderChest(UUID uuid) {
        setEnderChestConfig(uuid);
    }

    @SuppressWarnings("unchecked")
    public void loadInventory(UUID uuid) {
        createAccount(uuid);
        Player player = server.getPlayer(uuid);
        if (player == null) return;
        player.getInventory().setArmorContents(((List<ItemStack>) Objects.requireNonNull(config.get("Inventory.ArmorContents"))).toArray(ItemStack[]::new));
        player.getInventory().setContents(((List<ItemStack>) Objects.requireNonNull(config.get("Inventory.Contents"))).toArray(ItemStack[]::new));
        player.getInventory().setExtraContents(((List<ItemStack>) Objects.requireNonNull(config.get("Inventory.ExtraContents"))).toArray(ItemStack[]::new));
        player.getInventory().setStorageContents(((List<ItemStack>) Objects.requireNonNull(config.get("Inventory.StorageContents"))).toArray(ItemStack[]::new));
    }

    public void saveInventory(UUID uuid) {
        setInventoryConfig(uuid);
    }

    private void setEnderChestConfig(UUID uuid) {
        createAccount(uuid);
        Player player = server.getPlayer(uuid);
        if (player == null) return;
        config.set("EnderChest.Contents", player.getEnderChest().getContents());
        config.set("EnderChest.StorageContents", player.getEnderChest().getStorageContents());
        save();
    }

    private void setInventoryConfig(UUID uuid) {
        createAccount(uuid);
        Player player = server.getPlayer(uuid);
        if (player == null) return;
        config.set("Inventory.ArmorContents", player.getInventory().getArmorContents());
        config.set("Inventory.Contents", player.getInventory().getContents());
        config.set("Inventory.ExtraContents", player.getInventory().getExtraContents());
        config.set("Inventory.StorageContents", player.getInventory().getStorageContents());
        save();
    }
}
