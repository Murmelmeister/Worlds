package de.murmelmeister.worlds;

public final class Worlds extends Main {

    private static Worlds instance;

    private InitWorlds initWorlds;

    @Override
    public void onDisable() {
        initWorlds.unloadWorlds();
        handleDisableMessage();
    }

    @Override
    public void onEnable() {
        init();
        handleEnableMessage();
    }

    @Override
    public void onLoad() {
        setInstance(this);
    }

    @Override
    public void init() {
        setInitWorlds(new InitWorlds());
        initWorlds.init();
        initWorlds.loadWorlds();
    }

    public static Worlds getInstance() {
        return instance;
    }

    public static void setInstance(Worlds instance) {
        Worlds.instance = instance;
    }

    public InitWorlds getInitWorlds() {
        return initWorlds;
    }

    public void setInitWorlds(InitWorlds initWorlds) {
        this.initWorlds = initWorlds;
    }
}
