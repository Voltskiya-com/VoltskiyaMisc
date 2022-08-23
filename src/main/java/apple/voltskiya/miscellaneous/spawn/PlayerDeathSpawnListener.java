package apple.voltskiya.miscellaneous.spawn;

import apple.voltskiya.miscellaneous.VoltskiyaPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerDeathSpawnListener implements Listener {
    public PlayerDeathSpawnListener() {
        VoltskiyaPlugin.get().registerEvents(this);
    }

    @EventHandler(ignoreCancelled = true)
    public void spawn(PlayerRespawnEvent event) {
        if (event.isBedSpawn()) return;
        event.setRespawnLocation(PlayerSpawnDatabase.get().respawnBox);
    }
}
