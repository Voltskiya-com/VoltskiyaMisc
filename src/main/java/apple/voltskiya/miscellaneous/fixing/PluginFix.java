package apple.voltskiya.miscellaneous.fixing;

import apple.lib.pmc.AppleModule;

public class PluginFix extends AppleModule {

    /**
     * enables the module with listeners and commands and anything else
     */
    @Override
    public void enable() {
    }

    /**
     * @return the name of the module which also specifies the datafolder
     */
    @Override
    public String getName() {
        return "fixing";
    }
}
