package de.murmelmeister.worlds.listeners;

import de.murmelmeister.worlds.InitPlugin;
import org.bukkit.event.Listener;

public class Listeners implements Listener {

    private InitPlugin init;

    public Listeners(InitPlugin init) {
        setInit(init);
    }

    public void registerListeners() {
        setListener(new ListenerPlayerInventory(getInit()));
        setListener(new ListenerPlayerEnderChest(getInit()));
    }

    private void setListener(Listener listener) {
        getInit().getInstance().getServer().getPluginManager().registerEvents(listener, getInit().getInstance());
    }

    public InitPlugin getInit() {
        return init;
    }

    public void setInit(InitPlugin init) {
        this.init = init;
    }
}
