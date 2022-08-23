package apple.voltskiya.miscellaneous.tool.snow;

import apple.mc.utilities.player.wand.Wand;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Snow;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SnowToolWand extends Wand {
    private static final int MAX_FORCE = 10;
    private int radius = 4;
    private Mode mode = Mode.ADD;
    private int force = 6;

    public SnowToolWand(Player player) {
        super(player);
    }

    @Override
    public void onEvent(PlayerInteractEvent event) {
        if (actionIsRight(event.getAction())) rightClick(event);
    }


    public void rightClick(PlayerInteractEvent event) {
        Location playerLocation = event.getPlayer().getLocation();
        World world = playerLocation.getWorld();
        @Nullable RayTraceResult raytrace = world.rayTraceBlocks(playerLocation,
                                                                 playerLocation.getDirection(),
                                                                 200,
                                                                 FluidCollisionMode.NEVER
        );
        if (raytrace == null) return;
        @NotNull Vector hit = raytrace.getHitPosition();
        double locX = hit.getX();
        double locY = hit.getY();
        double locZ = hit.getZ();
        List<SnowBlock> snowBlocks = new ArrayList<>();
        for (int xi = -radius; xi <= radius; xi++) {
            for (int zi = -radius; zi <= radius; zi++) {
                for (int yi = radius; yi >= -radius; yi--) {
                    @NotNull Block block = world.getBlockAt((int) locX + xi, (int) locY + yi, (int) locZ + zi);
                    if (block.getType() == Material.SNOW && block.getBlockData() instanceof Snow snowBlock) {
                        snowBlocks.add(new SnowBlock(block.getX(), block.getY(), block.getZ(), snowBlock.getLayers()));
                        break;
                    } else if (block.getType() == Material.SNOW_BLOCK) {
                        snowBlocks.add(new SnowBlock(block.getX(), block.getY(), block.getZ(), 8));
                        break;
                    }
                }
            }
        }
        int countOfBlocks = snowBlocks.size();
        if (countOfBlocks < 3) {
            // a plane needs at least 3 points
            if (mode == Mode.ADD) {
                //todo add snow to the nothing that's there
            }
            return;
        }
        double sumX = 0, sumY = 0, sumZ = 0;
        for (SnowBlock snowBlock : snowBlocks) {
            sumX += snowBlock.x();
            sumY += snowBlock.mutatedY();
            sumZ += snowBlock.z();
        }
        double centerX = sumX / countOfBlocks, centerY = sumY / countOfBlocks, centerZ = sumZ / countOfBlocks;
        double xx = 0d, xy = 0d, xz = 0d, yy = 0d, yz = 0d, zz = 0d;
        for (SnowBlock snowBlock : snowBlocks) {
            double x = snowBlock.x() - centerX;
            double y = snowBlock.mutatedY() - centerY;
            double z = snowBlock.z() - centerZ;
            xx += x * x;
            xx += x * x;
            xy += x * y;
            xz += x * z;
            yy += y * y;
            yz += y * z;
            zz += z * z;
        }
        double det_x = yy * zz - yz * yz;
        double det_y = xx * zz - xz * xz;
        double det_z = xx * yy - xy * xy;

        double det_max = Math.max(det_x, Math.max(det_y, det_z));
        if (det_max <= 0.0) {
            // a plane needs at least 3 points
            if (mode == Mode.ADD) {
                //todo add snow to the nothing that's there
            }
            return;
        }

        Vector normal;
        if (det_max == det_x) {
            normal = new Vector(det_x, xz * yz - xy * zz, xy * yz - xz * yy);
        } else if (det_max == det_y) {
            normal = new Vector(xz * yz - xy * zz, det_y, xy * xz - yz * xx);
        } else {
            normal = new Vector(xy * yz - xz * yy, xy * xz - yz * xx, det_z);
        }
        Plane plane = new Plane(centerX, centerY, centerZ, normal);
        if (mode == Mode.ADD) {
            plane.up(1);
        } else if (mode == Mode.REMOVE) {
            plane.up(-1);
        }
        int maxHeight = world.getMaxHeight();
        for (int xi = -radius; xi <= radius; xi++) {
            for (int zi = -radius; zi <= radius; zi++) {
                double yiDesired = plane.yPoint(xi, zi), yi = yiDesired;
                if (yi >= maxHeight || yi < 0) {
                    continue;
                }
                @NotNull Block block = world.getBlockAt((int) locX + xi, (int) (yi), (int) locZ + zi);
                if (block.getType() == Material.AIR) {
                    // go down
                    if (yi < 0 || yi > maxHeight) break;
                    while ((block = world.getBlockAt((int) locX + xi,
                                                     (int) (yi),
                                                     (int) locZ + zi
                    )).getType() == Material.AIR) {
                        yi--;
                        if (yi < 0) {
                            break;
                        }
                    }
                } else {
                    // go up
                    if (yi < 0 || yi > maxHeight) break;
                    while (world.getBlockAt((int) locX + xi, (int) (yi), (int) locZ + zi).getType() != Material.AIR) {
                        yi++;
                        if (yi >= maxHeight) break;
                    }
                    yi--;
                    block = world.getBlockAt((int) locX + xi, (int) (yi), (int) locZ + zi);
                }
                // we are at a top block
                double realY;
                if (block.getType() == Material.SNOW && block.getBlockData() instanceof Snow snowBlock) {
                    if (snowBlock.getLayers() == snowBlock.getMaximumLayers()) {
                        realY = ((int) yi) + 1;
                    } else {
                        realY = ((int) yi) + snowBlock.getLayers() / (double) snowBlock.getMaximumLayers();
                    }
                } else if (block.getType() == Material.AIR) {
                    continue;
                } else {
                    realY = yi + 1;
                }
                if (realY < yiDesired) {
                    // go up
                    block = world.getBlockAt((int) locX + xi, (int) (realY), (int) locZ + zi);
                } else {
                    yi--;
                    block = world.getBlockAt((int) locX + xi, (int) (locY + yi), (int) locZ + zi);
                    if (block.getType() != Material.SNOW || block.getType() != Material.SNOW_BLOCK) {
                        continue;
                    }
                }
                @NotNull BlockData blockData = Material.SNOW.createBlockData();
                double diff = (yiDesired - realY);
                double newY = diff * force / MAX_FORCE;
                if (blockData instanceof Snow snowBlock) {
                    newY = Math.min(1, newY);
                    snowBlock.setLayers(Math.max(1, (int) (newY * snowBlock.getMaximumLayers())));
                }
                block.setBlockData(blockData);

            }
        }
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getRadius() {
        return radius;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public void setForce(int force) {
        this.force = force;
    }

    public int getForce() {
        return this.force;
    }

    public enum Mode {
        ADD,
        REMOVE,
        SMOOTH
    }

    private record SnowBlock(int x, int y, int z, int level) {
        public double mutatedY() {
            return y + level / 8d;
        }
    }

    private static final class Plane {
        private double x;
        private double y;
        private double z;
        private final Vector normal;

        private Plane(double x, double y, double z, Vector normal) {
            this.x = x;
            this.y = y;
            this.z = z;
            normal.normalize();
            if (normal.getY() < 0) {
                normal.multiply(-1);
            }
            this.normal = normal;
        }

        public void up(int force) {
            @NotNull Vector upness = normal.clone().multiply(force);
            x += upness.getX();
            y += upness.getY();
            z += upness.getZ();
        }

        public double yPoint(int xi, int zi) {
            // the dot of [xi,yi,zi] and normal is 0
            double x1 = normal.getX();
            double y1 = normal.getY();
            double z1 = normal.getZ();
            double dot = x1 * (double) xi + z1 * (double) zi;
            // dot + y1 * differenceY = 0
            // y1 * differenceY = -dot
            double differenceY = -dot / y1;
            return differenceY + y;
        }
    }
}
