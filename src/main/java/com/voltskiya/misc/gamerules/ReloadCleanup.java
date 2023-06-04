package com.voltskiya.misc.gamerules;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import voltskiya.apple.utilities.minecraft.TagConstants;

public class ReloadCleanup {

    public static void cleanup() {
        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (entity.getScoreboardTags().contains(TagConstants.CLEANUP_KILL)) {
                    entity.remove();
                }
            }
        }
    }
}
