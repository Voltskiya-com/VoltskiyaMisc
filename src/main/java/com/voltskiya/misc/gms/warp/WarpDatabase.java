package com.voltskiya.misc.gms.warp;

import com.voltskiya.lib.pmc.FileIOServiceNow;
import apple.mc.utilities.data.serialize.GsonSerializeLocation.Options;
import apple.mc.utilities.data.serialize.GsonSerializeMC;
import apple.utilities.database.ajd.AppleAJD;
import apple.utilities.database.ajd.AppleAJDInst;
import apple.utilities.threading.service.queue.AsyncTaskQueue;
import com.voltskiya.misc.gms.CommandsModule;
import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import org.bukkit.Location;
import org.jetbrains.annotations.Nullable;

public class WarpDatabase {

    private static AppleAJDInst<WarpDatabase, AsyncTaskQueue> manager;
    private final HashMap<String, WarpEntry> warps = new HashMap<>();

    public static void initialize() {
        File file = CommandsModule.get().getFile("WarpDatabase.json");
        manager = AppleAJD.createInst(WarpDatabase.class, file,
            FileIOServiceNow.get().taskCreator());
        Gson gson = GsonSerializeMC.registerLocationTypeAdapter(new GsonBuilder(),
            new Options(true, true, true)).create();
        manager.setSerializingJson(gson);
        manager.loadOrMake();
    }

    public WarpDatabase() {
    }

    public static WarpDatabase get() {
        return manager.getInstance();
    }

    public ImmutableList<WarpEntry> list() {
        return ImmutableList.copyOf(this.warps.values());
    }

    @Nullable
    public WarpEntry getWarp(String name) {
        return warps.get(normalizeWarpName(name));
    }

    public void create(Location location, String name) {
        warps.put(normalizeWarpName(name), new WarpEntry(location, name));
        manager.save();
    }

    public static String normalizeWarpName(String name) {
        return name.toLowerCase(Locale.ROOT).trim().replace(' ', '_');
    }


    public WarpEntry remove(String name) {
        return warps.remove(normalizeWarpName(name));
    }
}
