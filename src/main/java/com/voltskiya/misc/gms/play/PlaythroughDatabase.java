package com.voltskiya.misc.gms.play;

import apple.mc.utilities.data.serialize.GsonSerializeMC;
import apple.utilities.database.ajd.AppleAJD;
import apple.utilities.database.ajd.AppleAJDTyped;
import apple.utilities.threading.service.base.handler.TaskHandler;
import apple.utilities.threading.service.queue.AsyncTaskQueue;
import apple.utilities.threading.service.queue.TaskHandlerQueue;
import com.voltskiya.misc.spawn.ModulePlayerSpawn;
import java.io.File;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class PlaythroughDatabase {

    private static PlaythroughDatabase instance;
    private AppleAJDTyped<PlayerPlaythroughs> manager;
    private Map<UUID, PlayerPlaythroughs> playthroughs;

    public PlaythroughDatabase() {
        instance = this;
        File file = ModulePlayerSpawn.get().getFile("PlayerSpawnDatabase.json");
        TaskHandler<AsyncTaskQueue> ioService = new TaskHandlerQueue(10, 10, 10);
        manager = AppleAJD.createTyped(PlayerPlaythroughs.class, file, ioService.taskCreator());
        manager.setSerializingJson(GsonSerializeMC.completeGsonBuilderMC().create());
    }

    public static PlaythroughDatabase get() {
        return instance;
    }

    public CompletableFuture<PlayerPlaythroughs> getPlayerAsync(UUID player) {
        return CompletableFuture.supplyAsync(() -> this.getPlayer(player));
    }

    private PlayerPlaythroughs getPlayer(UUID player) {
        PlayerPlaythroughs playthrough = playthroughs.get(player);
        if (playthrough != null)
            return playthrough;

        playthrough = manager.loadFromFolderNow(PlayerPlaythroughs.getSaveFileName(player));
        if (playthrough == null) {
            playthrough = new PlayerPlaythroughs(player);
        }
        playthroughs.put(player, playthrough);
        return playthrough;
    }
}
