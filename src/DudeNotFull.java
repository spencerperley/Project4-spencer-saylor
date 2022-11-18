import processing.core.PImage;

import java.util.*;

public class DudeNotFull extends DudeEntity implements Moving{

    private int resourceCount;

    public DudeNotFull(String id,
                       Point position,
                       int actionPeriod,
                       int animationPeriod,
                       int resourceLimit,
                       List<PImage> images){
        super(id,position,actionPeriod,animationPeriod,resourceLimit,images);
        this.setResourceCount(0);
    }

    public boolean transformNotFull(
            WorldModel world,
            EventScheduler scheduler,
            ImageStore imageStore) {
        if (this.getResourceCount() >= this.getResourceLimit()) {
            Entity miner = new DudeFull(this.getId(),
                    this.getPosition(), this.getActionPeriod(),
                    this.getAnimationPeriod(),
                    this.getResourceLimit(),
                    this.getImages());

            world.removeEntity( this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity( miner);
            miner.scheduleActions(scheduler, world, imageStore);

            return true;
        }

        return false;
    }

    public void executeEntityActivity(EventScheduler scheduler,
            WorldModel world,
            ImageStore imageStore)
    {
        Optional<Entity> target =
                world.findNearest(this.getPosition(), new ArrayList<>(Arrays.asList(Tree.class, Sapling.class)));

        if (!target.isPresent() || ! this.moveToDude(world,
                (TreeEntity) target.get(),
                scheduler)
                || !this.transformNotFull(world, scheduler, imageStore))
        {
            scheduler.scheduleEvent(this,
                    new Activity(this, world, imageStore),
                    this.getActionPeriod());
        }
    }

    public boolean moveToDude(WorldModel world,
            TreeEntity target,
            EventScheduler scheduler) {
        if (Point.adjacent(this.getPosition(), target.getPosition())) {
            this.setResourceCount(this.getResourceCount() + 1);
            target.setHealth(target.getHealth() - 1);
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

    public int getResourceCount() {
        return resourceCount;
    }

    public void setResourceCount(int resourceCount) {
        this.resourceCount = resourceCount;
    }
}
