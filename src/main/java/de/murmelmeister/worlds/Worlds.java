package de.murmelmeister.worlds;

import de.murmelmeister.worlds.api.config.PlayerManager;
import de.murmelmeister.worlds.api.config.WorldManager;
import de.murmelmeister.worlds.commands.CommandManager;
import de.murmelmeister.worlds.files.ConfigFile;
import de.murmelmeister.worlds.files.MessageFile;
import de.murmelmeister.worlds.files.PermissionFile;
import de.murmelmeister.worlds.listeners.Listeners;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;

public final class Worlds extends JavaPlugin {
    private ConfigFile configFile;
    private MessageFile messageFile;
    private PermissionFile permissionFile;
    private WorldManager worldManager;
    private PlayerManager playerManager;

    @Override
    public void onDisable() {
        worldManager.unloadWorlds();
    }

    @Override
    public void onEnable() {
        worldManager.loadDefaultWorlds();
        worldManager.loadWorlds();
        Listeners.register(this);
        CommandManager.register(this);
    }

    @Override
    public void onLoad() {
        final Logger logger = getSLF4JLogger();
        final Server server = getServer();
        this.configFile = new ConfigFile(logger);
        this.messageFile = new MessageFile(logger);
        this.permissionFile = new PermissionFile(logger);
        this.worldManager = new WorldManager(logger, server);
        this.playerManager = new PlayerManager(logger, server);
    }

    public static Worlds getInstance() {
        return getPlugin(Worlds.class);
    }

    public static String getMainPath() {
        return "./plugins/" + Worlds.class.getSimpleName();
    }

    public ConfigFile getConfigFile() {
        return configFile;
    }

    public MessageFile getMessageFile() {
        return messageFile;
    }

    public PermissionFile getPermissionFile() {
        return permissionFile;
    }

    public WorldManager getWorldManager() {
        return worldManager;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }
}
