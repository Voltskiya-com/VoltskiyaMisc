package apple.voltskiya.miscellaneous.conveyer_belt;

import com.voltskiya.lib.AbstractModule;

public class PluginConveyorBelt extends AbstractModule {

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
    public boolean shouldEnable() {
        return false;
    }

    @Override
    public String getName() {
        return "conveyor_belt";
    }
}
