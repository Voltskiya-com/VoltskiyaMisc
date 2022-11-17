package com.voltskiya.misc.fix;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class FixingAttribute {

    public static void fixPlayerAttributes(Player player) {
        for (Attribute attribute : Attribute.values()) {
            @Nullable AttributeInstance playerAttribute = player.getAttribute(attribute);
            if (playerAttribute != null) {
                for (AttributeModifier modifier : playerAttribute.getModifiers()) {
                    playerAttribute.removeModifier(modifier);
                }
            }
        }
    }
}
