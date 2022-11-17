package com.voltskiya.misc.gms.warp;

import com.voltskiya.misc.VoltskiyaPlugin;
import com.voltskiya.misc.gms.PluginCommands;
import java.io.File;
import java.util.Set;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.Nullable;

public class MigrateWarpDatabase {

    public static void load() {
        File file = PluginCommands.get().getFile("warps.yml");
        ConfigurationSection yaml = YamlConfiguration.loadConfiguration(file)
            .getConfigurationSection("warps");
        Set<String> warpNames = yaml.getKeys(false);
        for (String warpName : warpNames) {
            ConfigurationSection warp = yaml.getConfigurationSection(warpName);
            ConfigurationSection location = warp.getConfigurationSection("location");
            ConfigurationSection position = location.getConfigurationSection("position");
            @Nullable World world = Bukkit.getWorld(UUID.fromString(location.getString("world")));
            if (world == null) {
                VoltskiyaPlugin.get().getSLF4JLogger().error(warpName + " not found");
                continue;
            }
            double x = position.getDouble("x");
            double y = position.getDouble("y");
            double z = position.getDouble("z");
            float yaw = (float) location.getDouble("yaw");
            float pitch = (float) location.getDouble("pitch");
            WarpDatabase.get()
                .create(new Location(world, x, y, z, yaw, pitch), "old " + warp.getString("name"));
        }
    }
}
