import processing.core.PImage;

import java.nio.file.Path;
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

    public static Point newPosi = new Point(4, 3);



    public DudeEntity(String id,
                      Point position,
                      int actionPeriod,
                      int animationPeriod,
                      int resourceLimit,
                      List<PImage> images){
        super(id,position,actionPeriod,animationPeriod,images);
        setPosition(newPosi);

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
        PathingStrategy strategy = new AStarPathingStrategy();
        List<Point> pointList = strategy.computePath(this.getPosition(), destPos, (p1) -> world.withinBounds(p1) && !world.isOccupied(p1), Point::adjacent, PathingStrategy.CARDINAL_NEIGHBORS);

//        if(pointList.size() == 0){
//            return getPosition();
//        }
//
//        return pointList.get(0);
        Point nextPos = newPosi;
        if (!this.getPosition().equals(nextPos)){
            world.moveEntity(this,nextPos);
        }

        return nextPos;
    }

    public int getResourceLimit() {
        return resourceLimit;
    }

    public void setResourceLimit(int resourceLimit) {
        this.resourceLimit = resourceLimit;
    }
}
