package com.voltskiya.misc.players.item;

import com.voltskiya.misc.VoltskiyaPlugin;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class PlayerInfinitePotions implements Listener {

    public PlayerInfinitePotions() {
        VoltskiyaPlugin.get().registerEvents(this);
    }

    @EventHandler
    public void infinite(PlayerItemConsumeEvent event) {
        @NotNull ItemStack item = event.getItem();
        ItemMeta itemMeta = item.getItemMeta();

        if (itemMeta.hasEnchant(Enchantment.INFINITY)) {
            event.setReplacement(item.clone());
        }
    }
}
