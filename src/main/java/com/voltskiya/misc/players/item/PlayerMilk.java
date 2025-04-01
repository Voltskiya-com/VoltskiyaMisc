package com.voltskiya.misc.players.item;

import com.voltskiya.misc.VoltskiyaPlugin;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Goat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;

public class PlayerMilk implements Listener {

    public static final NamespacedKey MILK_NAMESPACED_KEY = new NamespacedKey(VoltskiyaPlugin.get(),
        "milk");
    private final Map<UUID, Integer> lastMilked = new HashMap<>();

    public PlayerMilk() {
        VoltskiyaPlugin.get().registerEvents(this);
    }

    @EventHandler(ignoreCancelled = true)
    public void milk(PlayerInteractAtEntityEvent event) {
        final Player player = event.getPlayer();
        final UUID uuid = player.getUniqueId();
        final Integer lastMilked = this.lastMilked.get(uuid);
        if (lastMilked == null || lastMilked + 7 < Bukkit.getCurrentTick()) {
            @NotNull Entity clicked = event.getRightClicked();
            if (clicked instanceof Cow || clicked instanceof Goat) {
                @NotNull ItemStack bottle = player.getInventory().getItemInMainHand();
                if (bottle.getType() == Material.GLASS_BOTTLE) {
                    bottle.setAmount(bottle.getAmount() - 1);
                    final ItemStack milk = new ItemStack(Material.POTION);
                    ItemMeta itemMeta = milk.getItemMeta();
                    @NotNull PersistentDataContainer dataContainer = itemMeta.getPersistentDataContainer();
                    dataContainer.set(MILK_NAMESPACED_KEY, PersistentDataType.INTEGER, 1);
                    if (itemMeta instanceof PotionMeta potionMeta) {
                        potionMeta.setColor(Color.WHITE);
                        potionMeta.displayName(Component.text("Milk Bottle"));
                        milk.setItemMeta(potionMeta);
                    } else {
                        milk.setItemMeta(itemMeta);
                    }
                    this.lastMilked.put(uuid, Bukkit.getCurrentTick());
                    player.getInventory().addItem(milk);
                    player.playSound(player.getLocation(), Sound.ENTITY_COW_MILK, 1, 1);
                }
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void drinkMilk(PlayerItemConsumeEvent event) {
        @NotNull ItemStack item = event.getItem();
        ItemMeta itemMeta = item.getItemMeta();
        @NotNull PersistentDataContainer dataContainer = itemMeta.getPersistentDataContainer();
        if (dataContainer.has(MILK_NAMESPACED_KEY, PersistentDataType.INTEGER)) {
            final Player player = event.getPlayer();
            for (PotionEffect potionEffect : player.getActivePotionEffects()) {
                player.removePotionEffect(potionEffect.getType());
            }
        }
    }
}
