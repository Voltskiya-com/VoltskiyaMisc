package com.voltskiya.misc.gms.nsa;

import static net.kyori.adventure.text.format.NamedTextColor.DARK_RED;

import com.voltskiya.lib.acf.BaseCommand;
import com.voltskiya.lib.acf.annotation.CommandAlias;
import com.voltskiya.lib.acf.annotation.CommandCompletion;
import com.voltskiya.lib.acf.annotation.CommandPermission;
import com.voltskiya.lib.acf.annotation.Name;
import com.voltskiya.lib.acf.annotation.Subcommand;
import com.voltskiya.misc.VoltskiyaPlugin;
import java.time.Duration;
import java.util.List;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.title.Title.Times;
import org.bukkit.entity.Player;

@CommandAlias("nsa")
@CommandPermission("volt.nsa")
public class CommandNSA extends BaseCommand {

    public CommandNSA() {
        VoltskiyaPlugin.get().registerCommand(this);
    }

    @Subcommand("title")
    @CommandCompletion("@players")
    public void title(Player p1, @Name("player") Player player) {
        TextComponent I = Component.text("I", DARK_RED, TextDecoration.BOLD);
        TextComponent aml = Component.text(" AM L", DARK_RED, TextDecoration.UNDERLINED);
        Component i = Component.text("I", DARK_RED, TextDecoration.OBFUSCATED);
        TextComponent ste = Component.text("STE", DARK_RED, TextDecoration.STRIKETHROUGH, TextDecoration.BOLD);
        TextComponent ni = Component.text("NI", DARK_RED, TextDecoration.OBFUSCATED);
        TextComponent ng = Component.text("NG...", DARK_RED, TextDecoration.BOLD);
        Component main = Component.join(JoinConfiguration.noSeparators(), List.of(I, aml, i, ste, ni, ng));
        TextComponent sub = Component.text("APPLE IS THE NSA!!!", NamedTextColor.DARK_PURPLE, TextDecoration.UNDERLINED,
            TextDecoration.ITALIC);
        Title title = Title.title(main, sub, Times.times(Duration.ZERO, Duration.ofHours(1), Duration.ofHours(1)));
        player.showTitle(title);
        p1.showTitle(title);
    }


}
