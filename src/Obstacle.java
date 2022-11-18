import processing.core.PImage;

import java.util.List;

public class Obstacle extends AnimatedEntity{

    private static final int OBSTACLE_NUM_PROPERTIES = 5;
    private static final int OBSTACLE_ID = 1;
    private static final int OBSTACLE_COL = 2;
    private static final int OBSTACLE_ROW = 3;
    private static final int OBSTACLE_ANIMATION_PERIOD = 4;

    public Obstacle(String id, Point position, int animationPeriod, List<PImage> images)
    {
        super(id,position,animationPeriod,images);
    }

    public static boolean parseObstacle(
            String[] properties, WorldModel world, ImageStore imageStore) {
        if (properties.length == getObstacleNumProperties()) {
            Point pt = new Point(Integer.parseInt(properties[getObstacleCol()]),
                    Integer.parseInt(properties[getObstacleRow()]));
            Entity entity = new Obstacle(properties[getObstacleId()], pt,
                    Integer.parseInt(properties[getObstacleAnimationPeriod()]),
                    imageStore.getImageList(
                            imageStore.getObstacleKey()));
            world.tryAddEntity( entity);
        }

        return properties.length == getObstacleNumProperties();
    }



    public static int getObstacleNumProperties() {
        return OBSTACLE_NUM_PROPERTIES;
    }

    public static int getObstacleId() {
        return OBSTACLE_ID;
    }

    public static int getObstacleCol() {
        return OBSTACLE_COL;
    }

    public static int getObstacleRow() {
        return OBSTACLE_ROW;
    }

    public static int getObstacleAnimationPeriod() {
        return OBSTACLE_ANIMATION_PERIOD;
    }
}
