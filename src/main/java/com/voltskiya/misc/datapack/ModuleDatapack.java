package com.voltskiya.misc.datapack;

import com.voltskiya.lib.AbstractModule;
import com.voltskiya.misc.datapack.damage.ScoreboardListener;
import com.voltskiya.misc.datapack.reload.CommandDatapackReload;

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
