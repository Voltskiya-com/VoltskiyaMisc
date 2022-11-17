package apple.voltskiya.miscellaneous.drops.items;

import com.voltskiya.lib.acf.BaseCommand;
import com.voltskiya.lib.acf.annotation.CommandAlias;
import com.voltskiya.lib.acf.annotation.CommandCompletion;
import com.voltskiya.lib.acf.annotation.CommandPermission;
import com.voltskiya.lib.acf.annotation.Name;
import com.voltskiya.lib.acf.annotation.Subcommand;
import apple.mc.utilities.inventory.item.InventoryUtils;
import apple.mc.utilities.player.chat.SendMessage;
import apple.voltskiya.miscellaneous.VoltskiyaPlugin;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@CommandAlias("give_asi")
@CommandPermission("voltskiya.gm")
public class ItemFlagsCommand extends BaseCommand implements SendMessage {

    public ItemFlagsCommand() {
        VoltskiyaPlugin.get().registerCommand(this);
        VoltskiyaPlugin.get().getCommandManager().getCommandCompletions()
            .registerAsyncCompletion("get_special_items",
                (c) -> SpecialItemDatabase.get().getNames());
    }

    @Subcommand("give")
    @CommandCompletion("@get_special_items")
    public void special(Player player, @Name("item_name") String give) {
        @Nullable SpecialItemStack item = SpecialItemDatabase.get().get(give);
        if (item == null) {
            red(player, "There are no items with the name '%s'!", give);
            return;
        }
        player.getInventory().addItem(item.toItem());
    }

    @Subcommand("add")
    public void add(Player player) {
        @Nullable ItemStack item = getPlayerItem(player);
        if (item == null)
            return;
        SpecialItemStack special = new SpecialItemStack(item);
        SpecialItemDatabase.get().add(special);
        green(player, "Added '%s' as a new special item", special.getNameUid());
    }

    @Subcommand("flag")
    @CommandCompletion("[flag]")
    public void flag(Player player, String flag) {
        ItemStack item = getPlayerItem(player);
        if (item == null)
            return;
        InventoryUtils.get().addItemFlags(item, flag);
        green(player, "Added '%s' to the list of flags on your current item", flag);
    }

    @Nullable
    private ItemStack getPlayerItem(Player player) {
        @NotNull ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType().isAir()) {
            green(player, "There is no item in your hand!");
            return null;
        }
        return item;
    }

    @Subcommand("list_flags")
    public void list(Player player) {
        ItemStack item = getPlayerItem(player);
        if (item == null)
            return;
        String[] itemFlags = InventoryUtils.get().getItemFlags(item);
        String message = String.join(", ", itemFlags);
        green(player, "The item flags on this item are \"%s\"", message);
    }
}
