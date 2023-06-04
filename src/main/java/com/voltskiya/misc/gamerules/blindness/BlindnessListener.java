package com.voltskiya.misc.gamerules.blindness;

import com.voltskiya.misc.VoltskiyaPlugin;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftMob;
import org.bukkit.entity.Mob;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import voltskiya.apple.utilities.minecraft.TagConstants;

public class BlindnessListener implements Listener {

    public BlindnessListener() {
        VoltskiyaPlugin.get().registerEvents(this);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onBlindness(EntityPotionEffectEvent event) {
        if (!isBlindness(event.getNewEffect())) return;
        if (event.getEntity() instanceof Mob entity) {
            entity.addScoreboardTag(TagConstants.FORCE_TARGET);
            entity.setTarget(null);
            ((CraftMob) entity).getHandle().getBrain().clearMemories();
            entity.removeScoreboardTag(TagConstants.FORCE_TARGET);
        }
    }

    private boolean isBlindness(PotionEffect newEffect) {
        return newEffect != null && newEffect.getType().equals(PotionEffectType.BLINDNESS);
    }

    @EventHandler
    public void onTarget(EntityTargetEvent event) {
        if (event.getTarget() == null) return;
        if (!(event.getEntity() instanceof Mob entity)) return;
        PotionEffect effect = entity.getPotionEffect(PotionEffectType.BLINDNESS);
        if (effect != null) {
            event.setTarget(null);
        }
    }
}
