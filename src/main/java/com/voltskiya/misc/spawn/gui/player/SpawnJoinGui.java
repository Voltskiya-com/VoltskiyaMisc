package com.voltskiya.misc.spawn.gui.player;

import apple.mc.utilities.inventory.gui.acd.InventoryGuiACD;
import org.bukkit.entity.Player;

public class SpawnJoinGui extends InventoryGuiACD {
    public SpawnJoinGui(Player player) {
        addPage(new SpawnJoinGuiMain(this));
    }
}
