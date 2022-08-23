package apple.voltskiya.miscellaneous.wanted;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.BiFunction;

public abstract class Village {
    private final String factionName;
    private final String villageType;
    private final HashMap<UUID, VillageReputation> reputation = new HashMap<>();

    public Village(String factionName, String villageType) {
        this.factionName = factionName;
        this.villageType = villageType;
    }

    @Nullable
    public static Village create(String factionName, String type) {
        VillageType villageType = VillageType.valueOfName(type);
        if (villageType == null) {
            return null;
        }
        return villageType.construct(factionName, type);
    }

    public static String getTypeId() {
        return "villageType";
    }

    public String getFactionName() {
        return factionName;
    }

    private static class VillageReputation {
        private final int reputation;

        private VillageReputation(int reputation) {
            this.reputation = reputation;
        }

        private VillageReputation() {
            this.reputation = 0;
        }
    }

    public enum VillageType {
        EXAMINER("examiner", ExaminerVillage.class, ExaminerVillage::new),
        SWAMP("swamp", SwampVillage.class, SwampVillage::new);

        private final String name;
        private final Class<? extends Village> type;
        private final BiFunction<String, String, Village> constructor;
        private static List<String> names = null;

        VillageType(String name, Class<? extends Village> type, BiFunction<String, String, Village> constructor) {
            this.name = name;
            this.type = type;
            this.constructor = constructor;
        }

        @Nullable
        public static VillageType valueOfName(String name) {
            for (VillageType type : values()) {
                if (type.name.equals(name)) return type;
            }
            return null;
        }

        public static Collection<String> names() {
            if (names == null) names = new ArrayList<>() {{
                for (VillageType type : values()) add(type.name);
            }};
            return names;

        }

        public Village construct(String factionName, String type) {
            return constructor.apply(factionName, type);
        }

        public Class<? extends Village> type() {
            return type;
        }
    }
}
