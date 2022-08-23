package apple.voltskiya.miscellaneous.wanted;

import apple.voltskiya.miscellaneous.VoltskiyaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public class VillageCreationListener implements Listener {
    public VillageCreationListener() {
        Bukkit.getPluginManager().registerEvents(this, VoltskiyaPlugin.get());
    }
}
