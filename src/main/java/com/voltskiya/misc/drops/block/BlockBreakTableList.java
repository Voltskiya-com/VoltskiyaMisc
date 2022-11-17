package com.voltskiya.misc.drops.block;

import com.voltskiya.misc.drops.PluginDrops;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.HashMap;

public class BlockBreakTableList {
    private static BlockBreakTableList instance;

    private final HashMap<Material, BlockBreakTable> blockBreakTables = new HashMap<>();

    public BlockBreakTableList() {
        instance = this;
    }

    public static BlockBreakTable getBlock(Material type) {
        return get().blockBreakTables.get(type);
    }

    public void save() {
        PluginDrops.get().saveLootTableBlock();
    }

    public static BlockBreakTableList get() {
        return instance;
    }

    public void addTable(BlockBreakTable block) {
        blockBreakTables.put(block.getMaterial(), block);
        save();
    }

    public ArrayList<Material> getBlocks() {
        return new ArrayList<>(blockBreakTables.keySet());
    }

}
