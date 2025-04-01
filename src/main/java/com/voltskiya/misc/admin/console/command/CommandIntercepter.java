package com.voltskiya.misc.admin.console.command;

import com.voltskiya.misc.VoltskiyaPlugin;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerCommandEvent;
import org.jetbrains.annotations.NotNull;

public class CommandIntercepter implements Listener {

    private static final Set<CommandBlockInfo> commands = new HashSet<>();

    public CommandIntercepter() {
        VoltskiyaPlugin.get().registerEvents(this);
    }

    public static List<CommandBlockInfo> get() {
        synchronized (commands) {
            return new ArrayList<>(commands);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onCommand(ServerCommandEvent event) {
        if (event.getSender() instanceof BlockCommandSender sender) {
            @NotNull Block block = sender.getBlock();
            synchronized (commands) {
                commands.add(new CommandBlockInfo(block.getLocation(), block.getType(), event.getCommand()));
            }
        }
    }

    public record CommandBlockInfo(Location location, Material type, @NotNull String command) {

        @Override
        public int hashCode() {
            return location.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof CommandBlockInfo cmd && location.equals(cmd.location);
        }
    }
}
