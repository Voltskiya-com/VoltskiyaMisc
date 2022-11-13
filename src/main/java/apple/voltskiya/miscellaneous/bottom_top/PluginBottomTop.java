package apple.voltskiya.miscellaneous.bottom_top;

import apple.lib.pmc.AppleModule;

public class PluginBottomTop extends AppleModule {
    @Override
    public void enable() {
        new BottomTopDeathListener();
    }

    @Override
    public String getName() {
        return "bottom_top";
    }
}
