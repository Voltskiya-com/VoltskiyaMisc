package apple.voltskiya.miscellaneous.conveyer_belt;

import apple.voltskiya.miscellaneous.VoltskiyaPlugin;
import org.bukkit.Bukkit;

public class ConveyorRunning implements Runnable {
    public static final long STEPS_PER_TICK = 1;

    public ConveyorRunning() {
        Bukkit.getScheduler().scheduleSyncDelayedTask(VoltskiyaPlugin.get(), this);
    }

    @Override
    public void run() {
        for (ConveyorBelt belt : ConveyorBeltManager.values()) {
            belt.run();
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask(VoltskiyaPlugin.get(), this,STEPS_PER_TICK);
    }
}
