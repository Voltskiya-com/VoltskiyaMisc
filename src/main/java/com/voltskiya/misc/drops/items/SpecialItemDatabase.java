package com.voltskiya.misc.drops.items;

import com.voltskiya.misc.drops.DropsModule;
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
        DropsModule.get().saveSpecialItems();
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
