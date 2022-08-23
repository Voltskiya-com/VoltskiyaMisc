package apple.voltskiya.miscellaneous.event.basalt_dust_storm;

import apple.voltskiya.miscellaneous.VoltskiyaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Random;

public class BasaltDustStorm {
    private final Location center;
    private final int radius;
    private int ticksToLast;
    private final Random random = new Random();

    public BasaltDustStorm(Location center, int radius, int secondsToLast) {
        this.center = center;
        this.radius = radius;
        this.ticksToLast = secondsToLast * 20;
        runStorm();
    }

    private void runStorm() {
        if (this.ticksToLast <= 0) return;
        this.ticksToLast -= 20;
        Bukkit.getScheduler().scheduleSyncDelayedTask(VoltskiyaPlugin.get(), this::runStorm, 20);
        @NotNull Collection<Player> players = center.getNearbyEntitiesByType(Player.class, radius);
        for (int tick = 0; tick < 20; tick++) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(VoltskiyaPlugin.get(), () -> {
                for (Player player : players) {
                    Location location = player.getLocation();
                    double x = location.getX();
                    double y = location.getY();
                    double z = location.getZ();

                    for (int i = 0; i < 20; i++) {
                        double xi = (random.nextDouble() - .5) * 8;
                        double yi = (random.nextDouble() - .5) * 8;
                        double zi = (random.nextDouble() - .5) * 8;
                        location.getWorld().spawnParticle(Particle.WHITE_ASH, x + xi, y + yi, z + zi, 75, 1, 1, 1);
                    }
                }
            }, tick);
        }
    }
}
