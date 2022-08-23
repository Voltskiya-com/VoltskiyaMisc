package apple.voltskiya.miscellaneous.spawn;

import apple.mc.utilities.inventory.item.ItemSerial;
import org.bukkit.Location;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;

public class PlayerSpawnpoints {
    private ItemSerial item;
    private Location spawnLocation;

    public PlayerSpawnpoints(PlayerSpawnpoints other) {
        this.item = other.item;
        this.spawnLocation = other.spawnLocation;
    }

    public PlayerSpawnpoints(ItemStack item, Location location) {
        this.item = new ItemSerial(item, false);
        this.spawnLocation = location;
    }

    public ItemStack toItem() {
        return item.getItem();
    }

    public void spawn(HumanEntity player) {
        new PlayerSpawnAction(spawnLocation, player);
    }

    public PlayerSpawnpoints copy() {
        return new PlayerSpawnpoints(this);
    }

    public void setLocation(Location location) {
        this.spawnLocation = location;
    }

    public void setItem(ItemStack item) {
        this.item = new ItemSerial(item, false);
    }

}
