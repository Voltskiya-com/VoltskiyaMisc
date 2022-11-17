package apple.voltskiya.miscellaneous.admin.console.command;

import apple.voltskiya.miscellaneous.VoltskiyaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerCommandEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CommandIntercepter implements Listener {
    private static final Set<CommandBlockInfo> commands = new HashSet<>();

    public CommandIntercepter() {
        Bukkit.getPluginManager().registerEvents(this, VoltskiyaPlugin.get());
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

    public static List<CommandBlockInfo> get() {
        synchronized (commands) {
            return new ArrayList<>(commands);
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
