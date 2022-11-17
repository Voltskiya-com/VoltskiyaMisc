package com.voltskiya.misc.gms;

import com.voltskiya.lib.acf.BaseCommand;
import com.voltskiya.lib.acf.annotation.CommandAlias;
import com.voltskiya.lib.acf.annotation.CommandCompletion;
import com.voltskiya.lib.acf.annotation.CommandPermission;
import com.voltskiya.lib.acf.annotation.Default;
import com.voltskiya.lib.acf.annotation.Name;
import com.voltskiya.lib.acf.annotation.Optional;
import apple.mc.utilities.player.chat.SendMessage;
import apple.utilities.util.Pretty;
import com.voltskiya.misc.VoltskiyaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandGamemode implements SendMessage {

    public CommandGamemode() {
        VoltskiyaPlugin pl = VoltskiyaPlugin.get();
        pl.registerCommand(new CommandGMC());
        pl.registerCommand(new CommandGMSP());
        pl.registerCommand(new CommandGMS());
        pl.registerCommand(new CommandGMA());
    }

    private void handleCommand(CommandSender sender, String playerName, GameMode gamemode) {
        Player player = playerName == null ? null : Bukkit.getPlayer(playerName);
        String gmString = Pretty.upperCaseFirst(gamemode.name().toLowerCase());
        if (player != null) {
            player.setGameMode(gamemode);
            green(sender, "Set %s's gamemode to %s", player.getName(), gmString);
            green(player, "Your gamemode has been updated to %s", gmString);
            return;
        }
        if (sender instanceof Player senderPlayer) {
            senderPlayer.setGameMode(gamemode);
            green(sender, "Set own gamemode to %s", gmString);
            return;
        }
        red(sender, "Specify a player");
    }

    @CommandAlias("gmc")
    @CommandPermission("minecraft.command.gamemode")
    public class CommandGMC extends BaseCommand implements SendMessage {

        @Default()
        @CommandCompletion("@players")
        public void gamemode(CommandSender sender, @Optional() @Name("player") String player) {
            handleCommand(sender, player, GameMode.CREATIVE);
        }


    }

    @CommandAlias("gmsp")
    @CommandPermission("minecraft.command.gamemode")
    public class CommandGMSP extends BaseCommand implements SendMessage {

        @Default()
        @CommandCompletion("@players")
        public void gamemode(CommandSender sender, @Optional() @Name("player") String player) {
            handleCommand(sender, player, GameMode.SPECTATOR);
        }
    }

    @CommandAlias("gms")
    @CommandPermission("minecraft.command.gamemode")
    public class CommandGMS extends BaseCommand implements SendMessage {

        @Default()
        @CommandCompletion("@players")
        public void gamemode(CommandSender sender, @Optional() @Name("player") String player) {
            handleCommand(sender, player, GameMode.SURVIVAL);
        }
    }

    @CommandAlias("gma")
    @CommandPermission("minecraft.command.gamemode")
    public class CommandGMA extends BaseCommand implements SendMessage {

        @Default()
        @CommandCompletion("@players")
        public void gamemode(CommandSender sender, @Optional() @Name("player") String player) {
            handleCommand(sender, player, GameMode.ADVENTURE);
        }
    }
}
