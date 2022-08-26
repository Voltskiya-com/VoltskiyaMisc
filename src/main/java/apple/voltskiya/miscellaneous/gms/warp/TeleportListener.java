package apple.voltskiya.miscellaneous.gms.warp;

import apple.voltskiya.miscellaneous.VoltskiyaPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class TeleportListener implements Listener {

    public TeleportListener() {
        VoltskiyaPlugin.get().registerEvents(this);
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        if (event.getCause() != PlayerTeleportEvent.TeleportCause.SPECTATE)
            LocationHistoryDatabase.createBack(event.getPlayer().getUniqueId(), event.getFrom());
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerDeath(PlayerDeathEvent event) {
        LocationHistoryDatabase.createBack(event.getEntity().getUniqueId(),
            event.getEntity().getLocation());
    }
}
