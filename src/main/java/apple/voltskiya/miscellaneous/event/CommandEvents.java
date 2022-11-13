package apple.voltskiya.miscellaneous.event;

import apple.voltskiya.miscellaneous.VoltskiyaPlugin;
import apple.voltskiya.miscellaneous.event.basalt_dust_storm.BasaltDustStorm;
import apple.lib.acf.BaseCommand;
import apple.lib.acf.annotation.CommandAlias;
import apple.lib.acf.annotation.CommandCompletion;
import apple.lib.acf.annotation.CommandPermission;
import apple.lib.acf.annotation.Subcommand;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@CommandAlias("event_natural")
@CommandPermission("event_natural")
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
