package com.voltskiya.misc.datapack;

import com.voltskiya.lib.AbstractModule;

public class ModuleDatapack extends AbstractModule {

    @Override
    public void enable() {
        new ScoreboardListener();
        new CommandDatapackReload();
    }

    @Override
    public String getName() {
        return "DatapackIO";
    }
}
