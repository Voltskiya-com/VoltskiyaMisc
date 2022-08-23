package apple.voltskiya.miscellaneous.tag_gamerules;

import apple.lib.pmc.PluginModule;

public class PluginTagGamerules extends PluginModule {
    @Override
    public void enable() {
        new NoEntityDamageListener();
        new NoDamageCausedListener();
    }

    @Override
    public String getName() {
        return "tag_gamerules";
    }
}
