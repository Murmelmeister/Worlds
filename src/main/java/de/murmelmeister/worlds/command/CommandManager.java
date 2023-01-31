package de.murmelmeister.worlds.command;

import de.murmelmeister.worlds.Main;
import de.murmelmeister.worlds.api.WorldManager;
import de.murmelmeister.worlds.configs.DefaultConfig;
import de.murmelmeister.worlds.configs.MessageConfig;
import de.murmelmeister.worlds.utils.HexColor;
import de.murmelmeister.worlds.utils.configs.Configs;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

public abstract class CommandManager extends Commands implements TabExecutor {
    public final DefaultConfig config;
    public final MessageConfig message;
    public final WorldManager worldManager;

    public CommandManager(Main main) {
        super(main);
        this.config = main.getDefaultConfig();
        this.message = main.getMessageConfig();
        this.worldManager = main.getWorldManager();
    }

    public void sendMessage(CommandSender sender, String message) {
        if (config.getBoolean(Configs.PREFIX_ENABLE))
            sender.sendMessage(HexColor.format(this.message.prefix() + message));
        else sender.sendMessage(HexColor.format(message));
    }
}
