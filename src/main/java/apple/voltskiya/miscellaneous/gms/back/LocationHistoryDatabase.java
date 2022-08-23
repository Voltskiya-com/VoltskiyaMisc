package apple.voltskiya.miscellaneous.gms.back;

import apple.utilities.database.ajd.AppleAJD;
import apple.utilities.database.ajd.AppleAJDInst;
import apple.utilities.threading.service.queue.AsyncTaskQueue;
import apple.utilities.util.ObjectUtilsFormatting;
import apple.voltskiya.miscellaneous.gms.PluginCommands;
import apple.voltskiya.miscellaneous.io.FileIOService;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class LocationHistoryDatabase {
    public static AppleAJDInst<LocationHistoryDatabase, AsyncTaskQueue> manager = AppleAJD.createInst(
            LocationHistoryDatabase.class,
            PluginCommands.get().getFile("LocationHistory.json"),
            FileIOService.get().taskCreator()
    );

    public static void initialize() {
        manager.loadOrMake();
    }


    public static LocationHistoryDatabase getInstance() {
        return manager.getInstance();
    }

    private final Map<UUID, PlayerLocationHistory> locationHistory = new HashMap<>();

    public void createBack(UUID uuid, Location oldLocation) {
        locationHistory.computeIfAbsent(uuid, (k) -> new PlayerLocationHistory()).createBack(oldLocation);
        manager.save();
    }

    @Nullable
    public Location back(UUID uuid, @NotNull Location newerLoc) {
        Location location = ObjectUtilsFormatting.defaultIfNull(null,
                                                                locationHistory.get(uuid),
                                                                pl -> pl.back(newerLoc)
        );
        if (location != null) manager.save();
        return location;
    }

    public PlayerLocationEntry createCheckpoint(UUID uuid, Location location, String name) {
        PlayerLocationEntry checkpoint = locationHistory
                .computeIfAbsent(uuid, (k) -> new PlayerLocationHistory())
                .createCheckpoint(location, name);
        manager.save();
        return checkpoint;
    }

    public PlayerLocationEntry removeCheckpoint(UUID uuid, String name) {
        PlayerLocationEntry checkpoint = locationHistory
                .computeIfAbsent(uuid, (k) -> new PlayerLocationHistory())
                .removeCheckpoint(name);
        if (checkpoint != null) manager.save();
        return checkpoint;
    }

    @Nullable
    public Location forward(UUID uuid) {
        Location location = ObjectUtilsFormatting.defaultIfNull(null,
                                                                locationHistory.get(uuid),
                                                                PlayerLocationHistory::forward
        );
        if (location != null) manager.save();
        return location;
    }

    @Nullable
    public Location checkpoint(UUID uuid, String name) {
        return ObjectUtilsFormatting.defaultIfNull(null, locationHistory.get(uuid), lh -> lh.checkpoint(name));
    }

    public List<String> checkpointList(UUID uuid) {
        return ObjectUtilsFormatting.defaultIfNull(null,
                                                   locationHistory.get(uuid),
                                                   PlayerLocationHistory::checkpointList
        );
    }

    public boolean clearOne(UUID uuid) {
        boolean cleared = ObjectUtilsFormatting.defaultIfNull(false,
                                                              locationHistory.get(uuid),
                                                              PlayerLocationHistory::clearHistoryOne
        );
        if (cleared) manager.save();
        return cleared;
    }

    public boolean clearHistory(UUID uuid) {
        return ObjectUtilsFormatting.defaultIfNull(false,
                                                   locationHistory.get(uuid),
                                                   PlayerLocationHistory::clearHistory
        );
    }
}
