package com.voltskiya.misc.admin.reload;

import com.voltskiya.lib.acf.BaseCommand;
import com.voltskiya.lib.acf.annotation.CommandAlias;
import com.voltskiya.lib.acf.annotation.CommandPermission;
import com.voltskiya.lib.acf.annotation.Default;
import com.voltskiya.misc.VoltskiyaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

@CommandAlias("pr")
@CommandPermission("bukkit.command.reload")
public class CommandReload extends BaseCommand {

    public CommandReload() {
        VoltskiyaPlugin.get().registerCommand(this);
    }

    @Default()
    public void pluginReload(CommandSender commandSender) {
        Bukkit.dispatchCommand(commandSender, "bukkit:reload confirm");
    }
}
