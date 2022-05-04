package de.murmelmeister.worlds.api.config;

import de.murmelmeister.worlds.Worlds;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class PlayerManager {

    private final File folder = new File("plugins//Worlds//PlayerManager//");
    private File file;
    private YamlConfiguration config;

    private final Logger logger = Worlds.getInstance().getSLF4JLogger();
    private final Worlds instance = Worlds.getInstance();

    public void createConfig(UUID uuid) {
        if (!(getFolder().exists())) {
            boolean aBoolean = getFolder().mkdir();
            if (!(aBoolean)) logger.warn("Could not create the same folder.");
        }

        setFile(new File(getFolder(), uuid + ".yml"));

        if (!(file.exists())) {
            try {
                boolean aBoolean = file.createNewFile();
                if (!(aBoolean)) logger.warn(String.format("Could not create the same %s.yml.", uuid));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        setConfig(YamlConfiguration.loadConfiguration(file));
    }

    public void saveConfig() {
        try {
            getConfig().save(getFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean hasAccount(UUID uuid) {
        createConfig(uuid);
        return (this.getConfig().getString("Inventory") != null)
                && (this.getConfig().getString("EnderChest") != null);
    }

    public void createNewAccount(UUID uuid) {
        setInventoryConfig(uuid);
        setEnderChestConfig(uuid);
    }

    @SuppressWarnings("unchecked")
    public void loadEnderChest(UUID uuid) {
        createConfig(uuid);
        Player player = this.instance.getServer().getPlayer(uuid);
        if (player == null) return;
        player.getEnderChest().setContents(((List<ItemStack>) Objects.requireNonNull(this.getConfig().get("EnderChest.Contents"))).toArray(ItemStack[]::new));
        player.getEnderChest().setStorageContents(((List<ItemStack>) Objects.requireNonNull(this.getConfig().get("EnderChest.StorageContents"))).toArray(ItemStack[]::new));
    }

    public void saveEnderChest(UUID uuid) {
        setEnderChestConfig(uuid);
    }

    @SuppressWarnings("unchecked")
    public void loadInventory(UUID uuid) {
        createConfig(uuid);
        Player player = this.instance.getServer().getPlayer(uuid);
        if (player == null) return;
        player.getInventory().setArmorContents(((List<ItemStack>) Objects.requireNonNull(this.getConfig().get("Inventory.ArmorContents"))).toArray(ItemStack[]::new));
        player.getInventory().setContents(((List<ItemStack>) Objects.requireNonNull(this.getConfig().get("Inventory.Contents"))).toArray(ItemStack[]::new));
        player.getInventory().setExtraContents(((List<ItemStack>) Objects.requireNonNull(this.getConfig().get("Inventory.ExtraContents"))).toArray(ItemStack[]::new));
        player.getInventory().setStorageContents(((List<ItemStack>) Objects.requireNonNull(this.getConfig().get("Inventory.StorageContents"))).toArray(ItemStack[]::new));
    }

    public void saveInventory(UUID uuid) {
        setInventoryConfig(uuid);
    }

    private void setEnderChestConfig(UUID uuid) {
        createConfig(uuid);
        Player player = this.instance.getServer().getPlayer(uuid);
        if (player == null) return;
        this.getConfig().set("EnderChest.Contents", player.getEnderChest().getContents());
        this.getConfig().set("EnderChest.StorageContents", player.getEnderChest().getStorageContents());
        saveConfig();
    }

    private void setInventoryConfig(UUID uuid) {
        createConfig(uuid);
        Player player = this.instance.getServer().getPlayer(uuid);
        if (player == null) return;
        this.getConfig().set("Inventory.ArmorContents", player.getInventory().getArmorContents());
        this.getConfig().set("Inventory.Contents", player.getInventory().getContents());
        this.getConfig().set("Inventory.ExtraContents", player.getInventory().getExtraContents());
        this.getConfig().set("Inventory.StorageContents", player.getInventory().getStorageContents());
        saveConfig();
    }

    public File getFolder() {
        return folder;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public YamlConfiguration getConfig() {
        return config;
    }

    public void setConfig(YamlConfiguration config) {
        this.config = config;
    }

}
