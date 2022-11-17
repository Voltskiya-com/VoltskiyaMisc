package apple.voltskiya.miscellaneous.gms.give.tagged;

import apple.mc.utilities.inventory.item.InventoryUtils;
import apple.mc.utilities.player.chat.SendMessage;
import apple.voltskiya.miscellaneous.VoltskiyaPlugin;
import com.voltskiya.lib.acf.BaseCommand;
import com.voltskiya.lib.acf.BukkitCommandCompletionContext;
import com.voltskiya.lib.acf.annotation.CommandAlias;
import com.voltskiya.lib.acf.annotation.CommandCompletion;
import com.voltskiya.lib.acf.annotation.Default;
import com.voltskiya.lib.acf.annotation.Name;
import com.voltskiya.lib.acf.annotation.Single;
import com.voltskiya.lib.acf.annotation.Split;
import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import de.tr7zw.nbtapi.NBTList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@CommandAlias("givetag")
public class CommandGiveTagged extends BaseCommand implements SendMessage {


    private static final String SPAWN_EGG_STRING = "_SPAWN_EGG";

    public CommandGiveTagged() {
        VoltskiyaPlugin.get().getCommandManager().getCommandCompletions()
            .registerCompletion("spawneggs", this::spawnEggs);
        VoltskiyaPlugin.get().registerCommand(this);
    }

    private Collection<String> spawnEggs(BukkitCommandCompletionContext context) {
        return Arrays.stream(Material.values()).map(Material::name)
            .filter(name -> name.endsWith(SPAWN_EGG_STRING)).map(String::toLowerCase)
            .map(str -> str.substring(0, str.length() - SPAWN_EGG_STRING.length()))
            .collect(Collectors.toList());
    }

    @Default()
    @CommandCompletion("@spawneggs [tags...]")
    public void give(Player player, @Name("SpawnEgg") @Single String spawnEgg,
        @Name("Tags") @Split(" ") String[] scoreboardTags) {
        Material material = Material.matchMaterial(spawnEgg + SPAWN_EGG_STRING);
        if (material == null) {
            red(player, "'%s' not found", spawnEgg);
            return;
        }
        NBTItem nbt = new NBTItem(InventoryUtils.get().makeItem(material));
        final NBTCompound entityTag = nbt.getOrCreateCompound("EntityTag");
        final NBTList<String> tags = entityTag.getStringList("Tags");
        tags.addAll(Arrays.asList(scoreboardTags));
        final ItemStack item = nbt.getItem();
        item.add(63);
        player.getInventory().addItem(item);
    }
}
