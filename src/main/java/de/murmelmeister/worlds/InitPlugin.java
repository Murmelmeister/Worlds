package de.murmelmeister.worlds;

import de.murmelmeister.worlds.api.config.PlayerManager;
import de.murmelmeister.worlds.api.config.WorldManager;
import de.murmelmeister.worlds.commands.Commands;
import de.murmelmeister.worlds.configs.Config;
import de.murmelmeister.worlds.configs.Message;
import de.murmelmeister.worlds.configs.Permission;
import de.murmelmeister.worlds.listeners.Listeners;

public class InitPlugin {

    private Worlds instance;

    private WorldManager worldManager;
    private PlayerManager playerManager;

    private Config config;
    private Message message;
    private Permission permission;

    private Listeners listeners;
    private Commands commands;

    public InitPlugin(Worlds instance) {
        setInstance(instance);
    }

    public void onDisable() {
        getWorldManager().unloadWorlds();
        getInstance().handleDisableMessage();
    }

    public void onEnable() {
        init();
        getWorldManager().loadDefaultWorlds();
        getWorldManager().loadWorlds();
        getListeners().registerListeners();
        getCommands().registerCommands();
        getInstance().handleEnableMessage();
    }

    private void init() {
        setWorldManager(new WorldManager(this));
        setPlayerManager(new PlayerManager(this));
        setConfig(new Config(this));
        setMessage(new Message(this));
        setPermission(new Permission(this));
        setListeners(new Listeners(this));
        setCommands(new Commands(this));
    }

    public Worlds getInstance() {
        return instance;
    }

    public void setInstance(Worlds instance) {
        this.instance = instance;
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

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public Listeners getListeners() {
        return listeners;
    }

    public void setListeners(Listeners listeners) {
        this.listeners = listeners;
    }

    public Commands getCommands() {
        return commands;
    }

    public void setCommands(Commands commands) {
        this.commands = commands;
    }
}
