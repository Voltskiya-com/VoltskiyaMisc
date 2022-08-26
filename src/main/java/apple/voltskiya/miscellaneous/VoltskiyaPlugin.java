package apple.voltskiya.miscellaneous;

import apple.lib.pmc.ApplePlugin;
import apple.lib.pmc.PluginModule;
import apple.voltskiya.miscellaneous.ai_changes.PluginAIChanges;
import apple.voltskiya.miscellaneous.arrows.PluginArrowTags;
import apple.voltskiya.miscellaneous.bottom_top.PluginBottomTop;
import apple.voltskiya.miscellaneous.command_output.PluginCommandFinder;
import apple.voltskiya.miscellaneous.conveyer_belt.PluginConveyorBelt;
import apple.voltskiya.miscellaneous.coral.PluginCoral;
import apple.voltskiya.miscellaneous.event.PluginEventsNatural;
import apple.voltskiya.miscellaneous.fixing.PluginFix;
import apple.voltskiya.miscellaneous.gms.PluginCommands;
import apple.voltskiya.miscellaneous.loot_tables.PluginLootTables;
import apple.voltskiya.miscellaneous.players.PluginPlayerChanges;
import apple.voltskiya.miscellaneous.spawn.PluginPlayerSpawn;
import apple.voltskiya.miscellaneous.tag_gamerules.PluginTagGamerules;
import apple.voltskiya.miscellaneous.tool.PluginPowerTool;
import java.util.Collection;
import java.util.List;

public class VoltskiyaPlugin extends ApplePlugin {

    private static VoltskiyaPlugin instance;

    public VoltskiyaPlugin() {
        instance = this;
    }

    public static VoltskiyaPlugin get() {
        return instance;
    }

    @Override
    public Collection<PluginModule> getModules() {
        return List.of(new PluginLootTables(), new PluginBottomTop(), new PluginCommands(),
            new PluginTagGamerules(), new PluginEventsNatural(), new PluginAIChanges(),
            new PluginArrowTags(), new PluginFix(), new PluginCoral(), new PluginPlayerSpawn(),
            new PluginConveyorBelt(), new PluginPlayerChanges(), new PluginCommandFinder(),
            new PluginPowerTool(), new Snowball());
    }
}

