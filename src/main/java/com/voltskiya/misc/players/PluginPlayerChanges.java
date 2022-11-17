package com.voltskiya.misc.players;

import com.voltskiya.lib.AbstractModule;

public class PluginPlayerChanges extends AbstractModule {
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
