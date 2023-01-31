package de.murmelmeister.worlds.listener;

import de.murmelmeister.worlds.Main;
import de.murmelmeister.worlds.Worlds;
import de.murmelmeister.worlds.api.WorldManager;
import de.murmelmeister.worlds.configs.DefaultConfig;
import de.murmelmeister.worlds.configs.MessageConfig;
import de.murmelmeister.worlds.listener.listeners.PlayerEnderChestListener;
import de.murmelmeister.worlds.listener.listeners.PlayerInventoryListener;
import de.murmelmeister.worlds.utils.HexColor;
import de.murmelmeister.worlds.utils.configs.Configs;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;

public class Listeners implements Listener {
    public final Main main;
    public final Worlds instance;

    public final DefaultConfig config;
    public final MessageConfig message;
    public final WorldManager worldManager;

    public Listeners(Main main) {
        this.main = main;
        this.instance = main.getInstance();
        this.config = main.getDefaultConfig();
        this.message = main.getMessageConfig();
        this.worldManager = main.getWorldManager();
    }

    public void register() {
        addListener(new PlayerInventoryListener(main));
        addListener(new PlayerEnderChestListener(main));
    }

    private void addListener(Listener listener) {
        instance.getServer().getPluginManager().registerEvents(listener, instance);
    }

    public void sendMessage(CommandSender sender, String message) {
        if (config.getBoolean(Configs.PREFIX_ENABLE))
            sender.sendMessage(HexColor.format(this.message.prefix() + message));
        else sender.sendMessage(HexColor.format(message));
    }
}
