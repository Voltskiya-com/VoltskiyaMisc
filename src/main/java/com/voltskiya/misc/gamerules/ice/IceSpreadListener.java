package com.voltskiya.misc.gamerules.ice;

import com.voltskiya.misc.VoltskiyaPlugin;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFormEvent;

public class IceSpreadListener implements Listener {

    public IceSpreadListener() {
        VoltskiyaPlugin.get().registerEvents(this);
    }

    @EventHandler
    public void iceSpread(BlockFormEvent event) {
        if (event.getNewState().getType() == Material.ICE) {
            event.setCancelled(true);
        }
    }
}
