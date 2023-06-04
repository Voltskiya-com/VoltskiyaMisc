package com.voltskiya.misc.gamerules.no_vehicle;

import com.voltskiya.misc.VoltskiyaPlugin;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleEnterEvent;

public class NoVehicleEnterListener implements Listener {

    public NoVehicleEnterListener() {
        VoltskiyaPlugin.get().registerEvents(this);
    }

    @EventHandler
    public void onEnter(VehicleEnterEvent event) {
        if (event.getEntered().getType() != EntityType.PLAYER)
            event.setCancelled(true);
    }
}
