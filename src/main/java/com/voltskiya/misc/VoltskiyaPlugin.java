package com.voltskiya.misc;

import com.voltskiya.misc.drops.PluginDrops;
import com.voltskiya.misc.event.PluginEventsNatural;
import com.voltskiya.misc.fix.PluginFix;
import com.voltskiya.misc.gamerules.PluginTagGamerules;
import com.voltskiya.misc.gms.PluginCommands;
import com.voltskiya.misc.players.PluginPlayerChanges;
import com.voltskiya.misc.spawn.PluginPlayerSpawn;
import com.voltskiya.misc.tool.PluginPowerTool;
import com.voltskiya.lib.AbstractModule;
import com.voltskiya.lib.AbstractVoltPlugin;
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
        return List.of(new PluginDrops(), new PluginCommands(), new PluginTagGamerules(),
            new PluginEventsNatural(), new PluginFix(), new PluginPlayerSpawn(),
            new PluginPlayerChanges(), new PluginPowerTool(), new Snowball());
    }
}

