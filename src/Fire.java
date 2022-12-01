import processing.core.PImage;

import java.util.List;


public class Fire extends ActivityEntity implements Moving{

    public Fire(
            String id,
            Point position,
            int actionPeriod,
            int animationPeriod,
            List<PImage> images) {
        super(id, position, actionPeriod, animationPeriod, images
        );
    }

    @Override
    public void executeEntityActivity(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {

    }

    public static Entity createFire(String id, Point position, int actionPeriod, int animationPeriod, List<PImage> images){
        return new Fire(id, position,actionPeriod, animationPeriod, images);
    }


    @Override
    public Point nextPosition(WorldModel world, Point destPos) {
        return null;
}
