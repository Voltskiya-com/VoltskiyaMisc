package apple.voltskiya.miscellaneous.gms;

import apple.lib.acf.BaseCommand;
import apple.lib.acf.annotation.CommandAlias;
import apple.lib.acf.annotation.CommandCompletion;
import apple.lib.acf.annotation.CommandPermission;
import apple.lib.acf.annotation.Default;
import apple.voltskiya.miscellaneous.VoltskiyaPlugin;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@CommandAlias("flyspeed")
@CommandPermission("gm.flyspeed")
public class CommandSetFlyspeed extends BaseCommand {

    public CommandSetFlyspeed() {
        VoltskiyaPlugin.get().getCommandManager().registerCommand(this);
    }

    @Default
    @CommandCompletion("@range:10")
    public void flySpeed(Player player, float speed) {
        if (speed > 10 || speed < 0) {
            player.sendMessage(ChatColor.RED + "The speed must be between 0 and 10");
            return;
        }
        player.sendMessage(ChatColor.GREEN + String.format("Set your flyspeed to %.1f", speed));
        player.setFlySpeed(speed / 10);
    }
}
