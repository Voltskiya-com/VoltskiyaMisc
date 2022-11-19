package com.voltskiya.misc.players;

import com.voltskiya.lib.AbstractModule;
import com.voltskiya.lib.configs.factory.AppleConfigLike;
import com.voltskiya.misc.players.attributes.PlayerAttributeListener;
import com.voltskiya.misc.players.attributes.PlayerAttributesConfig;
import com.voltskiya.misc.players.crafting.RegisterCrafting;
import com.voltskiya.misc.players.item.PlayerInfinitePotions;
import com.voltskiya.misc.players.item.PlayerMilk;
import java.util.List;

public class ModulePlayerChanges extends AbstractModule {

    @Override
    public void enable() {
        RegisterCrafting.registerCraftingRecipes();
        new PlayerInfinitePotions();
        new PlayerMilk();
        new PlayerAttributeListener();
    }

    @Override
    public List<AppleConfigLike> getConfigs() {
        return List.of(configJson(PlayerAttributesConfig.class, "PlayerAttributes"));
    }

    @Override
    public String getName() {
        return "PlayerChanges";
    }
}
