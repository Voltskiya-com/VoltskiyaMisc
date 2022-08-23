package apple.voltskiya.miscellaneous.conveyer_belt;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.*;

public class ConveyorBeltManager {
    public HashMap<String, ConveyorBelt> conveyorBelts = new HashMap<>();

    private static final Gson gson;
    private static final File conveyorsFile;
    private static ConveyorBeltManager instance;

    static {
        final GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();

        // get the conveyors from our db
        conveyorsFile = new File(PluginConveyorBelt.get().getDataFolder(), "conveyorsDB.json");
        try {
            if (conveyorsFile.exists()) {
                try (BufferedReader reader = new BufferedReader(new FileReader(conveyorsFile))) {
                    instance = gson.fromJson(reader, ConveyorBeltManager.class);
                }
            } else {
                conveyorsFile.createNewFile();
                instance = new ConveyorBeltManager();
                save();
            }
        } catch (IOException e) {
            instance = null;
            e.printStackTrace();
        }
    }

    public synchronized static void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(conveyorsFile))) {
            gson.toJson(get(), writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized static ConveyorBeltManager get() {
        return instance;
    }

    public synchronized static boolean put(ConveyorBelt conveyorBelt) {
        if (get().conveyorBelts.containsKey(conveyorBelt.uniqueName())) {
            return false;
        }
        get().conveyorBelts.put(conveyorBelt.uniqueName(), conveyorBelt);
        save();
        return true;
    }

    public synchronized static Set<String> keyset() {
        return new HashSet<>(get().conveyorBelts.keySet());
    }

    public synchronized static boolean remove(String name) {
        if (get().conveyorBelts.remove(name) != null) {
            save();
            return true;
        }
        return false;
    }

    /**
     * @return a list that you should not edit!
     */
    public static Collection<ConveyorBelt> values() {
        return get().conveyorBelts.values();
    }

    public static ConveyorBelt get(String name) {
        return get().conveyorBelts.get(name);
    }
}
