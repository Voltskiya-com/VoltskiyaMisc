package com.voltskiya.misc.event;

import com.voltskiya.lib.acf.BaseCommand;
import com.voltskiya.lib.acf.annotation.CommandAlias;
import com.voltskiya.lib.acf.annotation.CommandCompletion;
import com.voltskiya.lib.acf.annotation.CommandPermission;
import com.voltskiya.lib.acf.annotation.Subcommand;
import com.voltskiya.misc.VoltskiyaPlugin;
import com.voltskiya.misc.event.basalt_dust_storm.BasaltDustStorm;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@CommandAlias("natural_event")
@CommandPermission("volt.gm.hostile.natural_event")
public class CommandEvents extends BaseCommand {

    public CommandEvents() {
        VoltskiyaPlugin.get().getCommandManager().registerCommand(this);
    }

    @Subcommand("basalt")
    public class Basalt extends BaseCommand {

        @Subcommand("dust_storm")
        @CommandCompletion("radius secondsToLast")
        public void dustStorm(Player player, int radius, int secondsToLast) {
            Location center = player.getLocation();
            new BasaltDustStorm(center, radius, secondsToLast);
        }
    }
}
