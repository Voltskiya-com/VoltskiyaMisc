package apple.voltskiya.miscellaneous.gms.checkpoint;

import apple.voltskiya.miscellaneous.VoltskiyaPlugin;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

@CommandAlias("forward")
public class CommandForward extends BaseCommand {
    private static CommandForward instance;

    public CommandForward() {
        instance = this;
        VoltskiyaPlugin.get().registerCommand(this);
    }

    public static CommandForward get() {
        return instance;
    }

    @Default
    public void forward(Player player) {
        Location back = LocationHistoryDatabase.forward(player.getUniqueId());
        if (back == null) {
            player.sendMessage(ChatColor.RED + "There are no locations for you to teleport forward to");
            return;
        }
        player.teleport(back, PlayerTeleportEvent.TeleportCause.SPECTATE);
    }
}
