package apple.voltskiya.miscellaneous.bottom_top;

import apple.voltskiya.miscellaneous.VoltskiyaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BottomTopDeathListener implements Listener {

    public static final String VEHICLE_SOUL_MATE_TAG = "vehicle_soul_mate";

    public BottomTopDeathListener() {
        Bukkit.getPluginManager().registerEvents(this, VoltskiyaPlugin.get());
    }

    @EventHandler
    public void onBottomTopDeath(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.getScoreboardTags().contains(VEHICLE_SOUL_MATE_TAG)) {
            // kill everything linked by vehicles

            // kill above
            @NotNull List<Entity> up = entity.getPassengers();
            for (Entity e : up) {
                if (e instanceof LivingEntity) {
                    ((LivingEntity) e).setHealth(0);
                }
            }
            @Nullable Entity down = entity.getVehicle();
            if (down instanceof LivingEntity) {
                ((LivingEntity) down).setHealth(0);
            }
        }
    }
}
