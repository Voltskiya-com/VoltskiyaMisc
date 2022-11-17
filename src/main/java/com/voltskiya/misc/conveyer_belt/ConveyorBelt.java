package com.voltskiya.misc.conveyer_belt;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;

public class ConveyorBelt {
    private String uniqueName;
    private ArrayList<Box3d> coords;
    private double speed;
    private Vector direction;
    private boolean isOn;

    public ConveyorBelt(String uniqueName, ArrayList<Box3d> coords, double speed, Vector direction) {
        this.uniqueName = uniqueName;
        this.coords = coords;
        this.speed = speed / 20*ConveyorRunning.STEPS_PER_TICK;
        this.direction = direction;
        this.isOn = true;
    }

    public String uniqueName() {
        return uniqueName;
    }

    public void setOn(boolean newOn) {
        this.isOn = newOn;
        ConveyorBeltManager.save();
    }

    public void run() {
        if (isOn) {
            for (Box3d coord : coords) {
                BoundingBox boundingBox = new BoundingBox(coord.xLower, coord.yLower, coord.zLower, coord.xUpper, coord.yUpper, coord.zUpper);
                @Nullable World world = Bukkit.getWorld(coord.world);
                if (world != null) {
                    Collection<Entity> entities = world.getNearbyEntities(boundingBox);
                    for (Entity entity : entities) {
                        if (entity instanceof Player player) {
                            if (player.getGameMode() == GameMode.SPECTATOR) continue;
                        }
                        Vector originalVelocity = entity.getVelocity();
                        Vector newGotoVelocity = new Vector(
                                (originalVelocity.getX() + direction.getX()) / 2,
                                (originalVelocity.getY() + direction.getY()) / 2,
                                (originalVelocity.getZ() + direction.getZ()) / 2
                        );
                        @NotNull Vector difference = newGotoVelocity.clone().subtract(originalVelocity);
                        difference.setX(difference.getX() * Math.abs(direction.getX()) * speed);
                        difference.setY(difference.getY() * Math.abs(direction.getY()) * speed);
                        difference.setZ(difference.getZ() * Math.abs(direction.getZ()) * speed);
                        entity.setVelocity(originalVelocity.add(difference));
                    }
                }
            }
        }
    }

    public void add(ArrayList<Box3d> coords) {
        this.coords.addAll(coords);
    }

    public boolean isOn() {
        return isOn;
    }
}
