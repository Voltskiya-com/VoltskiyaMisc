package com.voltskiya.misc.drops;

import com.voltskiya.lib.acf.BaseCommand;
import com.voltskiya.lib.acf.BukkitCommandCompletionContext;
import com.voltskiya.lib.acf.CommandCompletions;
import com.voltskiya.lib.acf.annotation.CommandAlias;
import com.voltskiya.lib.acf.annotation.CommandCompletion;
import com.voltskiya.lib.acf.annotation.CommandPermission;
import com.voltskiya.lib.acf.annotation.Single;
import com.voltskiya.lib.acf.annotation.Subcommand;
import apple.mc.utilities.player.chat.SendMessage;
import apple.nms.decoding.iregistry.DecodeEntityTypes;
import apple.nms.decoding.iregistry.DecodeIRegistry;
import apple.nms.decoding.world.DecodeMinecraftKey;
import com.voltskiya.misc.VoltskiyaPlugin;
import com.voltskiya.misc.drops.block.gui.BlockTableGui;
import com.voltskiya.misc.drops.xp.LootXpTableManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

@CommandAlias("drops")
@CommandPermission("volt.game.edit.drops")
public class DropsCommand extends BaseCommand implements SendMessage {

    public DropsCommand() {
        VoltskiyaPlugin.get().registerCommand(this);
        CommandCompletions<BukkitCommandCompletionContext> commandCompletions = VoltskiyaPlugin.get()
            .getCommandManager().getCommandCompletions();
        Bukkit.getScheduler().scheduleSyncDelayedTask(VoltskiyaPlugin.get(), () -> {
            commandCompletions.registerStaticCompletion("entity_type", this::getEntityTypes);
            commandCompletions.registerAsyncCompletion("loottable_by_tag",
                context -> LootXpTableManager.getTags());
        }, 1);
    }

    private Collection<String> getEntityTypes() {
        Collection<String> completions = new ArrayList<>();
        for (EntityType<?> entityTypes : DecodeIRegistry.getEntityType()) {
            ResourceLocation name = DecodeEntityTypes.getKey(entityTypes);
            completions.add(name.toString());
            if (DecodeMinecraftKey.isMinecraft(name)) {
                completions.add(DecodeMinecraftKey.getKey(name));
            }
        }
        return completions;
    }


    @Subcommand("xp")
    public class Xp extends BaseCommand {

        @Subcommand("bytag")
        public class ByTag extends BaseCommand {

            @Subcommand("set")
            @CommandCompletion("[tag]|@loottable_by_tag [xp]|@range:50")
            public void byTag(CommandSender sender, @Single String tag,
                @com.voltskiya.lib.acf.annotation.Optional() Integer xp) {
                if (xp == null)
                    LootXpTableManager.remove(tag);
                else
                    LootXpTableManager.add(tag, xp);
                green(sender, "Success!");
            }

            @Subcommand("get")
            @CommandCompletion("[tag]@loottable_by_tag")
            public void byTag(CommandSender sender, @Single String tag) {
                @Nullable Integer xp = LootXpTableManager.get(tag);
                if (xp == null)
                    aqua(sender, "Mobs with the '%s' tag will drop normal xp", tag);
                else
                    aqua(sender, "Mobs with the '%s' tag will drop %d xp", tag, xp);
            }
        }

        @Subcommand("bytype")
        public class ByType extends BaseCommand {

            @Subcommand("set")
            @CommandCompletion("@entity_type [xp]|@range:50")
            public void byType(CommandSender sender, String entityType,
                @com.voltskiya.lib.acf.annotation.Optional() Integer xp) {
                Optional<EntityType<?>> entityTypes = EntityType.byString(entityType);
                if (entityTypes.isEmpty()) {
                    red(sender, "There is no entity type '%s'", entityType);
                    return;
                }
                if (xp == null)
                    LootXpTableManager.remove(entityTypes.get());
                else
                    LootXpTableManager.add(entityTypes.get(), xp);
                green(sender, "Success!");
            }

            @Subcommand("get")
            @CommandCompletion("@entity_type [xp]|@range:1-50")
            public void byType(CommandSender sender, @Single String entityType) {
                Optional<EntityType<?>> entityTypes = EntityType.byString(entityType);
                if (entityTypes.isEmpty()) {
                    red(sender, "There is no entity type '%s'", entityType);
                    return;
                }
                Integer xp = LootXpTableManager.get(entityTypes.get());
                if (xp == null)
                    aqua(sender, "'%s' mobs will drop normal xp", entityType);
                else
                    aqua(sender, "'%s' mobs will drop %d xp", entityType, xp);
            }
        }

    }

    @Subcommand("block")
    public void block(Player player) {
        player.openInventory(new BlockTableGui(player).getInventory());
    }
}
