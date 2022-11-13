package apple.voltskiya.miscellaneous.command_output;

import apple.lib.acf.BaseCommand;
import apple.lib.acf.annotation.CommandAlias;
import apple.lib.acf.annotation.CommandCompletion;
import apple.lib.acf.annotation.CommandPermission;
import apple.lib.acf.annotation.Subcommand;
import apple.voltskiya.miscellaneous.VoltskiyaPlugin;
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
