package com.voltskiya.misc.admin.console.command;

import com.voltskiya.lib.acf.BaseCommand;
import com.voltskiya.lib.acf.annotation.CommandAlias;
import com.voltskiya.lib.acf.annotation.CommandCompletion;
import com.voltskiya.lib.acf.annotation.CommandPermission;
import com.voltskiya.lib.acf.annotation.Subcommand;
import com.voltskiya.misc.VoltskiyaPlugin;
import com.voltskiya.misc.admin.PluginAdmin;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("console")
@CommandPermission("volt.admin.console")
public class CommandListenerCommand extends BaseCommand {

    public CommandListenerCommand() {
        VoltskiyaPlugin.get().getCommandManager().registerCommand(this);
    }

    @Subcommand("listen_to_commands")
    @CommandPermission("volt.admin.console.commands")
    public class CommandListenerCommandSub extends BaseCommand {

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
}