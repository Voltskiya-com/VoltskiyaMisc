package com.voltskiya.misc.fix;

import com.voltskiya.misc.VoltskiyaPlugin;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class PlayerAttributesConfig {

    private static PlayerAttributesConfig instance;
    private double knockbackResistance = 0.1;

    public PlayerAttributesConfig() {
        instance = this;
    }

    public static PlayerAttributesConfig get() {
        return instance;
    }

    public void updatePlayerAttributes(Player player) {
        System.out.println("update");
        for (Attribute attribute : Attribute.values()) {
            @Nullable AttributeInstance playerAttribute = player.getAttribute(attribute);
            if (playerAttribute == null)
                continue;
            for (AttributeModifier modifier : playerAttribute.getModifiers()) {
                playerAttribute.removeModifier(modifier);
            }
        }

        AttributeInstance attribute = player.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE);
        if (attribute == null) {
            player.registerAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE);
            attribute = player.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE);
            if (attribute == null) {
                VoltskiyaPlugin.get().getSLF4JLogger().error("GENERIC_KNOCKBACK_RESISTANCE is unregistered on " + player.getName());
                return;
            }
        }
        attribute.setBaseValue(knockbackResistance);
    }
}
