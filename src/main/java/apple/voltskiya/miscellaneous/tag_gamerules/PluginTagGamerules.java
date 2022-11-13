package apple.voltskiya.miscellaneous.tag_gamerules;

import apple.lib.pmc.AppleModule;

public class PluginTagGamerules extends AppleModule {
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
