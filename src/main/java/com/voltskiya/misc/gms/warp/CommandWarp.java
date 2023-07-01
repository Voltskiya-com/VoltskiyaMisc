package com.voltskiya.misc.gms.warp;

import apple.mc.utilities.player.chat.SendMessage;
import com.voltskiya.lib.acf.BaseCommand;
import com.voltskiya.lib.acf.BukkitCommandCompletionContext;
import com.voltskiya.lib.acf.CommandCompletions;
import com.voltskiya.lib.acf.annotation.CommandAlias;
import com.voltskiya.lib.acf.annotation.CommandCompletion;
import com.voltskiya.lib.acf.annotation.CommandPermission;
import com.voltskiya.lib.acf.annotation.Name;
import com.voltskiya.lib.acf.annotation.Subcommand;
import com.voltskiya.misc.VoltskiyaPlugin;
import java.util.Collection;
import java.util.Locale;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

@CommandAlias("warp")
@CommandPermission("volt.gm.warp")
public class CommandWarp extends BaseCommand implements SendMessage {

    public CommandWarp() {
        VoltskiyaPlugin.get().registerCommand(this);
        CommandCompletions<BukkitCommandCompletionContext> completions = VoltskiyaPlugin.get()
            .getCommandManager().getCommandCompletions();
        completions.registerAsyncCompletion("warps", this::listWarps);
        completions.registerAsyncCompletion("checkpoint_locations",
            (c) -> LocationHistoryDatabase.checkpointList(c.getPlayer().getUniqueId()));
    }

    public Collection<String> listWarps(BukkitCommandCompletionContext context) {
        String filter = context.getContextValue(String.class);
        return WarpDatabase.get().list().stream().filter(warp -> warp.startsWith(filter))
            .map((warp) -> trimWarpName(filter, warp)).toList();
    }

    private String trimWarpName(String filter, WarpEntry warp) {
        String warpName = warp.getName();
        int filterSpacesCount = countSpaces(filter);
        if (filterSpacesCount == 0)
            return warpName;
        char[] warpChars = warpName.toCharArray();
        for (int i = 0; i < warpChars.length; i++) {
            if (warpChars[i] == ' ') {
                if (--filterSpacesCount == 0)
                    return warpName.substring(i + 1);
            }
        }
        return "";
    }

    private int countSpaces(String name) {
        int spacesCount = 0;
        for (char c : name.toCharArray()) {
            if (c == ' ')
                spacesCount++;
        }
        return spacesCount;
    }

    @Subcommand("tp")
    @CommandCompletion("@warps")
    public void warp(Player player, @Name("[name]") String name) {
        WarpEntry warp = WarpDatabase.get().getWarp(name);
        if (warp == null) {
            red(player, "There are no warps named '%s'", name);
            return;
        }
        player.teleport(warp.getLocation(), TeleportCause.COMMAND);
    }

    @Subcommand("set")
    @CommandCompletion("[folder] [name...]")
    public void set(Player player, @Name("[name]") String name) {
        Location loc = player.getLocation();
        WarpDatabase.get().create(loc, name);
        green(player, "The warp [%d, %d, %d] in \"%s\" has been saved under the name \"%s\"",
            loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), loc.getWorld().getName(), name);
    }

    @Subcommand("remove")
    @CommandCompletion("@warps")
    public void removewarp(Player player, @Name("[name]") String name) {
        name = name.trim().toLowerCase(Locale.ROOT);
        WarpEntry warp = WarpDatabase.get().remove(name);
        if (warp == null) {
            red(player, "There are no warps named '%s'", name);
            return;
        }
        Location loc = warp.getLocation();
        green(player, "The warp [%d, %d, %d] in \"%s\" has been removed. '%s' no longer exists",
            loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), loc.getWorld().getName(), name);
    }

    @Subcommand("private")
    public class PrivateCommandWarp extends BaseCommand implements SendMessage {

        @Subcommand("tp")
        @CommandCompletion("@checkpoint_locations")
        public void checkpoint(Player player, String name) {
            name = name.trim().toLowerCase(Locale.ROOT);
            Location checkpoint = LocationHistoryDatabase.checkpoint(player.getUniqueId(), name);
            if (checkpoint == null) {
                red(player, "There are no checkpoints named '%s'", name);
                return;
            }
            player.teleport(checkpoint, PlayerTeleportEvent.TeleportCause.SPECTATE);
        }

        @Subcommand("set")
        @CommandCompletion("[name_here]")
        public void setCheckpoint(Player player, String name) {
            name = name.trim().toLowerCase(Locale.ROOT);
            PlayerLocationEntry checkpoint = LocationHistoryDatabase.createCheckpoint(
                player.getUniqueId(), player.getLocation(), name);
            green(player,
                "The checkpoint [%d, %d, %d] in \"%s\" has been saved under the name '%s'",
                (int) checkpoint.getX(), (int) checkpoint.getY(), (int) checkpoint.getZ(),
                player.getWorld().getName(), name);
        }

        @Subcommand("remove")
        @CommandCompletion("@checkpoint_locations")
        public void removeCheckpoint(Player player, String name) {
            name = name.trim().toLowerCase(Locale.ROOT);
            PlayerLocationEntry checkpoint = LocationHistoryDatabase.removeCheckpoint(
                player.getUniqueId(), name);
            if (checkpoint == null) {
                red(player, "There are no checkpoints named '%s'", name);
            } else {
                green(player,
                    "The checkpoint [%d, %d, %d] in \"%s\" has been removed. '%s' no longer exists",
                    (int) checkpoint.getX(), (int) checkpoint.getY(), (int) checkpoint.getZ(),
                    player.getWorld().getName(), name);
            }
        }
    }
}
