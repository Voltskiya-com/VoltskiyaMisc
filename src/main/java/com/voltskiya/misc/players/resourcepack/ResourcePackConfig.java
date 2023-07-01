package com.voltskiya.misc.players.resourcepack;

import apple.utilities.database.ajd.AppleAJD;
import apple.utilities.database.ajd.AppleAJDInst;
import com.voltskiya.lib.pmc.FileIOServiceNow;
import com.voltskiya.misc.VoltskiyaPlugin;
import java.io.File;

public class ResourcePackConfig {

    private static ResourcePackConfig instance;
    public String url = "https://github.com/Voltskiya-com/VoltskiyaResourcePack/raw/master/VoltskiyaResourcePackLinked.zip";

    public static ResourcePackConfig get() {
        return instance;
    }

    public static void load() {
        File file = new File(VoltskiyaPlugin.get().getDataFolder(), "ResourcePack.json");
        AppleAJDInst<ResourcePackConfig> manager = AppleAJD.createInst(ResourcePackConfig.class, file, FileIOServiceNow.taskCreator());
        instance = manager.loadOrMake();
    }
}
