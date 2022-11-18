import processing.core.PImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Fairy extends ActivityEntity implements Moving {
    private static final int FAIRY_NUM_PROPERTIES = 6;
    private static final int FAIRY_ID = 1;
    private static final int FAIRY_COL = 2;
    private static final int FAIRY_ROW = 3;
    private static final int FAIRY_ANIMATION_PERIOD = 4;
    private static final int FAIRY_ACTION_PERIOD = 5;
    public Fairy(String id,
                 Point position,
                 int actionPeriod,
                 int animationPeriod,
                 List<PImage> images){
        super(id,position,actionPeriod,animationPeriod,images);
    }

    public static boolean parseFairy(
            String[] properties, WorldModel world, ImageStore imageStore) {
        if (properties.length == getFairyNumProperties()) {
            Point pt = new Point(Integer.parseInt(properties[getFairyCol()]),
                    Integer.parseInt(properties[getFairyRow()]));
            Entity entity = new Fairy(properties[getFairyId()],
                    pt,
                    Integer.parseInt(properties[getFairyActionPeriod()]),
                    Integer.parseInt(properties[getFairyAnimationPeriod()]),
                    imageStore.getImageList(ImageStore.getFairyKey()));
            world.tryAddEntity( entity);
        }

        return properties.length == getFairyNumProperties();
    }

    public static int getFairyNumProperties() {
        return FAIRY_NUM_PROPERTIES;
    }

    public static int getFairyId() {
        return FAIRY_ID;
    }

    public static int getFairyCol() {
        return FAIRY_COL;
    }

    public static int getFairyRow() {
        return FAIRY_ROW;
    }

    public static int getFairyAnimationPeriod() {
        return FAIRY_ANIMATION_PERIOD;
    }

    public static int getFairyActionPeriod() {
        return FAIRY_ACTION_PERIOD;
    }

    public boolean moveToFairy(
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

    public void executeEntityActivity(
            EventScheduler scheduler,
            WorldModel world,
            ImageStore imageStore)
    {
        Optional<Entity> fairyTarget =
                world.findNearest(this.getPosition(), new ArrayList<>(Arrays.asList(Stump.class)));

        if (fairyTarget.isPresent()) {
            Point tgtPos = fairyTarget.get().getPosition();

            if (moveToFairy(world,  fairyTarget.get(), scheduler)) {
                Entity sapling = new Sapling("sapling_" + this.getId(), tgtPos,
                        imageStore.getImageList(ImageStore.getSaplingKey()));

                world.addEntity( sapling);
                sapling.scheduleActions(scheduler, world, imageStore);
            }
        }

        scheduler.scheduleEvent(this,
                new Activity(this, world, imageStore),
                this.getActionPeriod());
    }

    public Point nextPosition(
            WorldModel world, Point destPos) {
        int horiz = Integer.signum(destPos.getX() - this.getPosition().getX());
        Point newPos = new Point(this.getPosition().getX() + horiz, this.getPosition().getY());

        if (horiz == 0 || world.isOccupied( newPos)) {
            int vert = Integer.signum(destPos.getY() - this.getPosition().getY());
            newPos = new Point(this.getPosition().getX(), this.getPosition().getY() + vert);

            if (vert == 0 || world.isOccupied( newPos)) {
                newPos = this.getPosition();
            }
        }

        return newPos;
    }
}
