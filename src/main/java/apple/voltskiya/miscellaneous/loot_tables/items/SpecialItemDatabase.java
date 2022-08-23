package apple.voltskiya.miscellaneous.loot_tables.items;

import apple.voltskiya.miscellaneous.loot_tables.PluginLootTables;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SpecialItemDatabase {
    private static SpecialItemDatabase instance;
    private final HashMap<String, SpecialItemStack> itemFlags = new HashMap<>();

    public SpecialItemDatabase() {
        instance = this;
    }

    public static SpecialItemDatabase get() {
        return instance;
    }

    private void save() {
        PluginLootTables.get().saveSpecialItems();
    }

    public List<String> getNames() {
        return new ArrayList<>(itemFlags.keySet());
    }

    @Nullable
    public SpecialItemStack get(String give) {
        return itemFlags.get(give);
    }

    public void add(SpecialItemStack item) {
        this.itemFlags.put(item.getNameUid(), item);
        save();
    }
}
