package com.voltskiya.misc.event;

import com.voltskiya.lib.AbstractModule;

public class PluginEventsNatural extends AbstractModule {
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
