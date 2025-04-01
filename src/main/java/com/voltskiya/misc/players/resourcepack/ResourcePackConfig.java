package com.voltskiya.misc.players.resourcepack;

import apple.utilities.database.concurrent.ConcurrentAJD;
import apple.utilities.database.concurrent.inst.ConcurrentAJDInst;
import com.voltskiya.misc.VoltskiyaPlugin;
import java.io.File;
import java.time.Duration;

public class ResourcePackConfig {

    private static final long CHECK_INTERVAL = Duration.ofMinutes(10).toMillis();
    private static ResourcePackConfig instance;
    private static ConcurrentAJDInst<ResourcePackConfig> manager;
    public String url = "https://github.com/Voltskiya-com/VoltskiyaResourcePack/raw/master/VoltskiyaResourcePackLinked.zip";
    public String hash;
    protected long lastUpdated = 0;

    public static ResourcePackConfig get() {
        return instance;
    }

    public static void load() {
        File file = new File(VoltskiyaPlugin.get().getDataFolder(), "ResourcePack.json");
        manager = ConcurrentAJD.createInst(ResourcePackConfig.class, file);
        instance = manager.loadNow();
    }

    public void update(String hash) {
        this.hash = hash;
        this.lastUpdated = System.currentTimeMillis();
        manager.save();
    }

    public boolean isRecentlyUpdated() {
        return System.currentTimeMillis() - lastUpdated < CHECK_INTERVAL;
    }

    public void reset() {
        this.lastUpdated = 0;
        manager.save();
    }
}
