package apple.voltskiya.miscellaneous.conveyer_belt;

import java.util.UUID;

public class Box3d {
    public final UUID world;
    public double xLower;
    public double yLower;
    public double zLower;
    public double xUpper;
    public double yUpper;
    public double zUpper;

    public Box3d(UUID world, double xLower, double yLower, double zLower, double xUpper, double yUpper, double zUpper) {
        this.world = world;
        this.xLower = xLower;
        this.yLower = yLower;
        this.zLower = zLower;
        this.xUpper = xUpper;
        this.yUpper = yUpper;
        this.zUpper = zUpper;
    }
}
