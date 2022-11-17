package com.voltskiya.misc.spawn;

public class PlayerSpawnConfig {
    private static PlayerSpawnConfig instance;
    public int spawnHeight = 50;
    public int invulnerableTicks = 20 * 5;
    public int simmerTitle = 20;

    public PlayerSpawnConfig() {
        instance = this;
    }

    public static PlayerSpawnConfig get() {
        return instance;
    }

    public int getSpawnHeight() {
        return spawnHeight;
    }

    public int getInvulnerability() {
        return invulnerableTicks;
    }

    public int getSimmer() {
        return simmerTitle;
    }
}
