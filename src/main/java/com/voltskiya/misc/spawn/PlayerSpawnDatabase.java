package com.voltskiya.misc.spawn;

import apple.mc.utilities.data.serialize.GsonSerializeMC;
import apple.utilities.database.ajd.AppleAJD;
import apple.utilities.database.ajd.AppleAJDInst;
import apple.utilities.threading.service.base.handler.TaskHandler;
import apple.utilities.threading.service.queue.AsyncTaskQueue;
import apple.utilities.threading.service.queue.TaskHandlerQueue;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Location;
import org.jetbrains.annotations.Nullable;

public class PlayerSpawnDatabase {

    private static final TaskHandler<AsyncTaskQueue> ioService = new TaskHandlerQueue(10, 10, 10);
    private static AppleAJDInst<PlayerSpawnDatabase> manager;

    public Location respawnBox;
    public UUID mainWorld;

    private final Map<Integer, PlayerSpawnpoints> spawnpoints = new HashMap<>();


    public static PlayerSpawnDatabase get() {
        return manager.getInstance();
    }

    public static void load() {
        File file = ModulePlayerSpawn.get().getFile("PlayerSpawnDatabase.json");
        manager = AppleAJD.createInst(PlayerSpawnDatabase.class, file, ioService.taskCreator());
        manager.setSerializingJson(GsonSerializeMC.completeGsonBuilderMC().create());
        manager.loadOrMake();
    }

    public Map<Integer, PlayerSpawnpoints> getSpawnPoints() {
        return new HashMap<>(spawnpoints);
    }

    @Nullable
    public PlayerSpawnpoints getSpawnPoint(int index) {
        return spawnpoints.get(index);
    }

    public void setSpawnPoint(int index, PlayerSpawnpoints spawnPoint) {
        this.spawnpoints.put(index, spawnPoint);
        save();
    }

    public void save() {
        manager.save();
    }

    public void removeSpawnPoint(int index) {
        this.spawnpoints.remove(index);
        save();
    }

    public void setMainWorld(UUID mainWorld) {
        this.mainWorld = mainWorld;
        save();
    }

    public void setBoxSpawn(Location location) {
        this.respawnBox = location;
        save();
    }
}
