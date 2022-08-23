package apple.voltskiya.miscellaneous.loot_tables.block.gui;

import apple.mc.utilities.inventory.gui.acd.page.InventoryGuiPageScrollableACD;
import apple.voltskiya.miscellaneous.loot_tables.block.BlockBreakTable;
import apple.voltskiya.miscellaneous.loot_tables.block.BlockBreakTableList;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

;

public class BlockTableGuiBlocks extends InventoryGuiPageScrollableACD<BlockTableGui> {
    public BlockTableGuiBlocks(BlockTableGui holder, BlockTableGui directParent) {
        super(holder);
    }

    @Override
    public void initialize() {
        setSlot(slotImpl(e -> {
        }, makeItem(Material.DARK_OAK_SAPLING, 1, "To add a block", List.of("Click a block in your inventory"))), 4);
        setSlot(slotImpl(e -> parentRemoveSubPage(), backItem()), 0);
    }

    @Override
    public void refreshPageItems() {
        clear();
        for (Material block : BlockBreakTableList.get().getBlocks()) {
            BlockBreakTable table = BlockBreakTableList.getBlock(block);
            add(slotImpl((e) -> parentAddSubPage(new BlockTableGuiLoot(this, table.copy())),
                         makeItem(block, table.getName())
            ));
        }
    }

    @Override
    public void onPlayerInventory(InventoryClickEvent event) {
        @Nullable ItemStack item = event.getCurrentItem();
        if (item == null) return;
        BlockBreakTable table = BlockBreakTableList.getBlock(item.getType());
        if (table == null) {
            table = new BlockBreakTable(item.getType(), item.getI18NDisplayName());
        } else {
            table = table.copy();
        }
        parentAddSubPage(new BlockTableGuiLoot(this, table));
    }

    @Override
    public String getName() {
        return "Special Block Loottables";
    }

    @Override
    public int size() {
        return 54;
    }
}
