package com.voltskiya.misc.drops.xp;

import com.voltskiya.misc.VoltskiyaPlugin;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import voltskiya.apple.utilities.minecraft.TagConstants;

public class LootTableKillListener implements Listener {

    public LootTableKillListener() {
        VoltskiyaPlugin.get().registerEvents(this);
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
            if (tag.equals(TagConstants.LOOT_EMPTY)) {
                event.getDrops().clear();
            }
        }
        Integer xp = LootXpTableManager.get((CraftEntity) event.getEntity());
        if (xp != null) event.setDroppedExp(xp);
    }
}
