package apple.voltskiya.miscellaneous.ai_changes.hit_revenge;

import apple.voltskiya.miscellaneous.VoltskiyaPlugin;
import org.bukkit.GameMode;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.projectiles.ProjectileSource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static apple.voltskiya.miscellaneous.bottom_top.BottomTopDeathListener.VEHICLE_SOUL_MATE_TAG;

public class HitRevengeListener implements Listener {
    public HitRevengeListener() {
        VoltskiyaPlugin.get().registerEvents(this);
    }

    @EventHandler(ignoreCancelled = true)
    public void onHitDoRevenge(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        final Entity damaged = event.getEntity();
        if (damager instanceof Arrow) {
            ProjectileSource shooter = ((Arrow) damager).getShooter();
            if (shooter instanceof LivingEntity) {
                damager = (LivingEntity) shooter;
            }
        }
        if (damaged instanceof Mob && damager instanceof LivingEntity)
            anger((Mob) damaged, (LivingEntity) damager);
        if (damager instanceof LivingEntity && damaged.getScoreboardTags().contains(VEHICLE_SOUL_MATE_TAG)) {
            // do targeting to everyone linked by vehicles
            upTarget(damaged, (LivingEntity) damager);

            @Nullable Entity down = damaged.getVehicle();
            if (down != null)
                downTarget(down, (LivingEntity) damager);
        }
    }

    private void downTarget(Entity damaged, LivingEntity damager) {
        @Nullable Entity down = damaged.getVehicle();
        if (down != null) downTarget(down, damager);
        if (damaged instanceof Mob)
            anger((Mob) damaged, damager);
    }

    private void upTarget(Entity damaged, LivingEntity damager) {
        @NotNull List<Entity> up = damaged.getPassengers();
        for (Entity e : up) {
            if (e instanceof Mob) {
                upTarget(e, damager);
            }
        }
        if (damaged instanceof Mob)
            anger((Mob) damaged, damager);
    }

    private void anger(Mob damaged, LivingEntity damager) {
        if (damager instanceof Player && ((Player) damager).getGameMode() != GameMode.SURVIVAL) {
            return;
        }
        if (damaged.getTarget() == null) {
            damaged.setTarget(damager);
        }
    }
}
