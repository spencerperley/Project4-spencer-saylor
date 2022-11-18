import processing.core.PImage;

import java.util.List;

public class Tree extends TreeEntity{



    private static final int TREE_NUM_PROPERTIES = 7;
    private static final int TREE_ID = 1;
    private static final int TREE_COL = 2;
    private static final int TREE_ROW = 3;
    private static final int TREE_ANIMATION_PERIOD = 4;
    private static final int TREE_ACTION_PERIOD = 5;
    private static final int TREE_HEALTH = 6;

    public Tree(  String id,
                  Point position,
                  int actionPeriod,
                  int animationPeriod,
                  int health,
                  List<PImage> images){
        super(id,position,actionPeriod,animationPeriod,health,images);
    }

    public static boolean parseTree(
            String[] properties, WorldModel world, ImageStore imageStore) {
        if (properties.length == getTreeNumProperties()) {
            Point pt = new Point(Integer.parseInt(properties[getTreeCol()]),
                    Integer.parseInt(properties[getTreeRow()]));
            Entity entity = new Tree(properties[getTreeId()],
                    pt,
                    Integer.parseInt(properties[getTreeActionPeriod()]),
                    Integer.parseInt(properties[getTreeAnimationPeriod()]),
                    Integer.parseInt(properties[getTreeHealth()]),
                    imageStore.getImageList(ImageStore.getTreeKey()));
            world.tryAddEntity( entity);
        }

        return properties.length == getTreeNumProperties();
    }

    public static int getTreeNumProperties() {
        return TREE_NUM_PROPERTIES;
    }

    public static int getTreeId() {
        return TREE_ID;
    }

    public static int getTreeCol() {
        return TREE_COL;
    }

    public static int getTreeRow() {
        return TREE_ROW;
    }

    public static int getTreeAnimationPeriod() {
        return TREE_ANIMATION_PERIOD;
    }

    public static int getTreeActionPeriod() {
        return TREE_ACTION_PERIOD;
    }

    public static int getTreeHealth() {
        return TREE_HEALTH;
    }

    public boolean transformPlant(
            WorldModel world,
            EventScheduler scheduler,
            ImageStore imageStore) {
        if (this.getHealth() <= 0) {
            Entity stump = new Stump(this.getId(),
                    this.getPosition(),
                    imageStore.getImageList(ImageStore.getStumpKey()));

            world.removeEntity( this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity( stump);

            return true;
        }

        return false;
    }


    public void executeEntityActivity(
            EventScheduler scheduler,
            WorldModel world,
            ImageStore imageStore)
    {

        if (!this.transformPlant(world, scheduler, imageStore)) {

            scheduler.scheduleEvent(this,
                    new Activity(this, world, imageStore),
                    this.getActionPeriod());
        }
    }


}
