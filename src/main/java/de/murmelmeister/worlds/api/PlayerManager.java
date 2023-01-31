package de.murmelmeister.worlds.api;

public class PlayerManager {
/*
    private InitPlugin init;

    private File folder;
    private File file;
    private YamlConfiguration config;

    public PlayerManager(InitPlugin init) {
        setInit(init);
    }

    public void createConfig(UUID uuid) {
        String fileName = uuid + ".yml";
        setFolder(new File(String.format("plugins//%s//PlayerManager", getInit().getInstance().getPluginName())));
        if (!(getFolder().exists())) {
            boolean aBoolean = getFolder().mkdir();
            if (!(aBoolean))
                getInit().getInstance().getSLF4JLogger().warn("The plugin can not create a second folder.");
        }
        setFile(new File(getFolder(), fileName));
        if (!(getFile().exists())) {
            try {
                boolean aBoolean = getFile().createNewFile();
                if (!(aBoolean))
                    getInit().getInstance().getSLF4JLogger().warn(String.format("The plugin can not create the file '%s'.", fileName));
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

    public void createAccount(UUID uuid) {
        createConfig(uuid);
        if (this.getConfig().getString("Inventory") == null) setInventoryConfig(uuid);
        if (this.getConfig().getString("EnderChest") == null) setEnderChestConfig(uuid);
        saveConfig();
    }

    @SuppressWarnings("unchecked")
    public void loadEnderChest(UUID uuid) {
        createConfig(uuid);
        Player player = getInit().getInstance().getServer().getPlayer(uuid);
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
        Player player = getInit().getInstance().getServer().getPlayer(uuid);
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
        Player player = getInit().getInstance().getServer().getPlayer(uuid);
        if (player == null) return;
        this.getConfig().set("EnderChest.Contents", player.getEnderChest().getContents());
        this.getConfig().set("EnderChest.StorageContents", player.getEnderChest().getStorageContents());
        saveConfig();
    }

    private void setInventoryConfig(UUID uuid) {
        createConfig(uuid);
        Player player = getInit().getInstance().getServer().getPlayer(uuid);
        if (player == null) return;
        this.getConfig().set("Inventory.ArmorContents", player.getInventory().getArmorContents());
        this.getConfig().set("Inventory.Contents", player.getInventory().getContents());
        this.getConfig().set("Inventory.ExtraContents", player.getInventory().getExtraContents());
        this.getConfig().set("Inventory.StorageContents", player.getInventory().getStorageContents());
        saveConfig();
    }

    public InitPlugin getInit() {
        return init;
    }

    public void setInit(InitPlugin init) {
        this.init = init;
    }

    public File getFolder() {
        return folder;
    }

    public void setFolder(File folder) {
        this.folder = folder;
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
    }*/
}
