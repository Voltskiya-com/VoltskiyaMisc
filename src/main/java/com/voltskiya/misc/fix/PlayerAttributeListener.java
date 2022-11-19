package com.voltskiya.misc.fix;

import com.voltskiya.misc.VoltskiyaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;


public class PlayerAttributeListener implements Listener {

    public PlayerAttributeListener() {
        VoltskiyaPlugin.get().registerEvents(this);
    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {
        PlayerAttributesConfig.get().updatePlayerAttributes(event.getPlayer());
    }
}
