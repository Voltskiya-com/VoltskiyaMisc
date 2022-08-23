package apple.voltskiya.miscellaneous.loot_tables.block.gui;

import apple.mc.utilities.inventory.gui.acd.page.InventoryGuiPageScrollableACD;
import apple.voltskiya.miscellaneous.loot_tables.block.BlockBreakTable;
import apple.voltskiya.miscellaneous.loot_tables.block.BlockDropPossibility;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

;

public class BlockTableGuiLoot extends InventoryGuiPageScrollableACD<BlockTableGuiBlocks> {
    private final BlockBreakTable block;

    public BlockTableGuiLoot(BlockTableGuiBlocks parent, BlockBreakTable block) {
        super(parent);
        this.block = block;
    }

    @Override
    public void initialize() {
        setSlot(slotDoNothing(block.getIcon()), 2);
        setSlot(slotImpl(e -> parentAddSubPage(new BlockTableGuiPossibility(this, new BlockDropPossibility())),
                         makeItem(Material.DARK_OAK_SAPLING, "Add a drop possibility")
        ), 4);
        setSlot(slotImpl(e -> {
            parentRemoveSubPage();
        }, backItem()), 0);
    }

    @Override
    public void refreshPageItems() {
        clear();
        int i = 0;
        for (BlockDropPossibility drop : block.getDrops()) {
            ItemStack icon = drop.getIcon();
            displayName(icon, getDisplayName(icon) + " " + i++);
            add(slotImpl(e -> parentAddSubPage(new BlockTableGuiPossibility(this, drop)), icon));
        }
    }

    @Override
    public String getName() {
        return "Loot table";
    }

    @Override
    public int size() {
        return 36;
    }

    public void save(BlockDropPossibility possibility) {
        block.addPossibility(possibility);
        block.save();
    }
}
