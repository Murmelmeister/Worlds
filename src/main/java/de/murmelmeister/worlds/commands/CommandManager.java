package de.murmelmeister.worlds.commands;

import de.murmelmeister.worlds.InitPlugin;
import de.murmelmeister.worlds.api.config.WorldManager;
import de.murmelmeister.worlds.configs.Message;
import de.murmelmeister.worlds.utils.HexColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

public abstract class CommandManager extends Commands implements TabExecutor {

    public CommandManager(InitPlugin init) {
        super(init);
    }

    public void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(HexColor.format(getMessage().getPrefix() + message));
    }

    public WorldManager getWorldManager() {
        return getInit().getWorldManager();
    }

    public Message getMessage() {
        return getInit().getMessage();
    }
}
