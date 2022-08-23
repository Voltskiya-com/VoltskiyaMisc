package apple.voltskiya.miscellaneous.loot_tables.block;

import apple.mc.utilities.inventory.item.InventoryUtils;
import apple.voltskiya.miscellaneous.loot_tables.items.SpecialItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

;

public class BlockDropPossibility {
    private final List<SpecialItemStack> items = new ArrayList<>();
    private UUID uuid;

    public BlockDropPossibility() {
        uuid = UUID.randomUUID();
    }

    public BlockDropPossibility(BlockDropPossibility other) {
        this.uuid = other.uuid;
        for (SpecialItemStack drop : other.items) {
            this.items.add(drop.copy());
        }
    }

    public List<ItemStack> drop() {
        List<ItemStack> loot = new ArrayList<>();
        for (SpecialItemStack drop : this.items) {
            loot.add(drop.toItem());
        }
        return loot;
    }

    public BlockDropPossibility copy() {
        return new BlockDropPossibility(this);
    }

    public ItemStack getIcon() {
        if (items.isEmpty()) {
            return InventoryUtils.get().makeItem(Material.BEDROCK, "Empty ");
        }
        return items.get(0).toItem();
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof BlockDropPossibility itemDrop && itemDrop.uuid.equals(this.uuid);
    }

    public ArrayList<SpecialItemStack> getItems() {
        return new ArrayList<>(items);
    }

    public void removeDrop(SpecialItemStack itemDrop) {
        this.items.remove(itemDrop);
    }

    public void add(SpecialItemStack itemDrop) {
        this.items.remove(itemDrop);
        this.items.add(itemDrop);
    }
}
