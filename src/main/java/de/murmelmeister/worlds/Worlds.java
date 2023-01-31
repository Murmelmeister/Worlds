package de.murmelmeister.worlds;

import org.bukkit.plugin.java.JavaPlugin;

public final class Worlds extends JavaPlugin {
    private Main main;

    @Override
    public void onLoad() {
        this.main = new Main(this);
    }

    @Override
    public void onDisable() {
        main.disable();
    }

    @Override
    public void onEnable() {
        main.enable();
    }
}
