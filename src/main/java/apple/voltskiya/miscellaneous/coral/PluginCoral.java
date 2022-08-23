package apple.voltskiya.miscellaneous.coral;

import apple.lib.pmc.PluginModule;

public class PluginCoral extends PluginModule {
    /**
     * enables the module with listeners and commands and anything else
     */
    @Override
    public void enable() {
        new CoralFadeListener();
    }

    /**
     * @return the name of the module which also specifies the datafolder
     */
    @Override
    public String getName() {
        return "coral";
    }
}
