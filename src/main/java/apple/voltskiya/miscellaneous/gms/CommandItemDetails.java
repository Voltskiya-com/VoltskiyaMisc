package apple.voltskiya.miscellaneous.gms;

import apple.voltskiya.miscellaneous.VoltskiyaPlugin;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

@CommandAlias("item_modify")
@CommandPermission("gm.item")
public class CommandItemDetails extends BaseCommand {
    public CommandItemDetails() {
        VoltskiyaPlugin.get().getCommandManager().registerCommand(this);
    }

    @Subcommand("rename")
    public void rename(Player player, String newName) {
        @NotNull ItemStack item = player.getInventory().getItemInMainHand();
        ItemMeta im = item.getItemMeta();
        TextComponent name = new TextComponent();
        name.setText(newName);
        im.setDisplayNameComponent(new TextComponent[]{name});
        item.setItemMeta(im);
    }

    @Subcommand("relore")
    public void relore(Player player, String newLore) {
        @NotNull ItemStack item = player.getInventory().getItemInMainHand();
        ItemMeta im = item.getItemMeta();
        TextComponent name = new TextComponent();
        name.setText(newLore);
        im.setLore(Arrays.asList(newLore.split("\\\\n")));
        item.setItemMeta(im);
    }
}
