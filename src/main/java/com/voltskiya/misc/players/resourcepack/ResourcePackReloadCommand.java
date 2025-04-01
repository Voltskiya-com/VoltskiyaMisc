package com.voltskiya.misc.players.resourcepack;

import apple.mc.utilities.player.chat.SendMessage;
import com.voltskiya.lib.acf.BaseCommand;
import com.voltskiya.lib.acf.annotation.CommandAlias;
import com.voltskiya.lib.acf.annotation.CommandPermission;
import com.voltskiya.lib.acf.annotation.Subcommand;
import com.voltskiya.misc.VoltskiyaPlugin;
import org.bukkit.command.CommandSender;

@CommandAlias("resourcepack")
@CommandPermission("volt.game.resourcepack")
public class ResourcePackReloadCommand extends BaseCommand {

    public ResourcePackReloadCommand() {
        VoltskiyaPlugin.get().registerCommand(this);
    }

    @Subcommand("reload")
    public void reload(CommandSender sender) {
        ResourcePackConfig.get().reset();
        SendMessage.get().aqua(sender, "Reload the server (and make players rejoin/refresh resourcepack) to see the change");
    }
}
