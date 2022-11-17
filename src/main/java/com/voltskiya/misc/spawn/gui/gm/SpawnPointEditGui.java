package com.voltskiya.misc.spawn.gui.gm;

import apple.mc.utilities.inventory.gui.acd.page.InventoryGuiPageImplACD;
import com.voltskiya.misc.spawn.PlayerSpawnDatabase;
import com.voltskiya.misc.spawn.PlayerSpawnpoints;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class SpawnPointEditGui extends InventoryGuiPageImplACD<SpawnJoinEditGuiMain> {

    private final PlayerSpawnpoints spawnPoint;
    private final int index;

    public SpawnPointEditGui(SpawnJoinEditGuiMain parent, PlayerSpawnpoints spawnPoint, int index) {
        super(parent);
        this.spawnPoint =
            spawnPoint == null ? new PlayerSpawnpoints(makeItem(Material.GLASS_BOTTLE),
                this.parent.getPlayer().getLocation()) : spawnPoint.copy();
        this.index = index;
    }

    @Override
    public void initialize() {
        setSlot(0, slotImpl(e -> {
            parentRemoveSubPage();
        }, backItem()));
        setSlot(1, slotImpl(e -> {
            PlayerSpawnDatabase.get().setSpawnPoint(index, spawnPoint);
            parentRemoveSubPage();
        }, saveItem()));
        setSlot(3, slotDoNothing(makeItem(Material.SPRUCE_SIGN, 1, "To change the block",
            List.of("Click an item in your inventory"))));
        setSlot(4, slotImpl(e -> {
            spawnPoint.setLocation(parent.getPlayer().getLocation());
        }, makeItem(Material.LIGHTNING_ROD, "Set the location")));
        setSlot(5, slotImpl(e -> {
            PlayerSpawnDatabase.get().removeSpawnPoint(index);
            parentRemoveSubPage();
        }, makeItem(Material.RED_CONCRETE, "Set slot to nothing")));
    }

    @Override
    public void refreshPageItems() {
        setSlot(8, slotDoNothing(this.spawnPoint.toItem()));
    }

    @Override
    public void onPlayerInventory(InventoryClickEvent event) {
        ItemStack currentItem = event.getCurrentItem();
        if (currentItem != null)
            spawnPoint.setItem(currentItem);
        refresh();
    }

    @Override
    public String getName() {
        return "Edit Spawnpoint";
    }

    @Override
    public int size() {
        return 9;
    }
}
