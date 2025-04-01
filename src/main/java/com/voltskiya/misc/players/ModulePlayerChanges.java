package com.voltskiya.misc.players;

import com.voltskiya.lib.AbstractModule;
import com.voltskiya.lib.configs.factory.AppleConfigLike;
import com.voltskiya.misc.VoltskiyaPlugin;
import com.voltskiya.misc.players.attributes.PlayerAttributeListener;
import com.voltskiya.misc.players.attributes.PlayerAttributesConfig;
import com.voltskiya.misc.players.crafting.RegisterCrafting;
import com.voltskiya.misc.players.glider.PlayerGlider;
import com.voltskiya.misc.players.item.PlayerInfinitePotions;
import com.voltskiya.misc.players.item.PlayerMilk;
import com.voltskiya.misc.players.resourcepack.ResourcePackConfig;
import com.voltskiya.misc.players.resourcepack.ResourcePackListener;
import com.voltskiya.misc.players.resourcepack.ResourcePackReloadCommand;
import java.util.List;
import org.bukkit.Bukkit;

public class ModulePlayerChanges extends AbstractModule {

    @Override
    public void enable() {
        ResourcePackConfig.load();
        new RegisterCrafting();
        new PlayerInfinitePotions();
        new PlayerMilk();
        new PlayerAttributeListener();
        new ResourcePackListener();
        new ResourcePackReloadCommand();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(VoltskiyaPlugin.get(), PlayerGlider::tickAll, 0, 1);
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
