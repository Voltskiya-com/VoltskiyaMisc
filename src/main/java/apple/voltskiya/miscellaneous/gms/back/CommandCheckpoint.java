package apple.voltskiya.miscellaneous.gms.back;

import apple.mc.utilities.player.chat.SendMessage;
import apple.voltskiya.miscellaneous.VoltskiyaPlugin;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.Locale;

@CommandAlias("checkpoint")
public class CommandCheckpoint extends BaseCommand implements SendMessage {
    public CommandCheckpoint() {
        VoltskiyaPlugin.get().registerCommand(this);
        VoltskiyaPlugin
                .get()
                .getCommandManager()
                .getCommandCompletions()
                .registerAsyncCompletion("checkpoint_locations",
                                         (c) -> LocationHistoryDatabase
                                                 .getInstance()
                                                 .checkpointList(c.getPlayer().getUniqueId())
                );
    }

    @Subcommand("tp")
    @CommandCompletion("@checkpoint_locations")
    public void checkpoint(Player player, String name) {
        name = name.trim().toLowerCase(Locale.ROOT);
        Location checkpoint = LocationHistoryDatabase.getInstance().checkpoint(player.getUniqueId(), name);
        if (checkpoint == null) {
            red(player, "There are no checkpoints named '%s'", name);
            return;
        }
        player.teleport(checkpoint, PlayerTeleportEvent.TeleportCause.SPECTATE);
    }

    @Subcommand("set|create")
    @CommandCompletion("[name_here]")
    public void setCheckpoint(Player player, String name) {
        name = name.trim().toLowerCase(Locale.ROOT);
        PlayerLocationEntry checkpoint = LocationHistoryDatabase
                .getInstance()
                .createCheckpoint(player.getUniqueId(), player.getLocation(), name);
        green(player,
              "The checkpoint [%d, %d, %d] in \"%s\" has been saved under the name '%s'",
              (int) checkpoint.getX(),
              (int) checkpoint.getY(),
              (int) checkpoint.getZ(),
              player.getWorld().getName(),
              name
        );
    }

    @Subcommand("remove")
    @CommandCompletion("@checkpoint_locations")
    public void removeCheckpoint(Player player, String name) {
        name = name.trim().toLowerCase(Locale.ROOT);
        PlayerLocationEntry checkpoint = LocationHistoryDatabase
                .getInstance()
                .removeCheckpoint(player.getUniqueId(), name);
        if (checkpoint == null) {
            red(player, "There are no checkpoints named '%s'", name);
        } else {
            green(player,
                  "The checkpoint [%d, %d, %d] in \"%s\" has been removed. '%s' no longer exists",
                  (int) checkpoint.getX(),
                  (int) checkpoint.getY(),
                  (int) checkpoint.getZ(),
                  player.getWorld().getName(),
                  name
            );
        }
    }
}
