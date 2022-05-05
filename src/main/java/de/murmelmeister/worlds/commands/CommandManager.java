package de.murmelmeister.worlds.commands;

import de.murmelmeister.worlds.Worlds;
import org.bukkit.command.TabExecutor;

import java.util.Objects;

public class CommandManager {

    private final Worlds instance = Worlds.getInstance();

    public void registerCommands() {
        setCommand("worlds", new CommandWorlds());
    }

    private void setCommand(String nameCommand, TabExecutor command) {
        Objects.requireNonNull(this.instance.getCommand(nameCommand)).setExecutor(command);
        Objects.requireNonNull(this.instance.getCommand(nameCommand)).setTabCompleter(command);
    }

}
