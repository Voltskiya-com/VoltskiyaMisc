package com.voltskiya.misc.gamerules.coral;

import com.voltskiya.misc.VoltskiyaPlugin;
import java.util.HashSet;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFadeEvent;

public class CoralFadeListener implements Listener {

    public static final HashSet<Material> CORALS = new HashSet<>() {{
        add(Material.BRAIN_CORAL);
        add(Material.BUBBLE_CORAL);
        add(Material.FIRE_CORAL);
        add(Material.TUBE_CORAL);
        add(Material.HORN_CORAL);
        add(Material.BRAIN_CORAL_BLOCK);
        add(Material.BUBBLE_CORAL_BLOCK);
        add(Material.FIRE_CORAL_BLOCK);
        add(Material.TUBE_CORAL_BLOCK);
        add(Material.HORN_CORAL_BLOCK);
        add(Material.BRAIN_CORAL_FAN);
        add(Material.BUBBLE_CORAL_FAN);
        add(Material.FIRE_CORAL_FAN);
        add(Material.TUBE_CORAL_FAN);
        add(Material.HORN_CORAL_FAN);
        add(Material.BRAIN_CORAL_WALL_FAN);
        add(Material.BUBBLE_CORAL_WALL_FAN);
        add(Material.FIRE_CORAL_WALL_FAN);
        add(Material.TUBE_CORAL_WALL_FAN);
        add(Material.HORN_CORAL_WALL_FAN);
    }};

    public CoralFadeListener() {
        VoltskiyaPlugin.get().registerEvents(this);
    }

    @EventHandler
    public void coralFade(BlockFadeEvent event) {
        if (isCoral(event.getNewState().getBlock().getType())) {
            event.setCancelled(true);
        }
    }

    private boolean isCoral(Material type) {
        return CORALS.contains(type);
    }
}
