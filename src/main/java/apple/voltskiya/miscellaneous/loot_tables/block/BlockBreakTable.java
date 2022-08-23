package apple.voltskiya.miscellaneous.loot_tables.block;

import apple.mc.utilities.inventory.item.InventoryUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

;

public class BlockBreakTable {
    private Material material;
    private String name;
    private final List<BlockDropPossibility> drops = new ArrayList<>();

    public BlockBreakTable(@NotNull Material material, String name) {
        this.material = material;
        this.name = name;
    }

    public BlockBreakTable(BlockBreakTable other) {
        this.material = other.material;
        this.name = other.name;
        for (BlockDropPossibility drop : other.drops) {
            this.drops.add(drop.copy());
        }
    }

    public BlockBreakTable() {
    }

    public List<ItemStack> drop() {
        int choice = new Random().nextInt(this.drops.size());
        BlockDropPossibility loot = this.drops.get(choice);
        return loot.drop();
    }

    public String getName() {
        return name;
    }

    public BlockBreakTable copy() {
        return new BlockBreakTable(this);
    }

    public List<BlockDropPossibility> getDrops() {
        return new ArrayList<>(drops);
    }

    public void addPossibility(BlockDropPossibility possibility) {
        this.drops.remove(possibility);
        this.drops.add(possibility);
    }

    public Material getMaterial() {
        return this.material;
    }

    public ItemStack getIcon() {
        return InventoryUtils.get().makeItem(this.material, this.name);
    }

    public void save() {
        BlockBreakTableList.get().addTable(this);
    }
}
