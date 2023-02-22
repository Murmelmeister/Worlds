package de.murmelmeister.worlds.commands;

import de.murmelmeister.worlds.InitPlugin;
import org.bukkit.command.TabExecutor;

import java.util.Objects;

public class Commands {

    private InitPlugin init;

    public Commands(InitPlugin init) {
        setInit(init);
    }

    public void registerCommands() {
        addCommand("worlds", new CommandWorlds(getInit()));
    }

    private void addCommand(String name, TabExecutor executor) {
        Objects.requireNonNull(getInit().getInstance().getCommand(name)).setExecutor(executor);
        Objects.requireNonNull(getInit().getInstance().getCommand(name)).setTabCompleter(executor);
    }

    public InitPlugin getInit() {
        return init;
    }

    public void setInit(InitPlugin init) {
        this.init = init;
    }
}
