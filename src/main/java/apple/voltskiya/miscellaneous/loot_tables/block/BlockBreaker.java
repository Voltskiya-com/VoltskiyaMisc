package apple.voltskiya.miscellaneous.loot_tables.block;

import apple.voltskiya.miscellaneous.VoltskiyaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;

public class BlockBreaker implements Listener {
    public BlockBreaker() {
        Bukkit.getPluginManager().registerEvents(this, VoltskiyaPlugin.get());
    }

    @EventHandler(ignoreCancelled = true)
    public void onBreak(BlockBreakEvent event) {
        BlockBreakTable blockBreakTable = BlockBreakTableList.getBlock(event.getBlock().getType());
        if (blockBreakTable == null) return;
        event.setDropItems(false);
        Location location = event.getBlock().getLocation();
        Bukkit.getScheduler().scheduleSyncDelayedTask(VoltskiyaPlugin.get(),
                () -> dropItems(blockBreakTable, location)
        );
    }

    private void dropItems(BlockBreakTable blockBreakTable, Location location) {
        for (ItemStack item : blockBreakTable.drop()) {
            location.getWorld().spawnEntity(location, EntityType.DROPPED_ITEM, CreatureSpawnEvent.SpawnReason.CUSTOM, e -> {
                if (e instanceof Item itemEntity) {
                    itemEntity.setItemStack(item);
                }
            });
        }
    }
}
