package de.murmelmeister.worlds;

import org.bukkit.plugin.java.JavaPlugin;

public abstract class Main extends JavaPlugin {

    private String prefix;

    public abstract void onDisable();

    public abstract void onEnable();

    public abstract void onLoad();

    public abstract void init();

    protected void handleDisableMessage() {
        this.getSLF4JLogger().info(getPluginName() + " » Plugin is disabled! Version: " + getVersion() + " by " + getAuthor());
    }

    protected void handleEnableMessage() {
        this.getSLF4JLogger().info(getPluginName() + " » Plugin is enabled! Version: " + getVersion() + " by " + getAuthor());
    }

    public String getVersion() {
        return "a1.0.0";
    }

    public String getAuthor() {
        return "Murmelmeister";
    }

    public String getPluginName() {
        return "Worlds";
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

}
