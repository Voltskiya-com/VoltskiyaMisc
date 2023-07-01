package com.voltskiya.misc.gms.warp;

import apple.mc.utilities.player.chat.SendMessage;
import com.voltskiya.lib.acf.BaseCommand;
import com.voltskiya.lib.acf.annotation.CommandAlias;
import com.voltskiya.lib.acf.annotation.CommandPermission;
import com.voltskiya.lib.acf.annotation.Default;
import com.voltskiya.lib.acf.annotation.Subcommand;
import com.voltskiya.misc.VoltskiyaPlugin;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

@CommandAlias("back")
@CommandPermission("volt.gm.cmi.tp")
public class CommandBack extends BaseCommand implements SendMessage {

    public CommandBack() {
        VoltskiyaPlugin.get().registerCommand(this);
    }

    @Default
    public void back(Player player) {
        Location back = LocationHistoryDatabase.back(player.getUniqueId(), player.getLocation());
        if (back == null) {
            red(player, "There are no locations for you to teleport back to");
            return;
        }
        player.teleport(back, PlayerTeleportEvent.TeleportCause.SPECTATE);
    }

    @Subcommand("forward")
    public void forward(Player player) {
        CommandForward.get().forward(player);
    }

    @Subcommand("clear all")
    public void clearHistory(Player player) {
        if (LocationHistoryDatabase.clearHistory(player.getUniqueId())) {
            green(player, "Successfully cleared your location history");
        } else {
            red(player, "Failed to clear your history. You have no history to clear");
        }
    }

    @Subcommand("clear one")
    public void clear(Player player) {
        if (LocationHistoryDatabase.clearOne(player.getUniqueId())) {
            green(player, "Successfully cleared a single location in your location history");
        } else {
            red(player,
                "Failed to clear your history. You either have no history to clear or you're at the end of your history");
        }
    }
}
