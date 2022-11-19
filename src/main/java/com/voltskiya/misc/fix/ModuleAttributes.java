package com.voltskiya.misc.fix;

import com.voltskiya.lib.AbstractModule;
import com.voltskiya.lib.configs.factory.AppleConfigLike;
import java.util.List;

public class ModuleAttributes extends AbstractModule {

    @Override
    public void enable() {
        new PlayerAttributeListener();
    }

    @Override
    public List<AppleConfigLike> getConfigs() {
        return List.of(configJson(PlayerAttributesConfig.class, "PlayerAttributes"));
    }

    @Override
    public String getName() {
        return "Attributes";
    }
}
