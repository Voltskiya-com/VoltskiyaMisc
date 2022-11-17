package apple.voltskiya.miscellaneous.drops.block.gui;

import apple.mc.utilities.inventory.gui.acd.InventoryGuiACD;
import org.bukkit.entity.Player;

public class BlockTableGui extends InventoryGuiACD {
    private Player player;

    public BlockTableGui(Player player) {
        this.player = player;
        addPage(new BlockTableGuiBlocks(this, this));
    }
}
