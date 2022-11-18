import processing.core.PImage;

import java.util.List;

public abstract class AnimatedEntity extends Entity{

    private int animationPeriod;

    public AnimatedEntity(
            String id,
            Point position,
            int animationPeriod,
            List<PImage> images)
    {
       super(id,position,images);
       this.setAnimationPeriod(animationPeriod);
    }

    public int getAnimationPeriod() {
        return this.animationPeriod;
    }

    public void scheduleActions(
            EventScheduler scheduler,
            WorldModel world,
            ImageStore imageStore)
    {
        scheduler.scheduleEvent(this,
                new Animation(this, 0),
                this.getAnimationPeriod());
    }

    public void setAnimationPeriod(int animationPeriod) {
        this.animationPeriod = animationPeriod;
    }
}
