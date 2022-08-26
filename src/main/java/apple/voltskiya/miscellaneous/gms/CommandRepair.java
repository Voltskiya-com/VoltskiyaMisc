package apple.voltskiya.miscellaneous.gms;

import apple.voltskiya.miscellaneous.VoltskiyaPlugin;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

@CommandAlias("repair")
@CommandPermission("gm.repair")
public class CommandRepair extends BaseCommand {

    public CommandRepair() {
        VoltskiyaPlugin.get().getCommandManager().registerCommand(this);
    }

    @Subcommand("percentage")
    @CommandCompletion("percentage")
    public void repairPerc(Player player, int points) {
        if (points <= 0 || points > 100) {
            player.sendMessage(ChatColor.RED + "Please specify a percentage between 0 and 100");
            return;
        }
        double perc = points / 100d;
        @NotNull ItemStack item = player.getInventory().getItemInMainHand();
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta instanceof Damageable damageable) {
            short maxDura = item.getType().getMaxDurability();
            damageable.setDamage(maxDura - (int) (maxDura * perc));
            item.setItemMeta(itemMeta);
        }

    }

    @Default()
    @Subcommand("full")
    public void repair(Player player) {
        @NotNull ItemStack item = player.getInventory().getItemInMainHand();
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta instanceof Damageable damageable) {
            damageable.setDamage(0);
            item.setItemMeta(damageable);
        }
    }

    @Default()
    @Subcommand("inventory")
    public void repairInventory(Player player) {
        for (ItemStack item : player.getInventory().getContents()) {
            if (item == null)
                continue;
            if (item.getItemMeta() instanceof Damageable damageable) {
                damageable.setDamage(0);
                item.setItemMeta(damageable);
            }
        }
    }

    @Subcommand("raw")
    @CommandCompletion("raw")
    public void repairRaw(Player player, int points) {
        if (points <= 0) {
            player.sendMessage(ChatColor.RED + "Please specify a value above 0");
            return;
        }
        @NotNull ItemStack item = player.getInventory().getItemInMainHand();
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta != null) {
            if (itemMeta instanceof org.bukkit.inventory.meta.Damageable) {
                final org.bukkit.inventory.meta.Damageable damageable = (org.bukkit.inventory.meta.Damageable) itemMeta;
                short maxDura = item.getType().getMaxDurability();
                damageable.setDamage(maxDura - Math.min(maxDura, points));
                item.setItemMeta(itemMeta);
            }
        }
    }
}
