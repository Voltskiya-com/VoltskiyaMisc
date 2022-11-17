package com.voltskiya.misc.conveyer_belt;

import com.voltskiya.misc.VoltskiyaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ConveyorWandListener implements Listener {
    public static final NamespacedKey CONVEYOR_WAND_KEY = new NamespacedKey(VoltskiyaPlugin.get(), "conveyor_wand");
    private static final Map<UUID, ConveyorWand> playerToWand = new HashMap<>();

    public ConveyorWandListener() {
        Bukkit.getPluginManager().registerEvents(this, VoltskiyaPlugin.get());
    }

    @Nullable
    public static ConveyorWand get(UUID uniqueId) {
        return playerToWand.get(uniqueId);
    }

    @EventHandler(ignoreCancelled = true)
    public void wand(PlayerInteractEvent event) {
        @Nullable ItemStack item = event.getItem();
        if (item != null) {
            @NotNull PersistentDataContainer dataContainer = item.getItemMeta().getPersistentDataContainer();
            if (dataContainer.has(CONVEYOR_WAND_KEY, PersistentDataType.BYTE)) {
                @Nullable Block block = event.getClickedBlock();
                if (block != null) {
                    playerToWand.putIfAbsent(event.getPlayer().getUniqueId(), new ConveyorWand());
                    ConveyorWand playerWand = playerToWand.get(event.getPlayer().getUniqueId());
                    @NotNull Location blockLocation = block.getLocation();
                    switch (event.getAction()) {
                        case LEFT_CLICK_AIR, LEFT_CLICK_BLOCK -> playerWand.leftClick(blockLocation);
                        case RIGHT_CLICK_AIR, RIGHT_CLICK_BLOCK -> playerWand.rightClick(blockLocation);
                    }
                    event.setCancelled(true);
                }
            }
        }
    }

    public static class ConveyorWand {
        public Location left = null;
        public Location right = null;
        public UUID world = null;

        public void rightClick(Location blockLocation) {
            right = blockLocation;
            world = blockLocation.getWorld().getUID();
        }

        public void leftClick(Location blockLocation) {
            left = blockLocation;
            world = blockLocation.getWorld().getUID();
        }

        public boolean isComplete() {
            return left != null || right != null;
        }

        public ArrayList<Box3d> getCoords() {
            double x1 = left.getX();
            double x2 = right.getX();
            double y1 = left.getY();
            double y2 = right.getY();
            double z1 = left.getZ();
            double z2 = right.getZ();
            return new ArrayList<>() {{
                add(new Box3d(world,
                        Math.min(x1, x2),
                        Math.min(y1, y2),
                        Math.min(z1, z2),
                        Math.max(x1, x2) + 1,
                        Math.max(y1, y2) + 1.001,
                        Math.max(z1, z2) + 1
                ));
            }};
        }
    }
}
