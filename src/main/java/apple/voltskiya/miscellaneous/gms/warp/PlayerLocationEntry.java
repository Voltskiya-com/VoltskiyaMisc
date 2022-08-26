package apple.voltskiya.miscellaneous.gms.warp;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class PlayerLocationEntry {
    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;
    private UUID world;

    public PlayerLocationEntry(Location prevLocation) {
        this.x = prevLocation.getX();
        this.y = prevLocation.getY();
        this.z = prevLocation.getZ();
        this.yaw = prevLocation.getYaw();
        this.pitch = prevLocation.getPitch();
        this.world = prevLocation.getWorld().getUID();
    }

    public PlayerLocationEntry() {
    }

    @Nullable
    public Location toLocation() {
        World world = Bukkit.getWorld(this.world);
        if (world == null) return null;
        return new Location(world, x, y, z, yaw, pitch);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public UUID getWorld() {
        return world;
    }
}
