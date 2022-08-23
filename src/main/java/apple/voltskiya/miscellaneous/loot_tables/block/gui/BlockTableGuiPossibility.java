package apple.voltskiya.miscellaneous.loot_tables.block.gui;

import apple.mc.utilities.inventory.gui.acd.page.InventoryGuiPageScrollableACD;
import apple.voltskiya.miscellaneous.loot_tables.block.BlockDropPossibility;
import apple.voltskiya.miscellaneous.loot_tables.items.SpecialItemStack;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

;

public class BlockTableGuiPossibility extends InventoryGuiPageScrollableACD<BlockTableGuiLoot> {
    private final BlockDropPossibility possibility;

    public BlockTableGuiPossibility(BlockTableGuiLoot parent, BlockDropPossibility possibility) {
        super(parent);
        this.possibility = possibility;
    }

    @Override
    public void initialize() {
        setSlot(slotImpl(e -> {
        }, makeItem(Material.DARK_OAK_SAPLING, 1, "To add a drop", List.of("Click a block in your inventory"))), 4);
        setSlot(slotImpl(e -> {
            parentRemoveSubPage();
        }, backItem()), 0);
        setSlot(slotImpl(e -> {
            save();
            parentRemoveSubPage();
        }, saveItem()), 2);
    }

    private void save() {
        parent.save(possibility);
    }

    @Override
    public void refreshPageItems() {
        clear();
        for (SpecialItemStack itemDrop : this.possibility.getItems()) {
            add(slotImpl(e -> {
                this.possibility.removeDrop(itemDrop);
                refresh();
            }, itemDrop.toItem()));
        }
    }

    @Override
    public void onPlayerInventory(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        if (item == null) return;
        this.possibility.add(new SpecialItemStack(item));
        refresh();
    }

    @Override
    public String getName() {
        return "Possible Drop";
    }

    @Override
    public int size() {
        return 54;
    }
}
