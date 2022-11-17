package com.voltskiya.misc.tool.snow;

import com.voltskiya.lib.acf.annotation.CommandPermission;
import apple.mc.utilities.player.chat.SendMessage;
import apple.utilities.util.Pretty;
import com.voltskiya.misc.VoltskiyaPlugin;
import com.voltskiya.misc.tool.ModulePowerTool;
import com.voltskiya.lib.acf.BaseCommand;
import com.voltskiya.lib.acf.annotation.CommandAlias;
import com.voltskiya.lib.acf.annotation.CommandCompletion;
import com.voltskiya.lib.acf.annotation.Default;
import com.voltskiya.lib.acf.annotation.Name;
import com.voltskiya.lib.acf.annotation.Optional;
import com.voltskiya.lib.acf.annotation.Subcommand;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@CommandAlias("snowtool")
@CommandPermission("volt.gm.dev.snowtool")
public class SnowToolCommand extends BaseCommand implements SendMessage {

    public static final List<String> SNOW_TOOL_MODES = Arrays.stream(SnowToolWand.Mode.values())
        .map(o1 -> Pretty.upperCaseFirst(o1.name())).toList();

    public SnowToolCommand() {
        VoltskiyaPlugin.get().getCommandManager().registerCommand(this);
        VoltskiyaPlugin.get().getCommandManager().getCommandCompletions()
            .registerCompletion("snow_tool_modes", o -> SNOW_TOOL_MODES);
    }

    @Default
    public void giveWand(Player player) {
        player.getInventory()
            .addItem(ModulePowerTool.SNOW_WAND.createItem(Material.WHITE_TULIP, "Wand"));
    }

    @Subcommand("give")
    public void giveWand2(Player player) {
        giveWand(player);
    }

    @Subcommand("radius")
    @CommandCompletion("#")
    public void radius(Player player, @Name("radius") Integer radius) {
        SnowToolWand wand = getWand(player);
        int oldRadius = wand.getRadius();
        if (radius == null) {
            green(player, "The radius is set to %d", oldRadius);
        } else {
            wand.setRadius(radius);
            player.sendMessage(
                String.format("The radius was set from %d to %d", oldRadius, radius));
        }
    }

    @Subcommand("force")
    @CommandCompletion("#")
    public void force(Player player, @Optional Integer force) {
        SnowToolWand wand = getWand(player);
        int oldRadius = wand.getForce();
        if (force == null) {
            green(player, "The force is set to %d", oldRadius);
        } else {
            wand.setForce(force);
            player.sendMessage(String.format("The force was set from %d to %d", oldRadius, force));
        }
    }

    @Subcommand("mode")
    @CommandCompletion("@snow_tool_modes")
    public void mode(Player player, @Optional String newModeString) {
        @NotNull SnowToolWand wand = getWand(player);
        SnowToolWand.Mode oldMode = wand.getMode();
        String oldModeString = Pretty.upperCaseFirst(oldMode.name());
        if (newModeString == null) {
            player.sendMessage(
                ChatColor.GREEN + String.format("The current oldMode is '%s'", oldModeString));
        } else {
            SnowToolWand.Mode newMode;
            try {
                newMode = SnowToolWand.Mode.valueOf(newModeString.toUpperCase(Locale.ROOT));
            } catch (IllegalArgumentException e) {
                player.sendMessage(
                    ChatColor.RED + String.format("'%s' is not a valid newMode", newModeString));
                return;
            }
            wand.setMode(newMode);
            green(player, "The mode was switched from '%s' to '%s'", oldModeString, newModeString);
        }
    }

    private SnowToolWand getWand(Player player) {
        return ModulePowerTool.SNOW_WAND.getWand(player);
    }
}
