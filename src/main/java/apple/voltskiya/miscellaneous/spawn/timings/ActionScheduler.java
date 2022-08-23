package apple.voltskiya.miscellaneous.spawn.timings;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class ActionScheduler {
    private final Plugin plugin;
    private final List<ScheduledAction> actions = new ArrayList<>();
    private final Map<UUID, ScheduledAction> actionsAvailable = new HashMap<>();
    private ActionPhase phase = ActionPhase.IDLE;
    private ScheduledAction doFinally = null;
    private ScheduledAction init = null;

    public ActionScheduler(Plugin plugin) {
        this.plugin = plugin;
    }

    public void start() {
        synchronized (this) {
            if (phase != ActionPhase.IDLE) return;
            this.phase = ActionPhase.STARTING;
            schedule(0, this::runInit);
            this.phase = ActionPhase.RUNNING;
            scheduleSelf(0);
        }
    }

    private void scheduleSelf(int ticks) {
        schedule(ticks, this::tick);
    }

    private void tick() {
//        synchronized () {
//            actions.addAll(actionsToAdd);
//            actionsToAdd.clear();
//        }
//        synchronized (actionsAvailable) {
//            for (RepeatableAction action : actionsAvailable.values()) {
//                if (action.shouldStart()) {
//                    actions.add(action);
//                }
//            }
//        }
//        if (phase.shouldRun() && !actions.isEmpty()) {
//            doAction();
//            scheduleSelf(1);
//        } else {
//            phase = ActionPhase.STOPPING;
//            if (this.doFinally != null)
//                this.doFinally.run();
//            phase = ActionPhase.IDLE;
//        }
    }

    private void schedule(int ticks, Runnable action) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, action, ticks);
    }

    protected void runInit() {

    }

    protected void runFinally() {

    }

    private enum ActionPhase {
        IDLE,
        STARTING,
        RUNNING,
        STOPPING;
    }
}
