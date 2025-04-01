package com.voltskiya.misc.gamerules.soul_mate;

import com.voltskiya.misc.VoltskiyaPlugin;
import java.util.List;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SoulMateDeathListener implements Listener {

    public static final String VEHICLE_SOUL_MATE_TAG = "vehicle_soul_mate";

    public SoulMateDeathListener() {
        VoltskiyaPlugin.get().registerEvents(this);
    }


    @EventHandler
    public void onBottomTopDeath(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.getScoreboardTags().contains(VEHICLE_SOUL_MATE_TAG)) {
            // kill everything linked by vehicles

            // kill above
            @NotNull List<Entity> up = entity.getPassengers();
            for (Entity e : up) {
                if (e instanceof LivingEntity living) {
                    living.setHealth(0);
                }
            }
            @Nullable Entity down = entity.getVehicle();
            if (down instanceof LivingEntity living) {
                living.setHealth(0);
            }
        }
    }
}
