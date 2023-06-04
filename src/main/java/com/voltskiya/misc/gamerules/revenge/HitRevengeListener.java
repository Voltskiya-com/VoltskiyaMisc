package com.voltskiya.misc.gamerules.revenge;

import static com.voltskiya.misc.gamerules.soul_mate.SoulMateDeathListener.VEHICLE_SOUL_MATE_TAG;

import com.voltskiya.misc.VoltskiyaPlugin;
import java.util.List;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import voltskiya.apple.utilities.DistanceUtils;

public class HitRevengeListener implements Listener {

    public HitRevengeListener() {
        VoltskiyaPlugin.get().registerEvents(this);
    }

    @EventHandler(ignoreCancelled = true)
    public void onHitDoRevenge(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        final Entity damaged = event.getEntity();
        if (damager instanceof AbstractArrow arrow) {
            if (arrow.getShooter() instanceof Entity damagerEntity) {
                damager = damagerEntity;
            }
        }
        if (!(damager instanceof LivingEntity damagerLiving))
            return;
        if (damaged instanceof Mob mob)
            anger(mob, damagerLiving);
        if (damaged.getScoreboardTags().contains(VEHICLE_SOUL_MATE_TAG)) {
            // do targeting to everyone linked by vehicles
            upTarget(damaged, damagerLiving);

            @Nullable Entity down = damaged.getVehicle();
            if (down != null)
                downTarget(down, damagerLiving);
        }
    }

    private void downTarget(Entity damaged, LivingEntity damager) {
        @Nullable Entity down = damaged.getVehicle();
        if (down != null)
            downTarget(down, damager);
        if (damaged instanceof Mob mob)
            anger(mob, damager);
    }

    private void upTarget(Entity damaged, LivingEntity damager) {
        @NotNull List<Entity> up = damaged.getPassengers();
        for (Entity e : up) {
            if (e instanceof Mob) {
                upTarget(e, damager);
            }
        }
        if (damaged instanceof Mob mob)
            anger(mob, damager);
    }

    private void anger(Mob damaged, LivingEntity damager) {
        // spectators don't count!!!
        if (damager instanceof Player player && player.getGameMode() != GameMode.SURVIVAL) {
            return;
        }
        LivingEntity target = damaged.getTarget();
        Location myLocation = damaged.getLocation();
        if (target == null) {
            damaged.setTarget(damager);
            return;
        }
        double distanceToTarget = DistanceUtils.distance(target.getLocation(), myLocation);
        double distanceToDamager = DistanceUtils.distance(damager.getLocation(), myLocation);
        if (distanceToDamager < distanceToTarget)
            damaged.setTarget(damager);
    }
}
