package apple.voltskiya.miscellaneous.gms;

import apple.voltskiya.miscellaneous.VoltskiyaPlugin;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@CommandAlias("flyspeed")
@CommandPermission("gm.flyspeed")
public class CommandSetFlyspeed extends BaseCommand {
    public CommandSetFlyspeed() {
        VoltskiyaPlugin.get().getCommandManager().registerCommand(this);
    }

    @Default
    @CommandCompletion("speed")
    public void flySpeed(Player player, float speed) {
        if (speed > 10 || speed < 0) {
            player.sendMessage(ChatColor.RED + "The speed must be between 0 and 10");
            return;
        }
        player.sendMessage(ChatColor.GREEN + String.format("Set your flyspeed to %.1f", speed));
        player.setFlySpeed(speed / 10);
    }
}
