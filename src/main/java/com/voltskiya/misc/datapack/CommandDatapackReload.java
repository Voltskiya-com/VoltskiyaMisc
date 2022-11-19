package com.voltskiya.misc.datapack;

import com.voltskiya.lib.acf.BaseCommand;
import com.voltskiya.lib.acf.annotation.CommandAlias;
import com.voltskiya.lib.acf.annotation.CommandPermission;
import com.voltskiya.lib.acf.annotation.Default;
import com.voltskiya.misc.VoltskiyaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

@CommandAlias("dr")
@CommandPermission("minecraft.command.reload")
public class CommandDatapackReload extends BaseCommand {

    public CommandDatapackReload() {
        VoltskiyaPlugin.get().registerCommand(this);
    }

    @Default
    public void reload(CommandSender commandSender) {
        Bukkit.dispatchCommand(commandSender, "minecraft:reload");
    }
}
