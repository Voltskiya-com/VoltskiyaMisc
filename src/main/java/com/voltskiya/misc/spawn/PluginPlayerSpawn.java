package com.voltskiya.misc.spawn;

import com.voltskiya.lib.configs.factory.AppleConfigLike;
import com.voltskiya.lib.AbstractModule;
import java.util.List;

public class PluginPlayerSpawn extends AbstractModule {

    private static PluginPlayerSpawn instance;

    public static PluginPlayerSpawn get() {
        return instance;
    }

    public PluginPlayerSpawn() {
        instance = this;
    }

    @Override
    public void enable() {
        PlayerSpawnDatabase.load();
        new CommandSpawnJoin();
        new PlayerDeathSpawnListener();
    }

    @Override
    public String getName() {
        return "PlayerSpawn";
    }

    @Override
    public List<AppleConfigLike> getConfigs() {
        return List.of(config(PlayerSpawnConfig.class, "PlayerSpawnConfig"));
    }
}
