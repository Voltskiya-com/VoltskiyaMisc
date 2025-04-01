package com.voltskiya.misc.gamerules.arrow;

import com.voltskiya.misc.VoltskiyaPlugin;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

public class ArrowHitListener implements Listener {

    private static final double ARROW_INCREASE_DAMAGE = 1.35;

    public ArrowHitListener() {
        VoltskiyaPlugin.get().registerEvents(this);
    }

    @EventHandler
    public void onArrowDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof AbstractArrow) {
            event.setDamage(event.getDamage() * ARROW_INCREASE_DAMAGE);
        }
    }

    @EventHandler
    public void onArrows(ProjectileHitEvent event) {
        if (event.getEntity() instanceof AbstractArrow arrow) {
            boolean isNoStick = arrow.getScoreboardTags().contains("no_stick");
            if (isNoStick) {
                VoltskiyaPlugin.get().scheduleSyncDelayedTask(() -> {
                    if (arrow.isInBlock()) arrow.remove();
                });
            }
        }

        noInvincibilityCheck(event, event.getEntity());
        noInvincibilityMobsCheck(event, event.getEntity());
    }

    private void noInvincibilityMobsCheck(ProjectileHitEvent event, Entity arrowEntity) {
        if (!arrowEntity.getScoreboardTags().contains("no_invincibility_mobs") || !(arrowEntity instanceof AbstractArrow))
            return;
        final Entity hitEntity = event.getHitEntity();
        if (hitEntity == null || hitEntity.getType() == EntityType.PLAYER)
            return;
        VoltskiyaPlugin.get().scheduleSyncDelayedTask(() -> hitEntity.setInvulnerable(false), 1);
    }

    private void noInvincibilityCheck(ProjectileHitEvent event, Entity arrowEntity) {
        if (!arrowEntity.getScoreboardTags().contains("no_invincibility_mobs") || !(arrowEntity instanceof AbstractArrow))
            return;
        final Entity hitEntity = event.getHitEntity();
        if (hitEntity == null)
            return;
        VoltskiyaPlugin.get().scheduleSyncDelayedTask(() -> hitEntity.setInvulnerable(false), 1);
    }

}
