package com.voltskiya.misc.drops;

import com.voltskiya.lib.configs.data.AppleConfigsDatabase;
import com.voltskiya.lib.configs.data.config.AppleConfig;
import com.voltskiya.lib.configs.factory.AppleConfigLike;
import com.voltskiya.lib.AbstractModule;
import apple.mc.utilities.data.serialize.GsonSerializeMC;
import com.voltskiya.misc.drops.block.BlockBreakTableList;
import com.voltskiya.misc.drops.block.BlockBreaker;
import com.voltskiya.misc.drops.items.ItemFlagsCommand;
import com.voltskiya.misc.drops.items.SpecialItemDatabase;
import com.voltskiya.misc.drops.xp.LootTableKillListener;
import com.voltskiya.misc.drops.xp.LootXpTableManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;

public class PluginDrops extends AbstractModule {

    private static PluginDrops instance;
    private static final String SPECIAL_ITEMS_CONFIG = "SpecialItemFlags";
    private static final String LOOT_BLOCK_CONFIG = "LoottableBlock";
    private static final String LOOT_XP_CONFIG = "LoottableXp";

    public static PluginDrops get() {
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
        new DropsCommand();
        new ItemFlagsCommand();
    }

    @Override
    public String getName() {
        return "Drops";
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
