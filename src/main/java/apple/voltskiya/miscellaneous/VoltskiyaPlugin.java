package apple.voltskiya.miscellaneous;

import apple.voltskiya.miscellaneous.drops.PluginDrops;
import apple.voltskiya.miscellaneous.event.PluginEventsNatural;
import apple.voltskiya.miscellaneous.fix.PluginFix;
import apple.voltskiya.miscellaneous.gamerules.PluginTagGamerules;
import apple.voltskiya.miscellaneous.gms.PluginCommands;
import apple.voltskiya.miscellaneous.players.PluginPlayerChanges;
import apple.voltskiya.miscellaneous.spawn.PluginPlayerSpawn;
import apple.voltskiya.miscellaneous.tool.PluginPowerTool;
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

