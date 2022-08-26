package apple.voltskiya.miscellaneous.gms.warp;

import apple.mc.utilities.player.chat.SendMessage;
import apple.voltskiya.miscellaneous.VoltskiyaPlugin;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.BukkitCommandCompletionContext;
import co.aikar.commands.CommandCompletions;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.Name;
import co.aikar.commands.annotation.Subcommand;
import java.util.Locale;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

@CommandAlias("warp")
public class CommandWarp extends BaseCommand implements SendMessage {

    public CommandWarp() {
        VoltskiyaPlugin.get().registerCommand(this);
        CommandCompletions<BukkitCommandCompletionContext> completions = VoltskiyaPlugin.get()
            .getCommandManager().getCommandCompletions();
        completions.registerAsyncCompletion("warps", (c) -> WarpDatabase.get().list());
        completions.registerAsyncCompletion("checkpoint_locations",
            (c) -> LocationHistoryDatabase.checkpointList(c.getPlayer().getUniqueId()));
    }

    @Subcommand("tp")
    @CommandCompletion("@warps")
    public void warp(Player player, @Name("[name]") String name) {
        WarpEntry warp = WarpDatabase.get().getWarp(name);
        if (warp == null) {
            red(player, "There are no warps named '%s'", name);
            return;
        }
        player.teleport(warp.getLocation(), PlayerTeleportEvent.TeleportCause.SPECTATE);
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
