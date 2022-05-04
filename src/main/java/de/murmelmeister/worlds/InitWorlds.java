package de.murmelmeister.worlds;

import de.murmelmeister.worlds.api.config.WorldManager;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.event.Listener;

public class InitWorlds implements Listener {

    private WorldManager worldManager;

    public void init() {
        setWorldManager(new WorldManager());
    }

    public void loadWorlds() {
        for (String worldName : getWorldManager().getConfig().getStringList("Worlds.List")) {
            getWorldManager().loadWorld(worldName, World.Environment.NORMAL, WorldType.NORMAL);
        }
    }

    public void unloadWorlds() {
        for (String worldName : getWorldManager().getConfig().getStringList("Worlds.List")) {
            getWorldManager().unloadWorld(worldName, World.Environment.NORMAL, WorldType.NORMAL);
        }
    }

    public WorldManager getWorldManager() {
        return worldManager;
    }

    public void setWorldManager(WorldManager worldManager) {
        this.worldManager = worldManager;
    }
}
