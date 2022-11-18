import processing.core.PImage;

import java.util.List;

public class Sapling extends TreeEntity {
    private static final int SAPLING_HEALTH_LIMIT = 5;
    private static final int SAPLING_ACTION_ANIMATION_PERIOD = 1000; // have to be in sync since grows and gains health at same time
    private static final int SAPLING_NUM_PROPERTIES = 4;
    private static final int SAPLING_ID = 1;
    private static final int SAPLING_COL = 2;
    private static final int SAPLING_ROW = 3;
    private static final int SAPLING_HEALTH = 4;

   private int healthLimit;

    public Sapling(String id,
                   Point position,
                   List<PImage> images){
        super(id,position, getSaplingActionAnimationPeriod(), getSaplingActionAnimationPeriod(),0,images);
    }

    public static boolean parseSapling(
            String[] properties, WorldModel world, ImageStore imageStore) {
        if (properties.length == getSaplingNumProperties()) {
            Point pt = new Point(Integer.parseInt(properties[getSaplingCol()]),
                    Integer.parseInt(properties[getSaplingRow()]));
            String id = properties[getSaplingId()];
            int health = Integer.parseInt(properties[getSaplingHealth()]);
            Entity entity = new Sapling( id, pt,imageStore.getImageList(ImageStore.getSaplingKey()));
            world.tryAddEntity(entity);
        }

        return properties.length == getSaplingNumProperties();
    }

    public static int getSaplingHealthLimit() {
        return SAPLING_HEALTH_LIMIT;
    }

    public static int getSaplingActionAnimationPeriod() {
        return SAPLING_ACTION_ANIMATION_PERIOD;
    }

    public static int getSaplingNumProperties() {
        return SAPLING_NUM_PROPERTIES;
    }

    public static int getSaplingId() {
        return SAPLING_ID;
    }

    public static int getSaplingCol() {
        return SAPLING_COL;
    }

    public static int getSaplingRow() {
        return SAPLING_ROW;
    }

    public static int getSaplingHealth() {
        return SAPLING_HEALTH;
    }


    public boolean transformPlant(
            WorldModel world,
            EventScheduler scheduler,
            ImageStore imageStore) {
        if (this.getHealth() <= 0) {
            Entity stump = new Stump(this.getId(),
                    this.getPosition(),
                    imageStore.getImageList(ImageStore.getStumpKey()));

            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(stump);

            return true;
        } else if (this.getHealth() >= this.getHealthLimit()) {
            Entity tree = new Tree("tree_" + this.getId(),
                    this.getPosition(),
                    Functions.getNumFromRange(getTreeActionMax(), getTreeActionMin()),
                    Functions.getNumFromRange(getTreeAnimationMax(), getTreeAnimationMin()),
                    Functions.getNumFromRange(getTreeHealthMax(), getTreeHealthMin()),
                    imageStore.getImageList(ImageStore.getTreeKey()));

            world.removeEntity( this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity( tree);
            tree.scheduleActions(scheduler, world, imageStore);

            return true;
        }

        return false;
    }

    public void executeEntityActivity(
            EventScheduler scheduler,
            WorldModel world,
            ImageStore imageStore
    )
    {
        this.setHealth(this.getHealth() + 1);
        if (!this.transformPlant( world, scheduler, imageStore))
        {
            scheduler.scheduleEvent(this,
                    new Activity(this, world, imageStore),
                    this.getActionPeriod());
        }
    }

    public int getHealthLimit() {
        return healthLimit;
    }

    public void setHealthLimit(int healthLimit) {
        this.healthLimit = healthLimit;
    }
}
