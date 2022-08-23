package apple.voltskiya.miscellaneous.arrows;

import apple.voltskiya.miscellaneous.VoltskiyaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class ArrowHitListener implements Listener {
    public ArrowHitListener() {
        Bukkit.getPluginManager().registerEvents(this, VoltskiyaPlugin.get());
    }

    @EventHandler
    public void onArrows(ProjectileHitEvent event) {
        final Entity arrowEntity = event.getEntity();
        VoltskiyaPlugin.get().scheduleSyncDelayedTask(() -> {
            boolean isNoStick = arrowEntity.getScoreboardTags().contains("no_stick");
            if (isNoStick && isArrow(arrowEntity) && arrowEntity.isOnGround()) arrowEntity.remove();
        });
        noInvincibilityCheck(event, arrowEntity);
        noInvincibilityMobsCheck(event, arrowEntity);
    }

    private void noInvincibilityMobsCheck(ProjectileHitEvent event, Entity arrowEntity) {
        if (!arrowEntity.getScoreboardTags().contains("no_invincibility_mobs") || !isArrow(arrowEntity)) return;
        final Entity hitEntity = event.getHitEntity();
        if (hitEntity == null || hitEntity.getType() == EntityType.PLAYER) return;
        VoltskiyaPlugin.get().scheduleSyncDelayedTask(() -> hitEntity.setInvulnerable(false), 1);
    }

    private void noInvincibilityCheck(ProjectileHitEvent event, Entity arrowEntity) {
        if (!arrowEntity.getScoreboardTags().contains("no_invincibility") || !isArrow(arrowEntity)) return;
        final Entity hitEntity = event.getHitEntity();
        if (hitEntity == null) return;
        VoltskiyaPlugin.get().scheduleSyncDelayedTask(() -> hitEntity.setInvulnerable(false), 1);
    }

    private boolean isArrow(Entity entity) {
        final EntityType type = entity.getType();
        return type == EntityType.ARROW || type == EntityType.SPECTRAL_ARROW;
    }
}
