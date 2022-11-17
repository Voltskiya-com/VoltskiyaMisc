package apple.voltskiya.miscellaneous.gms.warp;

import apple.utilities.database.SaveFileable;
import apple.utilities.util.NumberUtils;
import apple.utilities.util.ObjectUtilsFormatting;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerLocationHistory implements SaveFileable {

    public static final int HISTORY_SIZE = 15;

    private List<PlayerLocationEntry> history = new ArrayList<>();
    private final Map<String, PlayerLocationEntry> checkPoints = new HashMap<>();

    private int nowIndexInHistory = 0;
    private UUID player;

    public PlayerLocationHistory(UUID player) {
        this.player = player;
    }

    public void createBack(Location prevLocation) {
        if (history.isEmpty()) {
            nowIndexInHistory = 0;
        } else {
            if (nowIndexInHistory < history.size())
                history = history.subList(0, nowIndexInHistory);
        }
        history.add(new PlayerLocationEntry(prevLocation));
        nowIndexInHistory++;
        while (history.size() > HISTORY_SIZE) {
            history.remove(0);
            nowIndexInHistory--;
        }
    }

    @Nullable
    public Location back(@NotNull Location newerLoc) {
        int size = history.size();
        if (nowIndexInHistory == size)
            history.add(new PlayerLocationEntry(newerLoc));

        return NumberUtils.between(0, nowIndexInHistory - 1, size) ? history.get(
            --nowIndexInHistory).toLocation() : null;
    }

    @Nullable
    public Location forward() {
        return NumberUtils.between(0, nowIndexInHistory + 1, history.size()) ? history.get(
            ++nowIndexInHistory).toLocation() : null;
    }

    @Nullable
    public Location checkpoint(String name) {
        return ObjectUtilsFormatting.defaultIfNull(null, checkPoints.get(name),
            PlayerLocationEntry::toLocation);
    }

    public PlayerLocationEntry createCheckpoint(Location location, String name) {
        PlayerLocationEntry entry = new PlayerLocationEntry(location);
        this.checkPoints.put(name, entry);
        return entry;
    }

    public PlayerLocationEntry removeCheckpoint(String name) {
        return this.checkPoints.remove(name);
    }

    public List<String> checkpointList() {
        return this.checkPoints.keySet().stream().sorted(String.CASE_INSENSITIVE_ORDER)
            .collect(Collectors.toList());
    }

    public boolean clearHistory() {
        this.nowIndexInHistory = 0;
        if (this.history.isEmpty()) {
            return false;
        } else {
            this.history.clear();
        }
        return true;
    }

    public boolean clearHistoryOne() {
        if (NumberUtils.between(0, nowIndexInHistory - 1, history.size())) {
            history.remove(--nowIndexInHistory);
            return true;
        }
        return false;
    }

    @Override
    public String getSaveFileName() {
        return extensionJson(this.player.toString());
    }

    public UUID getUUID() {
        return this.player;
    }
}
