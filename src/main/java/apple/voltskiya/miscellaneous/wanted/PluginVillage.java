package apple.voltskiya.miscellaneous.wanted;

import apple.lib.pmc.PluginModule;

public class PluginVillage extends PluginModule {
    private static PluginVillage instance;

    public static PluginVillage get() {
        return instance;
    }

    @Override
    public void enable() {
        instance = this;
        VillageDatabase.initialize();
        new VillageCreationListener();
        new VillageCommand();
    }

    @Override
    public String getName() {
        return "Wanted";
    }
}
