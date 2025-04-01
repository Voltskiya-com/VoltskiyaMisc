package com.voltskiya.misc.gamerules.damage;

import com.voltskiya.misc.VoltskiyaPlugin;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class NoEntityDamageListener implements Listener {

    public static final Map<EntityType, String> NO_DAMAGE = new HashMap<>() {{
        put(EntityType.EVOKER_FANGS, "no_evoker_fangs_damage");
    }};

    public NoEntityDamageListener() {
        VoltskiyaPlugin.get().registerEvents(this);
    }

    @EventHandler
    public void noDamage(EntityDamageByEntityEvent event) {
        String tagRequired = NO_DAMAGE.get(event.getDamager().getType());
        if (tagRequired != null && event.getEntity().getScoreboardTags().contains(tagRequired)) {
            event.setCancelled(true);
        }
    }
}
