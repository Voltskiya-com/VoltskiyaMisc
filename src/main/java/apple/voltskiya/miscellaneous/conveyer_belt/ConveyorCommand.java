package apple.voltskiya.miscellaneous.conveyer_belt;

import apple.mc.utilities.inventory.item.InventoryUtils;
import apple.voltskiya.miscellaneous.VoltskiyaPlugin;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.Nullable;

;

@CommandAlias("conveyor")
public class ConveyorCommand extends BaseCommand {
    public ConveyorCommand() {
        VoltskiyaPlugin.get().getCommandManager().registerCommand(this);
        VoltskiyaPlugin.get().getCommandManager().getCommandCompletions().registerCompletion(
                "conveyors", (completionContext) -> ConveyorBeltManager.keyset()
        );
    }

    @Subcommand("wand")
    public void wand(Player player) {
        final ItemStack item = InventoryUtils.get().makeItem(Material.POPPY, 1, "Conveyor Belt Wand", null);
        final ItemMeta itemMeta = item.getItemMeta();
        itemMeta
                .getPersistentDataContainer()
                .set(ConveyorWandListener.CONVEYOR_WAND_KEY, PersistentDataType.BYTE, (byte) 1);
        item.setItemMeta(itemMeta);
        player.getInventory().addItem(item);
    }

    @Subcommand("create")
    public class Create extends BaseCommand {
        @Default
        @CommandCompletion("name blockPerSecond")
        public void create(Player player, String name, double speed) {
            @Nullable ConveyorWandListener.ConveyorWand wand = ConveyorWandListener.get(player.getUniqueId());
            if (wand != null && wand.isComplete()) {
                if (!ConveyorBeltManager.put(new ConveyorBelt(name,
                                                              wand.getCoords(),
                                                              speed,
                                                              player.getLocation().getDirection()
                ))) {
                    player.sendMessage(ChatColor.RED + "That name has already been used");
                } else {
                    player.sendMessage(ChatColor.GREEN + "Successful conveyor creation");
                }
            } else {
                player.sendMessage(ChatColor.RED + "Please finish right clicking and left clicking with a wand to build a new belt");
            }
        }
    }

    @Subcommand("remove")
    @CommandCompletion("@conveyors")
    public void remove(CommandSender sender, String name) {
        if (ConveyorBeltManager.remove(name)) {
            sender.sendMessage(ChatColor.GREEN + "removed " + name);
        } else {
            sender.sendMessage(ChatColor.RED + "Could not find a conveyor belt with that name");
        }
    }

    @Subcommand("add")
    @CommandCompletion("@conveyors")
    public void off(Player player, String name) {
        @Nullable ConveyorWandListener.ConveyorWand wand = ConveyorWandListener.get(player.getUniqueId());
        if (wand != null && wand.isComplete()) {
            final ConveyorBelt conveyorBelt = ConveyorBeltManager.get(name);
            if (conveyorBelt != null) {
                conveyorBelt.add(wand.getCoords());
            }
        }
    }

    @Subcommand("power")
    public class Power extends BaseCommand {
        @Subcommand("on")
        @CommandCompletion("@conveyors")
        public void on(String name) {
            final ConveyorBelt conveyorBelt = ConveyorBeltManager.get(name);
            if (conveyorBelt != null)
                conveyorBelt.setOn(true);
        }

        @Subcommand("off")
        @CommandCompletion("@conveyors")
        public void off(String name) {
            final ConveyorBelt conveyorBelt = ConveyorBeltManager.get(name);
            if (conveyorBelt != null)
                conveyorBelt.setOn(false);
        }

        @Subcommand("list")
        public void list(CommandSender sender) {
            for (ConveyorBelt belt : ConveyorBeltManager.values()) {
                if (belt.isOn()) {
                    sender.sendMessage(ChatColor.GREEN + belt.uniqueName() + " is on");
                } else {
                    sender.sendMessage(ChatColor.RED + belt.uniqueName() + " is off");
                }
            }
        }
    }
}
