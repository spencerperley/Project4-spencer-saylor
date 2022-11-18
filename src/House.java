import processing.core.PImage;

import java.util.List;

public class House extends Entity{


    private static final int HOUSE_NUM_PROPERTIES = 4;
    private static final int HOUSE_ID = 1;
    private static final int HOUSE_COL = 2;
    private static final int HOUSE_ROW = 3;


    public House(
            String id, Point position, List<PImage> images)
    {
        super(id,position,images);

    }

    public static boolean parseHouse(
            String[] properties, WorldModel world, ImageStore imageStore) {
        if (properties.length == getHouseNumProperties()) {
            Point pt = new Point(Integer.parseInt(properties[getHouseCol()]),
                    Integer.parseInt(properties[getHouseRow()]));
            House entity = new House(properties[getHouseId()], pt,
                    imageStore.getImageList(
                            ImageStore.getHouseKey()));
            world.tryAddEntity( entity);
        }

        return properties.length == getHouseNumProperties();
    }

    public static int getHouseNumProperties() {
        return HOUSE_NUM_PROPERTIES;
    }

    public static int getHouseId() {
        return HOUSE_ID;
    }

    public static int getHouseCol() {
        return HOUSE_COL;
    }

    public static int getHouseRow() {
        return HOUSE_ROW;
    }
}
