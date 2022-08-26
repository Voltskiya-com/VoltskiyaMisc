package apple.voltskiya.miscellaneous.loot_tables;

import apple.configs.data.config.AppleConfig;
import apple.configs.factory.AppleConfigLike;
import apple.configs.registered.AppleConfigsDatabase;
import apple.lib.pmc.PluginModule;
import apple.mc.utilities.data.serialize.GsonSerializeMC;
import apple.voltskiya.miscellaneous.loot_tables.block.BlockBreakTableList;
import apple.voltskiya.miscellaneous.loot_tables.block.BlockBreaker;
import apple.voltskiya.miscellaneous.loot_tables.items.ItemFlagsCommand;
import apple.voltskiya.miscellaneous.loot_tables.items.SpecialItemDatabase;
import apple.voltskiya.miscellaneous.loot_tables.xp.LootTableKillListener;
import apple.voltskiya.miscellaneous.loot_tables.xp.LootXpTableManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;

public class PluginLootTables extends PluginModule {

    private static PluginLootTables instance;
    private static final String SPECIAL_ITEMS_CONFIG = "SpecialItemFlags";
    private static final String LOOT_BLOCK_CONFIG = "LoottableBlock";
    private static final String LOOT_XP_CONFIG = "LoottableXp";

    public static PluginLootTables get() {
        return instance;
    }

    @Override
    public void init() {
        instance = this;
    }

    @Override
    public void enable() {
        new LootTableKillListener();
        new BlockBreaker();
        new LootTableCommand();
        new ItemFlagsCommand();
    }

    @Override
    public String getName() {
        return "LootTables";
    }

    @Override
    public List<AppleConfigLike> getConfigs() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        GsonSerializeMC.registerNBTTagTypeAdapter(gsonBuilder);
        GsonSerializeMC.registerEntityTypeTypeAdapter(gsonBuilder);
        GsonSerializeMC.registerMinecraftKeyTypeAdapter(gsonBuilder);
        Gson gson = gsonBuilder.create();
        return List.of(configJson(BlockBreakTableList.class, LOOT_BLOCK_CONFIG).asJson(gson),
            configJson(LootXpTableManager.class, LOOT_XP_CONFIG).asJson(gson),
            configJson(SpecialItemDatabase.class, SPECIAL_ITEMS_CONFIG).asJson(gson)
        );
    }

    public void saveLootTableBlock() {
        save(LOOT_BLOCK_CONFIG);
    }

    public void saveLootTableXp() {
        save(LOOT_XP_CONFIG);
    }

    public void saveSpecialItems() {
        save(SPECIAL_ITEMS_CONFIG);
    }

    private void save(String configName) {
        AppleConfig<?> config = AppleConfigsDatabase.get().findConfig(List.of(configName));
        if (config != null)
            config.save();
    }
}
