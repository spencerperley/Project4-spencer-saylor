import processing.core.PImage;

import java.util.List;

public abstract class DudeEntity extends ActivityEntity implements Moving {

    private static final int DUDE_NUM_PROPERTIES = 7;
    private static final int DUDE_ID = 1;
    private static final int DUDE_COL = 2;
    private static final int DUDE_ROW = 3;
    private static final int DUDE_LIMIT = 4;
    private static final int DUDE_ACTION_PERIOD = 5;
    private static final int DUDE_ANIMATION_PERIOD = 6;

    private int resourceLimit;


    public DudeEntity(String id,
                      Point position,
                      int actionPeriod,
                      int animationPeriod,
                      int resourceLimit,
                      List<PImage> images){
        super(id,position,actionPeriod,animationPeriod,images);
        this.setResourceLimit(resourceLimit);
    }


    public static boolean parseDude(
            String[] properties, WorldModel world, ImageStore imageStore) {
        if (properties.length == getDudeNumProperties()) {
            Point pt = new Point(Integer.parseInt(properties[getDudeCol()]),
                    Integer.parseInt(properties[getDudeRow()]));
            Entity entity = new DudeNotFull(properties[getDudeId()],
                    pt,
                    Integer.parseInt(properties[getDudeActionPeriod()]),
                    Integer.parseInt(properties[getDudeAnimationPeriod()]),
                    Integer.parseInt(properties[getDudeLimit()]),
                    imageStore.getImageList(ImageStore.getDudeKey()));
            world.tryAddEntity( entity);
        }

        return properties.length == getDudeNumProperties();
    }

    public static int getDudeNumProperties() {
        return DUDE_NUM_PROPERTIES;
    }

    public static int getDudeId() {
        return DUDE_ID;
    }

    public static int getDudeCol() {
        return DUDE_COL;
    }

    public static int getDudeRow() {
        return DUDE_ROW;
    }

    public static int getDudeLimit() {
        return DUDE_LIMIT;
    }

    public static int getDudeActionPeriod() {
        return DUDE_ACTION_PERIOD;
    }

    public static int getDudeAnimationPeriod() {
        return DUDE_ANIMATION_PERIOD;
    }

    public Point nextPosition(
            WorldModel world, Point destPos) {
        int horiz = Integer.signum(destPos.getX() - this.getPosition().getX());
        Point newPos = new Point(this.getPosition().getX() + horiz, this.getPosition().getY());

        if (horiz == 0 || world.isOccupied( newPos) && world.getOccupancyCell( newPos).getClass() != Stump.class) {
            int vert = Integer.signum(destPos.getY() - this.getPosition().getY());
            newPos = new Point(this.getPosition().getX(), this.getPosition().getY() + vert);

            if (vert == 0 || world.isOccupied( newPos) && world.getOccupancyCell( newPos).getClass() != Stump.class) {
                newPos = this.getPosition();
            }
        }

        return newPos;
    }

    public int getResourceLimit() {
        return resourceLimit;
    }

    public void setResourceLimit(int resourceLimit) {
        this.resourceLimit = resourceLimit;
    }
}
