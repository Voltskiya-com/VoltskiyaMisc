package com.voltskiya.misc.gms.warp;

import com.voltskiya.lib.pmc.FileIOServiceNow;
import apple.utilities.database.ajd.AppleAJD;
import apple.utilities.database.ajd.AppleAJDTyped;
import apple.utilities.threading.service.queue.AsyncTaskQueue;
import apple.utilities.util.ObjectUtilsFormatting;
import com.voltskiya.misc.gms.CommandsModule;
import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LocationHistoryDatabase {

    public static AppleAJDTyped<PlayerLocationHistory, AsyncTaskQueue> manager;
    private static final Map<UUID, PlayerLocationHistory> locationHistory = new HashMap<>();

    public static void initialize() {
        File file = CommandsModule.get().getFile("LocationHistory");
        manager = AppleAJD.createTyped(PlayerLocationHistory.class, file,
            FileIOServiceNow.get().taskCreator());
        Collection<PlayerLocationHistory> folder = manager.loadFolderNow();
        for (PlayerLocationHistory player : folder) {
            locationHistory.put(player.getUUID(), player);
        }
    }


    public static void createBack(UUID uuid, Location oldLocation) {
        PlayerLocationHistory history = getPlayer(uuid);
        history.createBack(oldLocation);
        manager.saveInFolder(history);
    }

    @Nullable
    public static Location back(UUID uuid, @NotNull Location newerLoc) {
        PlayerLocationHistory history = getPlayer(uuid);
        Location back = history.back(newerLoc);
        manager.saveInFolder(history);
        return back;
    }

    @NotNull
    private static PlayerLocationHistory getPlayer(UUID uuid) {
        return locationHistory.computeIfAbsent(uuid, PlayerLocationHistory::new);
    }

    public static PlayerLocationEntry createCheckpoint(UUID uuid, Location location, String name) {
        PlayerLocationHistory history = getPlayer(uuid);
        PlayerLocationEntry back = history.createCheckpoint(location, name);
        manager.saveInFolder(history);
        return back;
    }

    public static PlayerLocationEntry removeCheckpoint(UUID uuid, String name) {
        PlayerLocationHistory history = getPlayer(uuid);
        PlayerLocationEntry back = history.removeCheckpoint(name);
        manager.saveInFolder(history);
        return back;
    }

    @Nullable
    public static Location forward(UUID uuid) {
        PlayerLocationHistory history = getPlayer(uuid);
        Location back = history.forward();
        manager.saveInFolder(history);
        return back;
    }

    @Nullable
    public static Location checkpoint(UUID uuid, String name) {
        return ObjectUtilsFormatting.defaultIfNull(null, locationHistory.get(uuid),
            lh -> lh.checkpoint(name));
    }

    public static List<String> checkpointList(UUID uuid) {
        return ObjectUtilsFormatting.defaultIfNull(null, locationHistory.get(uuid),
            PlayerLocationHistory::checkpointList);
    }

    public static boolean clearOne(UUID uuid) {
        PlayerLocationHistory history = getPlayer(uuid);
        boolean back = history.clearHistoryOne();
        manager.saveInFolder(history);
        return back;
    }

    public static boolean clearHistory(UUID uuid) {
        PlayerLocationHistory history = getPlayer(uuid);
        boolean back = history.clearHistory();
        manager.saveInFolder(history);
        return back;
    }

}
