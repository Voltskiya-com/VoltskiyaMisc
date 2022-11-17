package apple.voltskiya.miscellaneous.gamerules.damage;

import apple.voltskiya.miscellaneous.VoltskiyaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.HashMap;
import java.util.Map;

public class NoEntityDamageListener implements Listener {
    public static final Map<EntityType, String> NO_DAMAGE = new HashMap<>() {{
        put(EntityType.EVOKER_FANGS, "no_evoker_fangs_damage");
    }};

    public NoEntityDamageListener() {
        Bukkit.getPluginManager().registerEvents(this, VoltskiyaPlugin.get());
    }

    @EventHandler
    public void noDamage(EntityDamageByEntityEvent event) {
            String tagRequired = NO_DAMAGE.get(event.getDamager().getType());
            if (tagRequired != null && event.getEntity().getScoreboardTags().contains(tagRequired)) {
                event.setCancelled(true);
        }
    }
}
