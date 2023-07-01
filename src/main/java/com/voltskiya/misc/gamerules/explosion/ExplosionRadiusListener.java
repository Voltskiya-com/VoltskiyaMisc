package com.voltskiya.misc.gamerules.explosion;

import com.voltskiya.misc.VoltskiyaPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.ExplosionPrimeEvent;

public class ExplosionRadiusListener implements Listener {

    public static final double MULTIPLIER = 1.5;

    public ExplosionRadiusListener() {
        VoltskiyaPlugin.get().registerEvents(this);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onExplode(EntityDamageByEntityEvent event) {
        DamageCause cause = event.getCause();
        if (cause == DamageCause.BLOCK_EXPLOSION || cause == DamageCause.ENTITY_EXPLOSION) {
            double damage = event.getDamage();
            event.setDamage(damage / MULTIPLIER);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPrime(ExplosionPrimeEvent event) {
        event.setRadius((float) (MULTIPLIER * event.getRadius()));
    }
}
