import java.util.*;

import processing.core.PImage;
import processing.core.PApplet;

/**
 * This class contains many functions written in a procedural style.
 * You will reduce the size of this class over the next several weeks
 * by refactoring this codebase to follow an OOP style.
 */
public final class Functions {
    public static final Random rand = new Random();


    public static int getNumFromRange(int max, int min) {
        Random rand = new Random();
        return min + rand.nextInt(
                max
                        - min);
    }

    /*
      Called with color for which alpha should be set and alpha value.
      setAlpha(img, color(255, 255, 255), 0));
    */

    public static int clamp(int value, int low, int high) {
        return Math.min(high, Math.max(value, low));
    }

/*

    public static void executeSaplingActivity(
            Entity entity,
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler) {
        entity.health++;
        if (!transformPlant(entity, world, scheduler, imageStore)) {
            scheduleEvent(scheduler, entity,
                    createActivityAction(entity, world, imageStore),
                    entity.actionPeriod);
        }
    }

    public static void executeTreeActivity(
            Entity entity,
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler) {

        if (!transformPlant(entity, world, scheduler, imageStore)) {

            scheduleEvent(scheduler, entity,
                    createActivityAction(entity, world, imageStore),
                    entity.actionPeriod);
        }
    }

    public static void executeFairyActivity(
            Entity entity,
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler) {
        Optional<Entity> fairyTarget =
                findNearest(world, entity.position, new ArrayList<>(Arrays.asList(EntityKind.STUMP)));

        if (fairyTarget.isPresent()) {
            Point tgtPos = fairyTarget.get().position;

            if (moveToFairy(entity, world, fairyTarget.get(), scheduler)) {
                Entity sapling = createSapling("sapling_" + entity.id, tgtPos,
                        getImageList(imageStore, SAPLING_KEY));

                addEntity(world, sapling);
                scheduleActions(sapling, scheduler, world, imageStore);
            }
        }

        scheduleEvent(scheduler, entity,
                createActivityAction(entity, world, imageStore),
                entity.actionPeriod);
    }

    public static void executeDudeNotFullActivity(
            Entity entity,
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler) {
        Optional<Entity> target =
                findNearest(world, entity.position, new ArrayList<>(Arrays.asList(EntityKind.TREE, EntityKind.SAPLING)));

        if (!target.isPresent() || !moveToNotFull(entity, world,
                target.get(),
                scheduler)
                || !transformNotFull(entity, world, scheduler, imageStore)) {
            scheduleEvent(scheduler, entity,
                    createActivityAction(entity, world, imageStore),
                    entity.actionPeriod);
        }
    }

    public static void executeDudeFullActivity(
            Entity entity,
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler) {
        Optional<Entity> fullTarget =
                findNearest(world, entity.position, new ArrayList<>(Arrays.asList(EntityKind.HOUSE)));

        if (fullTarget.isPresent() && moveToFull(entity, world,
                fullTarget.get(), scheduler)) {
            transformFull(entity, world, scheduler, imageStore);
        } else {
            scheduleEvent(scheduler, entity,
                    createActivityAction(entity, world, imageStore),
                    entity.actionPeriod);
        }
    }


    public static boolean moveToFairy(
            Entity fairy,
            WorldModel world,
            Entity target,
            EventScheduler scheduler)
    {
        if (adjacent(fairy.position, target.position)) {
            removeEntity(world, target);
            unscheduleAllEvents(scheduler, target);
            return true;
        }
        else {
            Point nextPos = nextPositionFairy(fairy, world, target.position);

            if (!fairy.position.equals(nextPos)) {
                Optional<Entity> occupant = getOccupant(world, nextPos);
                if (occupant.isPresent()) {
                    unscheduleAllEvents(scheduler, occupant.get());
                }

                moveEntity(world, fairy, nextPos);
            }
            return false;
        }
    }

    public static boolean moveToNotFull(
            Entity dude,
            WorldModel world,
            Entity target,
            EventScheduler scheduler)
    {
        if (adjacent(dude.position, target.position)) {
            dude.resourceCount += 1;
            target.health--;
            return true;
        }
        else {
            Point nextPos = nextPositionDude(dude, world, target.position);

            if (!dude.position.equals(nextPos)) {
                Optional<Entity> occupant = getOccupant(world, nextPos);
                if (occupant.isPresent()) {
                    unscheduleAllEvents(scheduler, occupant.get());
                }

                moveEntity(world, dude, nextPos);
            }
            return false;
        }
    }

    public static boolean moveToFull(
            Entity dude,
            WorldModel world,
            Entity target,
            EventScheduler scheduler)
    {
        if (adjacent(dude.position, target.position)) {
            return true;
        }
        else {
            Point nextPos = nextPositionDude(dude, world, target.position);

            if (!dude.position.equals(nextPos)) {
                Optional<Entity> occupant = getOccupant(world, nextPos);
                if (occupant.isPresent()) {
                    unscheduleAllEvents(scheduler, occupant.get());
                }

                moveEntity(world, dude, nextPos);
            }
            return false;
        }
    }

    public static Point nextPositionFairy(
            Entity entity, WorldModel world, Point destPos)
    {
        int horiz = Integer.signum(destPos.x - entity.position.x);
        Point newPos = new Point(entity.position.x + horiz, entity.position.y);

        if (horiz == 0 || isOccupied(world, newPos)) {
            int vert = Integer.signum(destPos.y - entity.position.y);
            newPos = new Point(entity.position.x, entity.position.y + vert);

            if (vert == 0 || isOccupied(world, newPos)) {
                newPos = entity.position;
            }
        }

        return newPos;
    }

    public static Point nextPositionDude(
            Entity entity, WorldModel world, Point destPos)
    {
        int horiz = Integer.signum(destPos.x - entity.position.x);
        Point newPos = new Point(entity.position.x + horiz, entity.position.y);

        if (horiz == 0 || isOccupied(world, newPos) && getOccupancyCell(world, newPos).kind != EntityKind.STUMP) {
            int vert = Integer.signum(destPos.y - entity.position.y);
            newPos = new Point(entity.position.x, entity.position.y + vert);

            if (vert == 0 || isOccupied(world, newPos) &&  getOccupancyCell(world, newPos).kind != EntityKind.STUMP) {
                newPos = entity.position;
            }
        }

        return newPos;
    }
*/

}
