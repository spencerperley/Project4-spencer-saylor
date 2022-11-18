import java.util.List;

import processing.core.PImage;

/**
 * Represents a background for the 2D world.
 */
public final class Background extends ImageObj
{
    private static final int BGND_NUM_PROPERTIES = 4;
    private static final int BGND_ID = 1;
    private static final int BGND_COL = 2;
    private static final int BGND_ROW = 3;


    public Background(String id, List<PImage> images) {
        super(id,images);
    }

    public static boolean parseBackground(
            String[] properties, WorldModel world, ImageStore imageStore) {
        if (properties.length == getBgndNumProperties()) {
            Point pt = new Point(Integer.parseInt(properties[getBgndCol()]),
                    Integer.parseInt(properties[getBgndRow()]));
            String id = properties[getBgndId()];
            world.setBackground( pt,
                    new Background(id, imageStore.getImageList( id)));
        }

        return properties.length == getBgndNumProperties();
    }


    public static int getBgndNumProperties() {
        return BGND_NUM_PROPERTIES;
    }

    public static int getBgndId() {
        return BGND_ID;
    }

    public static int getBgndCol() {
        return BGND_COL;
    }

    public static int getBgndRow() {
        return BGND_ROW;
    }
}
