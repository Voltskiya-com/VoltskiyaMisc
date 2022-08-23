package apple.voltskiya.miscellaneous.gms;

import apple.utilities.util.SystemUsage;
import apple.voltskiya.miscellaneous.VoltskiyaPlugin;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicBoolean;

@CommandAlias("resource")
@CommandPermission("apple.misc.op")
public class CommandTaskManager extends BaseCommand {
    private AtomicBoolean shouldRun = new AtomicBoolean(false);

    public CommandTaskManager() {
        VoltskiyaPlugin.get().getCommandManager().registerCommand(this);
    }

    @Subcommand("monitor show")
    public void show() {
        shouldRun.set(true);
        Bukkit.getScheduler().scheduleSyncDelayedTask(VoltskiyaPlugin.get(), this::setScores, 20);
    }

    @Subcommand("monitor stop")
    public void stop() {
        shouldRun.set(false);
        Scoreboard mainScoreboardManager = Bukkit.getServer().getScoreboardManager().getMainScoreboard();
        Objective scoreboard = mainScoreboardManager.getObjective("resource_monitor");
        if (scoreboard != null) scoreboard.unregister();
    }

    private void setScores() {
        Scoreboard mainScoreboardManager = Bukkit.getServer().getScoreboardManager().getMainScoreboard();
        Objective scoreboard = mainScoreboardManager.getObjective("resource_monitor");
        if (scoreboard == null) {
            scoreboard = mainScoreboardManager.registerNewObjective("resource_monitor", "dummy", Component.text("Resource Monitor"));
            scoreboard.setDisplaySlot(DisplaySlot.SIDEBAR);
        }
        if (!shouldRun.get()) {
            scoreboard.setDisplaySlot(null);
            return;
        }
        @NotNull Score cpuPerProcessor = scoreboard.getScore("cpu_usage_perc");
        cpuPerProcessor.setScore((int) (SystemUsage.getProcessCpuLoad() * 100));

        @NotNull Score cpuCount = scoreboard.getScore("cpu_count");
        cpuCount.setScore((int) SystemUsage.getProcessorCount());

        @NotNull Score totalMemory = scoreboard.getScore("total_memory");
        totalMemory.setScore((int) SystemUsage.getTotalMemory() / 1000000);

        @NotNull Score usedMemory = scoreboard.getScore("used_memory");
        usedMemory.setScore((int) ((SystemUsage.getTotalMemory() - SystemUsage.getFreeMemory()) / 1000000));

        @NotNull Score freeMemory = scoreboard.getScore("free_memory");
        freeMemory.setScore((int) (SystemUsage.getFreeMemory() / 1000000));
        Bukkit.getScheduler().scheduleSyncDelayedTask(VoltskiyaPlugin.get(), this::setScores, 20);
    }
}
