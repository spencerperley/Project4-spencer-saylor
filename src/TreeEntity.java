import processing.core.PImage;

import java.util.List;

public abstract class TreeEntity extends ActivityEntity {


    private static final int TREE_ANIMATION_MAX = 600;
    private static final int TREE_ANIMATION_MIN = 50;
    private static final int TREE_ACTION_MAX = 1400;
    private static final int TREE_ACTION_MIN = 1000;
    private static final int TREE_HEALTH_MAX = 3;
    private static final int TREE_HEALTH_MIN = 1;

    private int health;



    public TreeEntity(String id,
                      Point position,
                      int actionPeriod,
                      int animationPeriod,
                      int health,
                      List<PImage> images)
    {
        super(id,position,actionPeriod,animationPeriod,images);
        this.setHealth(health);
    }

    public static int getTreeAnimationMax() {
        return TREE_ANIMATION_MAX;
    }

    public static int getTreeAnimationMin() {
        return TREE_ANIMATION_MIN;
    }

    public static int getTreeActionMax() {
        return TREE_ACTION_MAX;
    }

    public static int getTreeActionMin() {
        return TREE_ACTION_MIN;
    }

    public static int getTreeHealthMax() {
        return TREE_HEALTH_MAX;
    }

    public static int getTreeHealthMin() {
        return TREE_HEALTH_MIN;
    }

    public abstract boolean transformPlant(
            WorldModel world,
            EventScheduler scheduler,
            ImageStore imageStore);


    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
