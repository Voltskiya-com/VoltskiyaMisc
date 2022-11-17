package apple.voltskiya.miscellaneous.drops.xp;

import apple.nms.decoding.iregistry.DecodeEntityTypes;
import apple.voltskiya.miscellaneous.VoltskiyaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class LootTableKillListener implements Listener {
    public LootTableKillListener() {
        Bukkit.getPluginManager().registerEvents(this, VoltskiyaPlugin.get());
    }

    @EventHandler(ignoreCancelled = true)
    public void onCreatureDeath(EntityDeathEvent event) {
        if (event.getEntityType() == EntityType.PLAYER) return;
        // if we're not a player
        for (String tag : event.getEntity().getScoreboardTags()) {
            Integer xp = LootXpTableManager.get(tag);
            if (xp != null) {
                event.setDroppedExp(xp);
                return;
            }
        }
        Integer xp = LootXpTableManager.get(DecodeEntityTypes.getType(event.getEntity()));
        if (xp != null) event.setDroppedExp(xp);
    }
}
