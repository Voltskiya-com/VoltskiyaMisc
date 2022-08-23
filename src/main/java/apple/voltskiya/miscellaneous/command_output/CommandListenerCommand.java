package apple.voltskiya.miscellaneous.command_output;

import apple.voltskiya.miscellaneous.VoltskiyaPlugin;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("listen_to_commands")
@CommandPermission("admin.listen_to_commands")
public class CommandListenerCommand extends BaseCommand {
    public CommandListenerCommand() {
        VoltskiyaPlugin.get().getCommandManager().registerCommand(this);
    }

    @Subcommand("set_enabled")
    @CommandCompletion("true|false")
    public void setEnabled(CommandSender sender, boolean shouldRun) {
        PluginCommandFinder.get().enable(shouldRun);
        sender.sendMessage(shouldRun ? "running" : "not running");
    }

    @Subcommand("list")
    public void list(Player player) {
        player.openInventory(new CommandBlockGui(player).getInventory());
    }
}
