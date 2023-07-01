package com.voltskiya.misc.gms.item;

import apple.mc.utilities.inventory.item.InventoryUtils;
import com.voltskiya.lib.acf.BaseCommand;
import com.voltskiya.lib.acf.annotation.CommandAlias;
import com.voltskiya.lib.acf.annotation.CommandPermission;
import com.voltskiya.lib.acf.annotation.Subcommand;
import com.voltskiya.misc.VoltskiyaPlugin;
import java.util.Arrays;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

@CommandAlias("item_modify")
@CommandPermission("minecraft.command.give")
public class CommandItemDetails extends BaseCommand {

    public CommandItemDetails() {
        VoltskiyaPlugin.get().getCommandManager().registerCommand(this);
    }

    @CommandPermission("volt.gm.item.rename")
    @Subcommand("rename")
    public void rename(Player player, String newName) {
        @NotNull ItemStack item = player.getInventory().getItemInMainHand();
        InventoryUtils.get().displayName(item, newName);
    }

    @Subcommand("relore")
    @CommandPermission("volt.gm.item.relore")
    public void relore(Player player, String newLore) {
        @NotNull ItemStack item = player.getInventory().getItemInMainHand();
        InventoryUtils.get().lore(Arrays.asList(newLore.split("\\\\n")), item);
    }
}
