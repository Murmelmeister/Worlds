package de.murmelmeister.worlds;

public final class Worlds extends Main {

    private InitPlugin init;

    @Override
    public void onDisable() {
        getInit().onDisable();
    }

    @Override
    public void onEnable() {
        getInit().onEnable();
    }

    @Override
    public void onLoad() {
        setInit(new InitPlugin(this));
    }

    public InitPlugin getInit() {
        return init;
    }

    public void setInit(InitPlugin init) {
        this.init = init;
    }
}
