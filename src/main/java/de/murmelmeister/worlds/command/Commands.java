package de.murmelmeister.worlds.command;

import de.murmelmeister.worlds.Main;
import de.murmelmeister.worlds.Worlds;
import de.murmelmeister.worlds.command.commands.WorldsCommand;
import org.bukkit.command.TabExecutor;

import java.util.Objects;

public class Commands {
    public final Main main;
    public final Worlds instance;

    public Commands(Main main) {
        this.main = main;
        this.instance = main.getInstance();
    }

    public void register() {
        addCommand("worlds", new WorldsCommand(main));
    }

    private void addCommand(String name, TabExecutor executor) {
        Objects.requireNonNull(instance.getCommand(name)).setExecutor(executor);
        Objects.requireNonNull(instance.getCommand(name)).setTabCompleter(executor);
    }
}
