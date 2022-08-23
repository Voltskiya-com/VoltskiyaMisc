package apple.voltskiya.miscellaneous.bottom_top;

import apple.lib.pmc.PluginModule;

public class PluginBottomTop extends PluginModule {
    @Override
    public void enable() {
        new BottomTopDeathListener();
    }

    @Override
    public String getName() {
        return "bottom_top";
    }
}
