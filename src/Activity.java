public class Activity implements Action{

    private WorldModel world;
    private ImageStore imageStore;
    private ActivityEntity entity;
    public Activity(ActivityEntity entity, WorldModel world, ImageStore imageStore)
    {
        this.setEntity(entity);
        this.setWorld(world);
        this.setImageStore(imageStore);
    }

    @Override
    public void executeAction(EventScheduler scheduler) {
        this.executeActivityAction(scheduler);
    }
    public void executeActivityAction(EventScheduler scheduler)
    {

        this.getEntity().executeEntityActivity(scheduler, this.getWorld(),
                this.getImageStore());

    }

    public WorldModel getWorld() {
        return world;
    }

    public void setWorld(WorldModel world) {
        this.world = world;
    }

    public ImageStore getImageStore() {
        return imageStore;
    }

    public void setImageStore(ImageStore imageStore) {
        this.imageStore = imageStore;
    }

    public ActivityEntity getEntity() {
        return entity;
    }

    public void setEntity(ActivityEntity entity) {
        this.entity = entity;
    }
}
