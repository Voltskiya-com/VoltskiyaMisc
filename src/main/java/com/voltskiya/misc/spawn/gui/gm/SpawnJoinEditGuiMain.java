package com.voltskiya.misc.spawn.gui.gm;

import apple.mc.utilities.inventory.gui.acd.page.InventoryGuiPageImplACD;
import com.voltskiya.misc.spawn.PlayerSpawnDatabase;
import com.voltskiya.misc.spawn.PlayerSpawnpoints;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public class SpawnJoinEditGuiMain extends InventoryGuiPageImplACD<SpawnJoinEditGui> {

    public SpawnJoinEditGuiMain(SpawnJoinEditGui parent) {
        super(parent);
    }

    @Override
    public void refreshPageItems() {
        ItemStack emptyItem = makeItem(Material.GLASS, "Nothing");
        int size = size();
        for (int i = 9; i < size; i++) {
            @Nullable PlayerSpawnpoints spawnPoint = PlayerSpawnDatabase.get().getSpawnPoint(i);
            ItemStack itemStack = spawnPoint == null ? emptyItem : spawnPoint.toItem();
            int finalI = i;
            setSlot(slotImpl(e -> {
                parentAddSubPage(new SpawnPointEditGui(this, spawnPoint, finalI));
            }, itemStack), i);
        }
    }

    @Override
    public String getName() {
        return "Edit Join GUI";
    }

    @Override
    public int size() {
        return 54;
    }

    public Player getPlayer() {
        return parent.getPlayer();
    }
}
