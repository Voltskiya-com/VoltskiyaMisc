package apple.voltskiya.miscellaneous.admin.console.command;

import apple.mc.utilities.inventory.gui.acd.InventoryGuiACD;
import org.bukkit.entity.Player;

public class CommandBlockGui extends InventoryGuiACD {
    private final Player player;

    public CommandBlockGui(Player player) {
        this.player = player;
        addPage(new CommandBlockGuiPageList(this));
    }

    public Player getPlayer() {
        return player;
    }
}
