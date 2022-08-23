package apple.voltskiya.miscellaneous.wanted;

import com.google.gson.*;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class VillageDatabase {
    private static VillageDatabase instance;
    private static final Gson gson;
    private static final File villageFile;

    private final ArrayList<Village> villages = new ArrayList<>();


    static {
        final GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Village.class, new VillageAdapter());
        gson = gsonBuilder.create();
        villageFile = new File(PluginVillage.get().getDataFolder(), "village.json");
        try {
            if (villageFile.exists()) {
                try (BufferedReader reader = new BufferedReader(new FileReader(villageFile))) {
                    instance = gson.fromJson(reader, VillageDatabase.class);
                }
            } else {
                villageFile.createNewFile();
                instance = new VillageDatabase();
                save();
            }
        } catch (IOException e) {
            instance = null;
            e.printStackTrace();
        }
    }

    private static void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(villageFile))) {
            gson.toJson(get(), writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void initialize() {
    }

    public static void addVillage(Village village) {
        get().villages.add(village);
    }

    public static ArrayList<Village> getAll() {
        return new ArrayList<>(get().villages);
    }


    private static VillageDatabase get() {
        return instance;
    }

    private static class VillageAdapter implements JsonSerializer<Village>, JsonDeserializer<Village> {
        @Override
        public Village deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            String typeId = jsonObject.get(Village.getTypeId()).getAsString();
            Village.VillageType villageType = Village.VillageType.valueOfName(typeId);
            if (villageType == null) {
                throw new JsonParseException("Unknown element type: " + type + ":" + typeId);
            }
            Class<? extends Village> classOfVillage = villageType.type();
            return context.deserialize(json, classOfVillage);
        }

        @Override
        public JsonElement serialize(Village village, Type src, JsonSerializationContext context) {
            JsonObject result = context.serialize(src, src.getClass()).getAsJsonObject();
            result.add("type", new JsonPrimitive(Village.getTypeId()));
            return result;
        }
    }
}
