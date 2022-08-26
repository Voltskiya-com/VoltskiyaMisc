package apple.voltskiya.miscellaneous.gms;

import apple.mc.utilities.inventory.item.InventoryUtils;
import apple.voltskiya.miscellaneous.VoltskiyaPlugin;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import java.util.Arrays;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

@CommandAlias("item_modify")
@CommandPermission("gm.item")
public class CommandItemDetails extends BaseCommand {

    public CommandItemDetails() {
        VoltskiyaPlugin.get().getCommandManager().registerCommand(this);
    }

    @Subcommand("rename")
    public void rename(Player player, String newName) {
        @NotNull ItemStack item = player.getInventory().getItemInMainHand();
        InventoryUtils.get().displayName(item, newName);
    }

    @Subcommand("relore")
    public void relore(Player player, String newLore) {
        @NotNull ItemStack item = player.getInventory().getItemInMainHand();
        InventoryUtils.get().lore(Arrays.asList(newLore.split("\\\\n")), item);
    }
}
