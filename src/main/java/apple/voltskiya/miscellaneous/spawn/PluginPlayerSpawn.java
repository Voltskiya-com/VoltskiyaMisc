package apple.voltskiya.miscellaneous.spawn;

import apple.configs.factory.AppleConfigLike;
import apple.lib.pmc.PluginModule;
import apple.mc.utilities.data.serialize.GsonSerializeLocation;
import apple.mc.utilities.data.serialize.GsonSerializeMC;
import com.google.gson.GsonBuilder;
import java.util.List;

public class PluginPlayerSpawn extends PluginModule {

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
        GsonBuilder gson = new GsonBuilder();
        GsonSerializeLocation.Options options = new GsonSerializeLocation.Options(true, true, true);
        GsonSerializeMC.registerLocationTypeAdapter(gson, options);
        return List.of(configYaml(PlayerSpawnConfig.class, "PlayerSpawnConfig"));
    }

    public void saveSpawnPoints() {
        PlayerSpawnDatabase.get().save();
    }
}
