package com.voltskiya.misc.players;

import com.voltskiya.misc.VoltskiyaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerBlockingLoginListener implements Listener {

    public PlayerBlockingLoginListener() {
        Bukkit.getPluginManager().registerEvents(this, VoltskiyaPlugin.get());
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().setShieldBlockingDelay(0);
    }
}
