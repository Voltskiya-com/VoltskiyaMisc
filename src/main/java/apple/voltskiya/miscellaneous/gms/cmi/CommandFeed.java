package apple.voltskiya.miscellaneous.gms.cmi;

import com.voltskiya.lib.acf.BaseCommand;
import com.voltskiya.lib.acf.annotation.CommandAlias;
import com.voltskiya.lib.acf.annotation.CommandCompletion;
import com.voltskiya.lib.acf.annotation.CommandPermission;
import com.voltskiya.lib.acf.annotation.Default;
import com.voltskiya.lib.acf.annotation.Name;
import com.voltskiya.lib.acf.annotation.Optional;
import apple.mc.utilities.player.chat.SendMessage;
import apple.voltskiya.miscellaneous.VoltskiyaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("feed")
@CommandPermission("volt.gm.cmi.feed")
public class CommandFeed extends BaseCommand implements SendMessage {

    public CommandFeed() {
        VoltskiyaPlugin.get().registerCommand(this);
    }

    @Default
    @CommandCompletion("@players")
    public void feed(CommandSender sender, @Optional() @Name("player") String playerName) {
        Player player = playerName == null ? null : Bukkit.getPlayer(playerName);
        if (player != null) {
            feedPlayer(player);
            green(sender, "Fed %s", player.getName());
            green(player, "Your have been fed");
            return;
        }
        if (sender instanceof Player senderPlayer) {
            feedPlayer(senderPlayer);
            green(senderPlayer, "You have been fed");
            return;
        }
        red(sender, "Specify a player");
    }

    private void feedPlayer(Player player) {
        player.setFoodLevel(20);
        player.setSaturation(5);
    }
}
