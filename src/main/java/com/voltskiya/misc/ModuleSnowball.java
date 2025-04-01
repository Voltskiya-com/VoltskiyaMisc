package com.voltskiya.misc;

import apple.mc.utilities.inventory.item.InventoryUtils;
import com.voltskiya.lib.AbstractModule;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class ModuleSnowball extends AbstractModule implements Listener {

    private static final String SNOWBALL = "snowball";

    @Override
    public void enable() {
        VoltskiyaPlugin.get().registerEvents(this);
    }

    @Override
    public String getName() {
        return "Snowball";
    }

    @EventHandler
    public void snowballEvent(PlayerInteractEvent event) {
        if (!event.getAction().isRightClick()) return;
        ItemStack item = event.getItem();
        if (item == null || item.getType() == Material.SNOWBALL) return;
        @NotNull String name = InventoryUtils.get().getDisplayName(item);
        if (name.equalsIgnoreCase(SNOWBALL))
            snowball(event.getPlayer().getEyeLocation());
    }


    private void snowball(Location playerLocation) {
        Vector direction = playerLocation.getDirection();
        new SnowballThrow(playerLocation.add(direction), direction.multiply(.75));
    }

    private static class SnowballThrow implements Runnable {

        public static final double HITBOX = 0.5;
        private final static Vector GRAVITY = new Vector(0, -0.02, 0);
        private final static Random random = new Random();
        private final static double radius = 5d;
        private final Location currentLocation;
        private final World world;
        private final Vector direction;

        public SnowballThrow(Location currentLocation, Vector direction) {
            this.currentLocation = currentLocation;
            this.direction = direction;
            this.world = currentLocation.getWorld();
            this.world.playSound(currentLocation, Sound.ENTITY_SNOWBALL_THROW, .2f, 1f);
            run();
        }

        @Override
        public void run() {
            direction.add(GRAVITY);
            currentLocation.add(direction);
            snowballParticles();
            if (isOutOfBounds()) return;
            if (blockCollision()) {
                world.playSound(currentLocation, Sound.BLOCK_SNOW_STEP, .5f, .85f);
                return;
            }
            if (playerCollision()) {
                world.playSound(currentLocation, Sound.BLOCK_SNOW_BREAK, 40, .85f);
                return;
            }
            Bukkit.getScheduler().scheduleSyncDelayedTask(VoltskiyaPlugin.get(), this, 1);
        }

        private boolean blockCollision() {
            return !currentLocation.getBlock().getType().isAir();
        }

        private boolean isOutOfBounds() {
            double y = currentLocation.getY();
            return y < world.getMinHeight() || y > world.getMaxHeight();
        }

        private boolean playerCollision() {
            for (Player player : world.getNearbyPlayers(currentLocation, 2)) {
                BoundingBox playerBox = player.getBoundingBox();
                BoundingBox snowball = getSnowballBox();
                if (playerBox.overlaps(snowball))
                    return true;
            }
            return false;
        }

        private BoundingBox getSnowballBox() {
            Vector radius = new Vector(HITBOX, HITBOX, HITBOX);
            Vector min = currentLocation.toVector().subtract(radius);
            Vector max = min.add(radius.multiply(2));
            return BoundingBox.of(min, max);
        }

        private void snowballParticles() {
            for (int i = 0; i < 30; i++) {
                double theta = random.nextDouble() * 360;
                double thetay = random.nextDouble() * 360;
                double x = Math.cos(Math.toRadians(theta)) * radius;
                double y = Math.sin(Math.toRadians(theta)) * radius;
                double z = Math.sin(Math.toRadians(thetay)) * radius;
                world.spawnParticle(Particle.DUST, currentLocation, 0, x, y, z,
                    new Particle.DustOptions(Color.fromRGB(0xFFFFFF), 1.4f));
            }
        }
    }

}
