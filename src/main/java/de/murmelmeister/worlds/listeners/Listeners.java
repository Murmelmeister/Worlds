package de.murmelmeister.worlds.listeners;

import de.murmelmeister.worlds.Worlds;
import de.murmelmeister.worlds.api.config.PlayerManager;
import org.bukkit.event.Listener;

public class Listeners implements Listener {

    protected Worlds instance = Worlds.getInstance();

    protected PlayerManager playerManager = this.instance.getInitWorlds().getPlayerManager();

    public void registerListeners() {
        setListener(new ListenerPlayerInventory());
        setListener(new ListenerPlayerEnderChest());
    }

    private void setListener(Listener listener) {
        this.instance.getServer().getPluginManager().registerEvents(listener, this.instance);
    }

}
