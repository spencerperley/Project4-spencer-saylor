import processing.core.PImage;

import java.util.List;

public abstract class ActivityEntity extends AnimatedEntity{

    private int actionPeriod;

    public ActivityEntity(String id,
                          Point position,
                          int actionPeriod,
                          int animationPeriod,
                          List<PImage> images)
    {
        super(id,position,animationPeriod,images);
        this.setActionPeriod(actionPeriod);
    }

    public abstract void executeEntityActivity(EventScheduler scheduler,
                                                     WorldModel world,
                                                     ImageStore imageStore);

    public void scheduleActions(
            EventScheduler scheduler,
            WorldModel world,
            ImageStore imageStore) {
        scheduler.scheduleEvent(this,
                new Activity(this, world, imageStore),
                this.getActionPeriod());
        scheduler.scheduleEvent(this,new Animation(this,0), this.getActionPeriod());
    }

    public int getActionPeriod() {
        return actionPeriod;
    }

    public void setActionPeriod(int actionPeriod) {
        this.actionPeriod = actionPeriod;
    }
}
