package com.voltskiya.misc.spawn;

import com.voltskiya.misc.VoltskiyaPlugin;
import java.time.Duration;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.title.Title;
import org.bukkit.Location;
import org.bukkit.entity.HumanEntity;
import org.jetbrains.annotations.NotNull;
import voltskiya.apple.utilities.action.ActionMeta;
import voltskiya.apple.utilities.action.ActionReturn;
import voltskiya.apple.utilities.action.RepeatableActionImpl;
import voltskiya.apple.utilities.action.RepeatingActionManager;

public class PlayerSpawnAction {

    private static final String VOLTSKIYA = "Voltskiya";

    private static final String SPAWN = "spawn";
    private static final String SIMMER = "simmer";
    private final Location spawnLocation;
    private final HumanEntity player;
    private final TextComponent welcome = Component.text("Welcome to ")
        .style(Style.style(TextColor.color(0xacacad)));

    private final int duration = PlayerSpawnConfig.get().getInvulnerability();
    RepeatingActionManager actionManager = new RepeatingActionManager(
        VoltskiyaPlugin.get()).registerInit(this::runInit).registerFinally(this::runFinally)
        .registerAction(new RepeatableActionImpl(SPAWN, this::showTitle, duration,
            duration / VOLTSKIYA.length())).registerAction(
            new RepeatableActionImpl(SIMMER, this::simmerTitle, PlayerSpawnConfig.get().getSimmer(),
                5));

    public PlayerSpawnAction(Location spawnLocation, HumanEntity player) {
        this.spawnLocation = spawnLocation;
        this.player = player;
        actionManager.startActionAndStart(SPAWN);
    }

    private ActionReturn simmerTitle(ActionMeta meta) {
        return new ActionReturn(true);
    }

    protected void runInit() {
        Location location = this.spawnLocation.clone();
        location.add(0, PlayerSpawnConfig.get().getSpawnHeight(), 0);
        player.teleport(location);
        player.setInvulnerable(true);
    }

    protected void runFinally() {
        @NotNull TextComponent volt = Component.text(VOLTSKIYA)
            .style(Style.style(TextColor.color(0x730202), TextDecoration.BOLD));
        @NotNull Component message = Component.join(JoinConfiguration.noSeparators(), welcome,
            volt);
        Title.Times timings = Title.Times.times(Duration.ZERO, Duration.ofSeconds(3L),
            Duration.ofSeconds(2L));
        player.resetTitle();
        player.showTitle(Title.title(message, Component.empty(), timings));
        player.setInvulnerable(false);
        player.getWorld().playSound(
            Sound.sound(org.bukkit.Sound.UI_TOAST_CHALLENGE_COMPLETE.key(), Sound.Source.MASTER, 1f,
                1f));
    }

    private ActionReturn showTitle(ActionMeta meta) {
        int currentTick = meta.currentRepeat();
        @NotNull TextComponent volt;
        @NotNull TextComponent skiya;
        if (VOLTSKIYA.length() <= currentTick + 1) {
            volt = Component.text(VOLTSKIYA)
                .style(Style.style(TextColor.color(0x545454), TextDecoration.ITALIC));
            skiya = Component.empty();
        } else {
            volt = Component.text(VOLTSKIYA.substring(0, currentTick + 1))
                .style(Style.style(TextColor.color(0x545454), TextDecoration.ITALIC));
            skiya = Component.text(VOLTSKIYA.substring(currentTick + 1))
                .style(Style.style(TextColor.color(0x545454), TextDecoration.OBFUSCATED));
        }
        @NotNull Component message = Component.join(JoinConfiguration.noSeparators(), welcome, volt,
            skiya);
        Title.Times timings = Title.Times.times(Duration.ZERO, Duration.ofSeconds(5L),
            Duration.ofSeconds(3L));
        player.resetTitle();
        player.showTitle(Title.title(message, Component.empty(), timings));
        if (meta.isLastRun()) {
            actionManager.startAction(SIMMER);
            player.getWorld().playSound(
                Sound.sound(org.bukkit.Sound.BLOCK_BEACON_DEACTIVATE.key(), Sound.Source.PLAYER, 1f,
                    1f));
        }
        return new ActionReturn(true);
    }
}
