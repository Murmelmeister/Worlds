package de.murmelmeister.worlds;

import de.murmelmeister.worlds.api.config.PlayerManager;
import de.murmelmeister.worlds.api.config.WorldManager;
import de.murmelmeister.worlds.commands.CommandManager;
import de.murmelmeister.worlds.listeners.Listeners;
import org.bukkit.World;

public class InitWorlds {

    private WorldManager worldManager;
    private PlayerManager playerManager;
    private Listeners listeners;
    private CommandManager commandManager;

    public void init() {
        Worlds.getInstance().setPrefix("§8[§3Worlds§8] §r");
        setWorldManager(new WorldManager());
        setPlayerManager(new PlayerManager());
        setListeners(new Listeners());
        setCommandManager(new CommandManager());
    }

    public void loadWorlds() {
        for (String worldName : getWorldManager().getConfig().getStringList("Worlds.List")) {
            getWorldManager().loadWorld(worldName, World.Environment.NORMAL);
        }
    }

    public void unloadWorlds() {
        for (String worldName : getWorldManager().getConfig().getStringList("Worlds.List")) {
            getWorldManager().unloadWorld(worldName, World.Environment.NORMAL);
        }
    }

    public WorldManager getWorldManager() {
        return worldManager;
    }

    public void setWorldManager(WorldManager worldManager) {
        this.worldManager = worldManager;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public void setPlayerManager(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    public Listeners getListeners() {
        return listeners;
    }

    public void setListeners(Listeners listeners) {
        this.listeners = listeners;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public void setCommandManager(CommandManager commandManager) {
        this.commandManager = commandManager;
    }
}
