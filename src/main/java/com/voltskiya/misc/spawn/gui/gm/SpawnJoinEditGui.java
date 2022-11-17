package com.voltskiya.misc.spawn.gui.gm;

import apple.mc.utilities.inventory.gui.acd.InventoryGuiACD;
import org.bukkit.entity.Player;

public class SpawnJoinEditGui extends InventoryGuiACD {
    private final Player player;

    public SpawnJoinEditGui(Player player) {
        this.player = player;
        addPage(new SpawnJoinEditGuiMain(this));
    }

    public Player getPlayer() {
        return player;
    }
}
