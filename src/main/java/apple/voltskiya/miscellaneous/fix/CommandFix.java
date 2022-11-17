package apple.voltskiya.miscellaneous.fix;

import com.voltskiya.lib.acf.BaseCommand;
import com.voltskiya.lib.acf.annotation.CommandAlias;
import com.voltskiya.lib.acf.annotation.CommandPermission;
import com.voltskiya.lib.acf.annotation.Optional;
import com.voltskiya.lib.acf.annotation.Subcommand;
import apple.voltskiya.miscellaneous.VoltskiyaPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("fix_player")
@CommandPermission("volt.gm.player_fix")
public class CommandFix extends BaseCommand {

    public CommandFix() {
        VoltskiyaPlugin.get().getCommandManager().registerCommand(this);
    }

    @Subcommand("attributes")
    public void attributes(CommandSender sender, @Optional Player player) {
        if (player != null) {
            FixingAttribute.fixPlayerAttributes(player);
            sender.sendMessage(ChatColor.GREEN + "Fixed " + player.getName() + "'s attributes");
        } else if (sender instanceof Player) {
            FixingAttribute.fixPlayerAttributes((Player) sender);
            sender.sendMessage(ChatColor.GREEN + "Fixed " + sender.getName() + "'s attributes");
        } else {
            sender.sendMessage(ChatColor.RED + "Nobody had their attributes fixed");
        }
    }
}
