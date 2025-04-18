package com.voltskiya.misc.drops.xp;

import com.voltskiya.misc.drops.DropsModule;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.minecraft.world.entity.EntityType;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.jetbrains.annotations.Nullable;

public class LootXpTableManager {

    private static LootXpTableManager instance;
    private final HashMap<String, Integer> tagXpTables = new HashMap<>();
    private final HashMap<EntityType<?>, Integer> entityXpTables = new HashMap<>();

    public LootXpTableManager() {
        instance = this;
    }

    @Nullable
    public static Integer get(String tag) {
        return instance.tagXpTables.get(tag);
    }

    public static Integer get(CraftEntity entity) {
        EntityType<?> type = entity.getHandle().getType();
        return get(type);
    }

    public static Integer get(EntityType<?> entityType) {
        return instance.entityXpTables.get(entityType);
    }

    public static void add(String tag, int xp) {
        instance.tagXpTables.put(tag, xp);
        save();
    }

    public static void remove(String tag) {
        instance.tagXpTables.remove(tag);
        save();
    }

    private static void save() {
        DropsModule.get().saveLootTableXp();
    }

    public static void add(EntityType<?> entityType, int xp) {
        instance.entityXpTables.put(entityType, xp);
        save();
    }

    public static void remove(EntityType<?> entityType) {
        instance.entityXpTables.remove(entityType);
        save();
    }

    public static List<String> getTags() {
        return new ArrayList<>(instance.tagXpTables.keySet());
    }
}
