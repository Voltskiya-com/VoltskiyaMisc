package apple.voltskiya.miscellaneous.tool;

import com.voltskiya.lib.AbstractModule;
import apple.mc.utilities.PluginModuleMcUtil;
import apple.mc.utilities.player.wand.WandType;
import apple.voltskiya.miscellaneous.tool.snow.SnowToolCommand;
import apple.voltskiya.miscellaneous.tool.snow.SnowToolWand;

public class PluginPowerTool extends AbstractModule implements PluginModuleMcUtil {
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
