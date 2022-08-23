package apple.voltskiya.miscellaneous.gms;

import apple.voltskiya.miscellaneous.VoltskiyaPlugin;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Levelled;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@CommandAlias("setwater")
@CommandPermission("apple.misc.op")
public class CommandSetWater extends BaseCommand {
    public CommandSetWater() {
        VoltskiyaPlugin.get().getCommandManager().registerCommand(this);
    }

    @Default
    @CommandCompletion("x1 y1 z1 x2 y2 z2 level")
    public void setWater(Player sender, int x1, int y1, int z1, int x2, int y2, int z2, int level) {
        for (; x1 <= x2; x1++) {
            for (; y1 <= y2; y1++) {
                for (; z1 <= z2; z1++) {
                    @NotNull BlockData water = Material.WATER.createBlockData();
                    if (water instanceof Levelled levelled) {
                        ((Levelled) water).setLevel(level);
                    }
                    sender.getWorld().getBlockAt(x1, y1, z1).setBlockData(water,false);
                }
            }
        }
        sender.sendMessage(ChatColor.GREEN + "Set blocks to water level " + level);
    }
}
