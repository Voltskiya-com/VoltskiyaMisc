package apple.voltskiya.miscellaneous.gms.warp;

import apple.mc.utilities.data.serialize.GsonSerializeLocation.Options;
import apple.mc.utilities.data.serialize.GsonSerializeMC;
import apple.utilities.database.ajd.AppleAJD;
import apple.utilities.database.ajd.AppleAJDInst;
import apple.utilities.threading.service.queue.AsyncTaskQueue;
import apple.voltskiya.miscellaneous.gms.PluginCommands;
import apple.voltskiya.miscellaneous.io.FileIOService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import org.bukkit.Location;
import org.jetbrains.annotations.Nullable;

public class WarpDatabase {

    private static AppleAJDInst<WarpDatabase, AsyncTaskQueue> manager;
    private final HashMap<String, WarpEntry> warps = new HashMap<>();

    public static void initialize() {
        File file = PluginCommands.get().getFile("WarpDatabase.json");
        manager = AppleAJD.createInst(WarpDatabase.class, file, FileIOService.taskCreator());
        Gson gson = GsonSerializeMC.registerLocationTypeAdapter(new GsonBuilder(),
            new Options(true, true, true)).create();
        manager.setSerializingJson(gson);
        manager.loadOrMake();
    }

    public static WarpDatabase get() {
        return manager.getInstance();
    }

    @Nullable
    public WarpEntry getWarp(String name) {
        return warps.get(normalizeName(name));
    }

    public void create(Location location, String name) {
        warps.put(normalizeName(name), new WarpEntry(location, name));
        manager.save();
    }

    private String normalizeName(String name) {
        return name.toLowerCase(Locale.ROOT).trim().replace(' ', '_');
    }

    public Collection<String> list() {
        return warps.values().stream().map(WarpEntry::getName).toList();
    }

    public WarpEntry remove(String name) {
        return warps.remove(normalizeName(name));
    }
}
