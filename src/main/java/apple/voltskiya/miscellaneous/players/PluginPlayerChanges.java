package apple.voltskiya.miscellaneous.players;

import apple.lib.pmc.PluginModule;

public class PluginPlayerChanges extends PluginModule {
    @Override
    public void enable() {
        new PlayerBlockingLoginListener();
        new PlayerInfinitePotions();
        new PlayerMilk();
    }

    @Override
    public String getName() {
        return "player_changes";
    }
}
