package com.voltskiya.misc;

import com.voltskiya.lib.AbstractModule;
import com.voltskiya.lib.AbstractVoltPlugin;
import com.voltskiya.misc.admin.PluginAdmin;
import com.voltskiya.misc.datapack.ModuleDatapack;
import com.voltskiya.misc.drops.DropsModule;
import com.voltskiya.misc.event.EventsNaturalModule;
import com.voltskiya.misc.gamerules.TagGamerulesModule;
import com.voltskiya.misc.gms.CommandsModule;
import com.voltskiya.misc.players.ModulePlayerChanges;
import com.voltskiya.misc.spawn.ModulePlayerSpawn;
import com.voltskiya.misc.tool.ModulePowerTool;
import java.util.Collection;
import java.util.List;

public class VoltskiyaPlugin extends AbstractVoltPlugin {

    private static VoltskiyaPlugin instance;

    public VoltskiyaPlugin() {
        instance = this;
    }

    public static VoltskiyaPlugin get() {
        return instance;
    }


    @Override
    public Collection<AbstractModule> getModules() {
        return List.of(new DropsModule(), new CommandsModule(), new TagGamerulesModule(),
            new EventsNaturalModule(), new ModulePlayerSpawn(),
            new ModulePlayerChanges(), new ModulePowerTool(), new ModuleSnowball(),
            new ModuleDatapack(), new PluginAdmin());
    }
}

