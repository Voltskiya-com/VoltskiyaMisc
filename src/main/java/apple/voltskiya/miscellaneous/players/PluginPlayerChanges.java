package apple.voltskiya.miscellaneous.players;

import apple.lib.pmc.AppleModule;

public class PluginPlayerChanges extends AppleModule {
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
