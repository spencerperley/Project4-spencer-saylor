import processing.core.PImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.List;

public class Sheep extends ActivityEntity implements Moving{

    public Sheep(
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


    //   public void executeEntityActivity(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
 //      return null;
 //   }



    public static Entity createSheep(String id, Point position, int actionPeriod, int animationPeriod, List<PImage> images){
        return new Sheep(id, position,actionPeriod, animationPeriod, images);
        }


    public Point nextPosition(WorldModel world, Point destPos) {
        return null;
    }
}
