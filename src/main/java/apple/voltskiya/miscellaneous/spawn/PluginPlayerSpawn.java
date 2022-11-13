package apple.voltskiya.miscellaneous.spawn;

import apple.lib.configs.factory.AppleConfigLike;
import apple.lib.pmc.AppleModule;
import java.util.List;

public class PluginPlayerSpawn extends AppleModule {

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
