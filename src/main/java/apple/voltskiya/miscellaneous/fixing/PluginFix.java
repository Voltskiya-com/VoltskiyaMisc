package apple.voltskiya.miscellaneous.fixing;

import apple.lib.pmc.PluginModule;

public class PluginFix extends PluginModule {
    /**
     * enables the module with listeners and commands and anything else
     */
    @Override
    public void enable() {
        new CommandFix();
    }

    /**
     * @return the name of the module which also specifies the datafolder
     */
    @Override
    public String getName() {
        return "fixing";
    }
}
