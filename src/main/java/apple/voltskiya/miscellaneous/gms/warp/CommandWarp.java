package apple.voltskiya.miscellaneous.gms.warp;

import apple.mc.utilities.player.chat.SendMessage;
import apple.voltskiya.miscellaneous.VoltskiyaPlugin;
import co.aikar.commands.BaseCommand;
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
        VoltskiyaPlugin.get().getCommandManager().getCommandCompletions()
            .registerAsyncCompletion("warps", (c) -> WarpDatabase.get().list());
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
}
