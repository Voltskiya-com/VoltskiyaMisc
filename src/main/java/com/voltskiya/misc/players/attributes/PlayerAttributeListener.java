package com.voltskiya.misc.players.attributes;

import com.voltskiya.misc.VoltskiyaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;


public class PlayerAttributeListener implements Listener {

    public PlayerAttributeListener() {
        VoltskiyaPlugin.get().registerEvents(this);
        Bukkit.getOnlinePlayers().forEach(player -> player.setInvulnerable(false));
    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.setShieldBlockingDelay(0);
        player.setInvulnerable(false);
        PlayerAttributesConfig.get().updatePlayerAttributes(player);
    }
}
