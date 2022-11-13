package apple.voltskiya.miscellaneous.gms;

import apple.mc.utilities.player.chat.SendMessage;
import apple.voltskiya.miscellaneous.VoltskiyaPlugin;
import apple.lib.acf.BaseCommand;
import apple.lib.acf.annotation.CommandAlias;
import apple.lib.acf.annotation.CommandCompletion;
import apple.lib.acf.annotation.CommandPermission;
import apple.lib.acf.annotation.Default;
import apple.lib.acf.annotation.Name;
import apple.lib.acf.annotation.Optional;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("heal")
@CommandPermission("gm.heal")
public class CommandHeal extends BaseCommand implements SendMessage {

    public CommandHeal() {
        VoltskiyaPlugin.get().registerCommand(this);
    }

    @Default
    @CommandPermission("gm.heal")
    @CommandCompletion("@players")
    public void heal(CommandSender sender, @Optional() @Name("player") String playerName) {
        Player player = playerName == null ? null : Bukkit.getPlayer(playerName);
        if (player != null) {
            healPlayer(player);
            green(sender, "Healed %s", player.getName());
            green(player, "Your have been healed");
            return;
        }
        if (sender instanceof Player senderPlayer) {
            healPlayer(senderPlayer);
            green(senderPlayer, "You have been healed");
            return;
        }
        red(sender, "Specify a player");
    }

    private void healPlayer(Player player) {
        AttributeInstance maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        player.setHealth(maxHealth == null ? 20 : maxHealth.getValue());
        player.setFoodLevel(20);
        player.setSaturation(5);
    }
}

