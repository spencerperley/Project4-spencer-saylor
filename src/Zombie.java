import processing.core.PImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Zombie extends ActivityEntity implements Moving{

    private static final int ZOMBIE_NUM_PROPERTIES = 10;
    private static final int ZOMBIE_ID = 1;
    private static final int ZOMBIE_COL = 2;
    private static final int ZOMBIE_ROW = 3;
    private static final int ZOMBIE_ACTION_PERIOD = 5;
    private static final int ZOMBIE_ANIMATION_PERIOD = 4;


    public Zombie(
            String id,
            Point position, int actionPeriod,
            int animationPeriod,
            List<PImage> images) {
        super(id, position, actionPeriod, animationPeriod, images);
    }

    public static int getZombieCol() {
        return ZOMBIE_COL;
    }

    public static int getZombieRow() {
        return ZOMBIE_ROW;
    }

    public static int getZombieNumProperties() {
        return ZOMBIE_NUM_PROPERTIES;
    }

    public static int getZombieAnimationPeriod() {
        return ZOMBIE_ANIMATION_PERIOD;
    }

    public static int getZombieActionPeriod() {
        return ZOMBIE_ACTION_PERIOD;
    }

    public static int getZombieId() {
        return ZOMBIE_ID;
    }


    public static Zombie createZombie(String id, Point position, int actionPeriod, int animationPeriod, List<PImage> images){
        return new Zombie(id, position, actionPeriod, animationPeriod, images);
    }

    public Point nextPosition(
            WorldModel world, Point destPos) {
        PathingStrategy strategy = new AStarPathingStrategy();
        List<Point> pointList = strategy.computePath(this.getPosition(), destPos
                , (p1) -> world.withinBounds(p1) && !world.isOccupied(p1), Point::adjacent, PathingStrategy.CARDINAL_NEIGHBORS);

        if(pointList.size() == 0){
            return getPosition();
        }

        return pointList.get(0);


    }

    public void executeEntityActivity(
            EventScheduler scheduler,
            WorldModel world,
            ImageStore imageStore)
    {
        Optional<Entity> zombieTarget =
                world.findNearest(this.getPosition(), new ArrayList<>(Arrays.asList(DudeNotFull.class)));

        if (zombieTarget.isPresent()) {
            Point tgtPos = zombieTarget.get().getPosition();

            if (moveToZombie(world,  zombieTarget.get(), scheduler)) {
                Entity stump = new Stump("stump" + this.getId(), tgtPos,
                        imageStore.getImageList(ImageStore.getDudeKey()));

                world.addEntity(stump);

            }
        }

        scheduler.scheduleEvent(this,
                new Activity(this, world, imageStore),
                this.getActionPeriod());
    }

    public boolean moveToZombie(
            WorldModel world,
            Entity target,
            EventScheduler scheduler) {
        if (Point.adjacent(this.getPosition(), target.getPosition())) {
            world.removeEntity( target);
            scheduler.unscheduleAllEvents(target);
            return true;
        } else {
            Point nextPos = this.nextPosition(world, target.getPosition());

            if (!this.getPosition().equals(nextPos)) {
                Optional<Entity> occupant = world.getOccupant( nextPos);
                if (occupant.isPresent()) {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity( this, nextPos);
            }
            return false;
        }
    }



}
