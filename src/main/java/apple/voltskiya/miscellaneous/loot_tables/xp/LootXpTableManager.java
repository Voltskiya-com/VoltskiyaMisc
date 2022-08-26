package apple.voltskiya.miscellaneous.loot_tables.xp;

import apple.voltskiya.miscellaneous.loot_tables.PluginLootTables;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.Nullable;

public class LootXpTableManager {

    private final HashMap<String, Integer> tagXpTables = new HashMap<>();
    private final HashMap<EntityType<?>, Integer> entityXpTables = new HashMap<>();
    private static LootXpTableManager instance;

    public LootXpTableManager() {
        instance = this;
    }

    @Nullable
    public static Integer get(String tag) {
        return instance.tagXpTables.get(tag);
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
        PluginLootTables.get().saveLootTableXp();
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
