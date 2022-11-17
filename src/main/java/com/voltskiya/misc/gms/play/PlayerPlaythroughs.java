package com.voltskiya.misc.gms.play;

import apple.utilities.database.SaveFileable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerPlaythroughs implements SaveFileable {

    private UUID uuid;
    private Map<String, Playthrough> playthroughs = new HashMap<>();

    public PlayerPlaythroughs(UUID uuid) {
        this.uuid = uuid;
    }


    @Override
    public String getSaveFileName() {
        return this.uuid.toString();
    }

    public static String getSaveFileName(UUID uuid) {
        return uuid.toString();
    }
}
