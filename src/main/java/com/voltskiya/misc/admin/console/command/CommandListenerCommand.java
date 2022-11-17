package com.voltskiya.misc.admin.console.command;

import com.voltskiya.misc.VoltskiyaPlugin;
import com.voltskiya.misc.admin.PluginAdmin;
import com.voltskiya.lib.acf.BaseCommand;
import com.voltskiya.lib.acf.annotation.CommandAlias;
import com.voltskiya.lib.acf.annotation.CommandCompletion;
import com.voltskiya.lib.acf.annotation.CommandPermission;
import com.voltskiya.lib.acf.annotation.Subcommand;
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
        PluginAdmin.get().enable(shouldRun);
        sender.sendMessage(shouldRun ? "running" : "not running");
    }

    @Subcommand("list")
    public void list(Player player) {
        player.openInventory(new CommandBlockGui(player).getInventory());
    }
}
