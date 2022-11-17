package com.voltskiya.misc.tool;

import com.voltskiya.lib.AbstractModule;
import apple.mc.utilities.PluginModuleMcUtil;
import apple.mc.utilities.player.wand.WandType;
import com.voltskiya.misc.tool.snow.SnowToolCommand;
import com.voltskiya.misc.tool.snow.SnowToolWand;

public class ModulePowerTool extends AbstractModule implements PluginModuleMcUtil {
    public static WandType<SnowToolWand> SNOW_WAND;

    @Override
    public void enable() {
        SNOW_WAND = createWand(SnowToolWand::new, "snow_wand");
        new SnowToolCommand();
    }

    @Override
    public String getName() {
        return "Powertool";
    }
}
