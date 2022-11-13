package apple.voltskiya.miscellaneous.event;

import apple.lib.pmc.AppleModule;

public class PluginEventsNatural extends AppleModule {
    /**
     * enables the module with listeners and commands and anything else
     */
    @Override
    public void enable() {
        new CommandEvents();
    }

    /**
     * @return the name of the module which also specifies the datafolder
     */
    @Override
    public String getName() {
        return "volt_events";
    }
}
