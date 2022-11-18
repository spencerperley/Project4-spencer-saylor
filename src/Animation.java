public class Animation implements Action {
    private int repeatCount;
    private AnimatedEntity entity;

    public Animation(AnimatedEntity entity, int repeatCount) {
        this.setEntity(entity);
        this.setRepeatCount(repeatCount);

    }

    @Override
    public void executeAction(EventScheduler scheduler) {
        this.executeAnimationAction(scheduler);

    }

    public void executeAnimationAction(EventScheduler scheduler)
    {
        this.getEntity().nextImage();

        if (this.getRepeatCount() != 1) {
            scheduler.scheduleEvent(this.getEntity(),
                    new Animation(this.getEntity(),
                            Math.max(this.getRepeatCount() - 1,
                                    0)),
                    this.getEntity().getAnimationPeriod());
        }
    }

    public int getRepeatCount() {
        return repeatCount;
    }

    public void setRepeatCount(int repeatCount) {
        this.repeatCount = repeatCount;
    }

    public AnimatedEntity getEntity() {
        return entity;
    }

    public void setEntity(AnimatedEntity entity) {
        this.entity = entity;
    }
}

