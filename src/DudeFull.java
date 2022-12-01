import processing.core.PImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class DudeFull extends DudeEntity implements Moving{

    public DudeFull(String id,
                       Point position,
                       int actionPeriod,
                       int animationPeriod,
                       int resourceLimit,
                       List<PImage> images){
        super(id,position,actionPeriod,animationPeriod,resourceLimit,images);

    }

    public void transformFull(
            WorldModel world,
            EventScheduler scheduler,
            ImageStore imageStore) {
        Entity miner = new DudeNotFull(this.getId(),
                this.getPosition(), this.getActionPeriod(),
                this.getAnimationPeriod(),
                this.getResourceLimit(),
                this.getImages());

        world.removeEntity( this);
        scheduler.unscheduleAllEvents(this);

        world.addEntity( miner);
        miner.scheduleActions(scheduler, world, imageStore);
    }

    public void executeEntityActivity(
            EventScheduler scheduler,
            WorldModel world,
            ImageStore imageStore)
    {
        Optional<Entity> fullTarget =
                world.findNearest(this.getPosition(), new ArrayList<>(Arrays.asList(House.class)));

        if (fullTarget.isPresent() && this.moveToDude(world,
                 fullTarget.get(), scheduler))
        {
            this.transformFull( world, scheduler, imageStore);
        }
        else {
            scheduler.scheduleEvent( this,
                   new Activity(this, world, imageStore),
                    this.getActionPeriod());
        }
    }

    public boolean moveToDude(
            WorldModel world,
            Entity target,
            EventScheduler scheduler) {
        if (Point.adjacent(this.getPosition(), target.getPosition())) {
            return true;
        } else {
            Point nextPos = this.nextPosition(world, target.getPosition());

            if (!this.getPosition().equals(nextPos)) {
                Optional<Entity> occupant = world.getOccupant( nextPos);
                if (occupant.isPresent()) {
                    scheduler.unscheduleAllEvents( occupant.get());
                }

                world.moveEntity( this, nextPos);
            }
            return false;
        }
    }


}
