package de.murmelmeister.worlds;

import org.bukkit.plugin.java.JavaPlugin;

public abstract class Main extends JavaPlugin {

    public abstract void onDisable();

    public abstract void onEnable();

    public abstract void onLoad();

    public void handleDisableMessage() {
        getServer().getConsoleSender().sendMessage(String.format("§3%s §c» §7Plugin is§c disabled§7! §7Version: §b%s §7by §b%s", getPluginName(), getVersion(), getAuthor()));
    }

    public void handleEnableMessage() {
        getServer().getConsoleSender().sendMessage(String.format("§3%s §a» §7Plugin is§a enabled§7! §7Version: §b%s §7by §b%s", getPluginName(), getVersion(), getAuthor()));
    }

    public String getAuthor() {
        return "Murmelmeister";
    }

    public String getVersion() {
        return "0.0.1-ALPHA-SNAPSHOT";
    }

    public String getPluginName() {
        return "Worlds";
    }
}
