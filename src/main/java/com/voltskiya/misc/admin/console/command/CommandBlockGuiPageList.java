package com.voltskiya.misc.admin.console.command;

import apple.mc.utilities.inventory.gui.acd.page.InventoryGuiPageScrollableACD;
import apple.mc.utilities.world.vector.VectorUtils;
import com.voltskiya.misc.admin.console.command.CommandIntercepter.CommandBlockInfo;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class CommandBlockGuiPageList extends InventoryGuiPageScrollableACD<CommandBlockGui> {

    private final CommandBlockGui commandBlockGui;

    public CommandBlockGuiPageList(CommandBlockGui commandBlockGui) {
        super(commandBlockGui);
        this.commandBlockGui = commandBlockGui;
        addCmds();
    }

    private void addCmds() {
        final Player player = commandBlockGui.getPlayer();
        final List<CommandBlockInfo> cmds = CommandIntercepter.get();
        cmds.sort(Comparator.comparingDouble(
            c -> VectorUtils.distance(c.location(), player.getLocation())));
        for (CommandBlockInfo cmd : cmds) {
            final Location location = cmd.location();
            final List<String> lore = new ArrayList<>() {
                {
                    add(String.format("%d, %d, %d, %s",
                        location.getBlockX(),
                        location.getBlockY(),
                        location.getBlockZ(),
                        location.getWorld().getName()
                    ));
                    add(String.format("%d blocks away",
                        (int) VectorUtils.distance(location, player.getLocation())));
                }
            };
            String command = cmd.command();
            while (command.length() > 41) {
                lore.add(command.substring(0, 40));
                command = command.substring(40);
            }
            if (!command.isBlank())
                lore.add(command);
            add(slotImpl(e -> {
                player.teleport(location);
                player.closeInventory(InventoryCloseEvent.Reason.PLUGIN);
            }, makeItem(cmd.type(), 1, null, lore)));
        }
    }

    @Override
    public String getName() {
        return "Command blocks";
    }

    @Override
    public int size() {
        return 54;
    }
}
