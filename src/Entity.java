import java.util.List;

import processing.core.PImage;

/**
 * An entity that exists in the world. See EntityKind for the
 * different kinds of entities that exist.
 */
public abstract class Entity extends ImageObj
{

    private Point position;
    public Entity(

            String id,
            Point position,
            List<PImage> images
           )
    {
        super(id,images);

        this.setPosition(position);

        this.setImageIndex(0);

    }
    public void scheduleActions(
            EventScheduler scheduler,
            WorldModel world,
            ImageStore imageStore){

    }



    public void nextImage() {
        this.setImageIndex((this.getImageIndex() + 1) % this.getImages().size());
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }


    // health starts at 0 and builds up until ready to convert to Tree



    // need resource count, though it always starts at 0


    // do

}

