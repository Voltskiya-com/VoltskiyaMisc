package com.voltskiya.misc.admin.console.monitor;

import com.voltskiya.lib.acf.BaseCommand;
import com.voltskiya.lib.acf.annotation.CommandAlias;
import com.voltskiya.lib.acf.annotation.CommandPermission;
import com.voltskiya.lib.acf.annotation.Subcommand;
import apple.utilities.util.SystemUsage;
import com.voltskiya.misc.VoltskiyaPlugin;
import java.util.concurrent.atomic.AtomicBoolean;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.jetbrains.annotations.NotNull;

@CommandAlias("console.monitor")
@CommandPermission("apple.misc.op")
public class CommandTaskManager extends BaseCommand {

    private final AtomicBoolean shouldRun = new AtomicBoolean(false);

    public CommandTaskManager() {
        VoltskiyaPlugin.get().getCommandManager().registerCommand(this);
    }

    @Subcommand("show")
    public void show() {
        shouldRun.set(true);
        Bukkit.getScheduler().scheduleSyncDelayedTask(VoltskiyaPlugin.get(), this::setScores, 20);
    }

    @Subcommand("stop")
    public void stop() {
        shouldRun.set(false);
        Scoreboard mainScoreboardManager = Bukkit.getServer().getScoreboardManager()
            .getMainScoreboard();
        Objective scoreboard = mainScoreboardManager.getObjective("resource_monitor");
        if (scoreboard != null)
            scoreboard.unregister();
    }

    private void setScores() {
        Scoreboard mainScoreboardManager = Bukkit.getServer().getScoreboardManager()
            .getMainScoreboard();
        Objective scoreboard = mainScoreboardManager.getObjective("resource_monitor");
        if (scoreboard == null) {
            scoreboard = mainScoreboardManager.registerNewObjective("resource_monitor", "dummy",
                Component.text("Resource Monitor"));
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
        usedMemory.setScore(
            (int) ((SystemUsage.getTotalMemory() - SystemUsage.getFreeMemory()) / 1000000));

        @NotNull Score freeMemory = scoreboard.getScore("free_memory");
        freeMemory.setScore((int) (SystemUsage.getFreeMemory() / 1000000));
        Bukkit.getScheduler().scheduleSyncDelayedTask(VoltskiyaPlugin.get(), this::setScores, 20);
    }
}
