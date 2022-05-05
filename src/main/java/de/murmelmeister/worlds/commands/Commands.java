package de.murmelmeister.worlds.commands;

import de.murmelmeister.worlds.Worlds;
import de.murmelmeister.worlds.api.config.WorldManager;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

public abstract class Commands implements TabExecutor {

    protected Worlds instance = Worlds.getInstance();

    protected WorldManager worldManager = this.instance.getInitWorlds().getWorldManager();

    protected void setSendMessage(CommandSender sender, String message) {
        sender.sendMessage(this.instance.getPrefix() + message);
    }

}
