package apple.voltskiya.miscellaneous.conveyer_belt;

import apple.lib.pmc.PluginModule;

public class PluginConveyorBelt extends PluginModule {
    private static PluginConveyorBelt instance;

    public static PluginConveyorBelt get() {
        return instance;
    }

    @Override
    public void enable() {
        instance = this;
        new ConveyorCommand();
        new ConveyorRunning();
        new ConveyorWandListener();
    }

    @Override
    public String getName() {
        return "conveyor_belt";
    }
}
