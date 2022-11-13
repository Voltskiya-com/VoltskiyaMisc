package apple.voltskiya.miscellaneous.gms.warp;

import org.bukkit.Location;

public class WarpEntry {

    private Location location;
    private String name;

    public WarpEntry(Location location, String name) {
        this.location = location;
        this.name = name;
    }

    public WarpEntry() {
    }

    public Location getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public boolean startsWith(String filter) {
        return WarpDatabase.normalizeWarpName(this.getName())
            .startsWith(WarpDatabase.normalizeWarpName(filter));
    }
}
