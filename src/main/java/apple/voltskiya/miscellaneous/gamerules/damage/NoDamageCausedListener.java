package apple.voltskiya.miscellaneous.gamerules.damage;

import static voltskiya.apple.utilities.minecraft.TagConstants.NO_BEE_STING_POISON;
import static voltskiya.apple.utilities.minecraft.TagConstants.NO_BEE_STING_POISON_INFLICTED;
import static voltskiya.apple.utilities.minecraft.TagConstants.NO_COLLIDE_FIREBALL_THROW;
import static voltskiya.apple.utilities.minecraft.TagConstants.NO_FALL_DAMAGE;
import static voltskiya.apple.utilities.minecraft.TagConstants.NO_SUFFOCATION_DAMAGE;

import apple.voltskiya.miscellaneous.VoltskiyaPlugin;
import com.destroystokyo.paper.event.entity.ProjectileCollideEvent;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.jetbrains.annotations.NotNull;

public class NoDamageCausedListener implements Listener {

    public static final Map<EntityDamageEvent.DamageCause, String> NO_DAMAGE = new HashMap<>() {{
        put(EntityDamageEvent.DamageCause.SUFFOCATION, NO_SUFFOCATION_DAMAGE);
        put(EntityDamageEvent.DamageCause.FALL, NO_FALL_DAMAGE);
    }};

    public NoDamageCausedListener() {
        Bukkit.getPluginManager().registerEvents(this, VoltskiyaPlugin.get());
    }

    @EventHandler
    public void noDamage(EntityDamageEvent event) {
        String tagRequired = NO_DAMAGE.get(event.getCause());
        if (tagRequired != null && event.getEntity().getScoreboardTags().contains(tagRequired)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void noPoison(EntityDamageByEntityEvent event) {
        if (event.getDamager().getScoreboardTags().contains(NO_BEE_STING_POISON)) {
            Entity entity = event.getEntity();
            entity.addScoreboardTag(NO_BEE_STING_POISON_INFLICTED);
            VoltskiyaPlugin.get().scheduleSyncDelayedTask(() -> removePoisonTag(entity), 1);
        }
    }

    @EventHandler
    public void noPoison(EntityPotionEffectEvent event) {
        if (event.getAction() == EntityPotionEffectEvent.Action.ADDED && event.getEntity()
            .getScoreboardTags().contains(NO_BEE_STING_POISON_INFLICTED)) {
            event.setCancelled(true);
        }
    }

    private void removePoisonTag(Entity entity) {
        entity.removeScoreboardTag(NO_BEE_STING_POISON_INFLICTED);
    }

    @EventHandler
    public void collide(ProjectileCollideEvent event) {
        @NotNull Projectile entity = event.getEntity();
        if (entity.getScoreboardTags().contains(NO_COLLIDE_FIREBALL_THROW)) {
            if (event.getCollidedWith().getScoreboardTags().contains(NO_COLLIDE_FIREBALL_THROW)) {
                event.setCancelled(true);
            }
        }
    }
}
