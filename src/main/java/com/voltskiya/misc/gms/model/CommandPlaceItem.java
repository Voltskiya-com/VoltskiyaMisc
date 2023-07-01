package com.voltskiya.misc.gms.model;

import com.voltskiya.lib.acf.BaseCommand;
import com.voltskiya.lib.acf.annotation.CommandAlias;
import com.voltskiya.lib.acf.annotation.CommandPermission;
import com.voltskiya.lib.acf.annotation.Default;
import com.voltskiya.lib.acf.annotation.Name;
import com.voltskiya.lib.acf.annotation.Optional;
import com.voltskiya.misc.VoltskiyaPlugin;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

@CommandAlias("item_place")
@CommandPermission("minecraft.command.give")
public class CommandPlaceItem extends BaseCommand {

    public CommandPlaceItem() {
        VoltskiyaPlugin.get().registerCommand(this);
    }

    @Default
    public void itemPlace(Player player,
        @Optional @Name("horiz") Double x,
        @Optional @Name("y") Double y,
        @Optional @Name("depth") Double z) {
        ItemStack item = player.getInventory().getItemInMainHand();
        RayTraceResult rayTrace = player.rayTraceBlocks(5, FluidCollisionMode.NEVER);
        if (rayTrace == null || rayTrace.getHitBlockFace() == null) return;

        Vector hitPosition = rayTrace.getHitPosition();
        Location clickedLocation = hitPosition.toLocation(player.getWorld());
        double fixX = -0.5;
        double fixZ = -0.5;
        boolean isSide = true;
        int yawRotation = 0;
        switch (rayTrace.getHitBlockFace()) {
            case EAST -> {
                fixX++;
                fixZ++;
                yawRotation = 90;
            }
            case WEST -> {
                fixZ++;
                yawRotation = 270;
            }
            case SOUTH -> {
                fixX++;
                fixZ++;
                yawRotation = 180;
            }
            case NORTH -> fixX++;
            case UP, DOWN -> {
                fixX++;
                fixZ++;
                isSide = false;
                yawRotation = getPlayerFacingFixYaw(player);
            }
        }
//        yawRotation += 180;
        Location pasteLocation = clickedLocation.getBlock().getLocation().add(fixX, isSide ? 1 : 0, fixZ);
        pasteLocation.setYaw(yawRotation);
        pasteLocation.setPitch(0);
        pasteLocation.subtract(new Vector(.75, 0, 0).rotateAroundY(yawRotation));
        pasteLocation.subtract(0, 1.675, 0); // go down
        if (x != null && y != null && z != null)
            pasteLocation.add(new Vector(x, y, z).rotateAroundY(yawRotation));

        pasteLocation.getWorld().spawnEntity(pasteLocation, EntityType.ARMOR_STAND, SpawnReason.COMMAND, (e) -> {
            if (e instanceof ArmorStand stand) {
                stand.setHeadPose(new EulerAngle(Math.toRadians(90), 0, 0));
                stand.setItem(EquipmentSlot.HEAD, item);
                stand.setGravity(false);
                stand.setInvulnerable(true);
                stand.setInvisible(false);
                for (EquipmentSlot slot : EquipmentSlot.values()) {
                    stand.addDisabledSlots(slot);
                }
            }
        });
    }

    private int getPlayerFacingFixYaw(Player event) {
        return switch (event.getFacing()) {
            case EAST -> 90;
            case SOUTH -> 180;
            case WEST -> 270;
            default -> 0;
        };
    }
}
