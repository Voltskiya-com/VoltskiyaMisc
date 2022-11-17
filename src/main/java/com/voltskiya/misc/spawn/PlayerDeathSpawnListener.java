package com.voltskiya.misc.spawn;

import com.voltskiya.misc.VoltskiyaPlugin;
import com.voltskiya.misc.spawn.gui.player.SpawnJoinGui;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerDeathSpawnListener implements Listener {

    public PlayerDeathSpawnListener() {
        VoltskiyaPlugin.get().registerEvents(this);
    }

    @EventHandler(ignoreCancelled = true)
    public void spawn(PlayerRespawnEvent event) {
        if (event.isBedSpawn())
            return;
        event.setRespawnLocation(PlayerSpawnDatabase.get().respawnBox);
        Player player = event.getPlayer();
        VoltskiyaPlugin.get().scheduleSyncDelayedTask(
            () -> player.openInventory(new SpawnJoinGui(player).getInventory()), 1);
    }
}
