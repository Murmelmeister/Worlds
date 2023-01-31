package de.murmelmeister.worlds;

import de.murmelmeister.worlds.api.WorldID;
import de.murmelmeister.worlds.api.WorldManager;
import de.murmelmeister.worlds.command.Commands;
import de.murmelmeister.worlds.configs.DefaultConfig;
import de.murmelmeister.worlds.configs.MessageConfig;
import de.murmelmeister.worlds.listener.Listeners;
import de.murmelmeister.worlds.utils.InfoUtil;

public class Main {
    private final Worlds instance;

    private final DefaultConfig defaultConfig;
    private final MessageConfig messageConfig;
    private final WorldID worldID;
    private final WorldManager worldManager;

    private final Listeners listeners;
    private final Commands commands;

    public Main(Worlds instance) {
        this.instance = instance;
        this.defaultConfig = new DefaultConfig(this);
        this.messageConfig = new MessageConfig(this);
        this.worldID = new WorldID(this);
        this.worldManager = new WorldManager(this);
        this.listeners = new Listeners(this);
        this.commands = new Commands(this);
    }

    public void disable() {
        worldManager.unloadWorlds();
        InfoUtil.disableMessage(instance.getServer());
    }

    public void enable() {
        defaultConfig.register();
        messageConfig.register();
        worldID.loadListID();
        worldManager.loadDefaultWorlds();
        worldManager.loadWorlds();
        listeners.register();
        commands.register();
        InfoUtil.enableMessage(instance.getServer());
    }

    public Worlds getInstance() {
        return instance;
    }

    public DefaultConfig getDefaultConfig() {
        return defaultConfig;
    }

    public MessageConfig getMessageConfig() {
        return messageConfig;
    }

    public WorldID getWorldID() {
        return worldID;
    }

    public WorldManager getWorldManager() {
        return worldManager;
    }
}
