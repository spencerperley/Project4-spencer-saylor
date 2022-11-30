import processing.core.PImage;

import java.util.List;

public class Zombie {

    private static final int properties = 6;
    private String id;
    private static final int col = 2;
    private static final int row = 3;
    private int action = 5;
    private int animation = 4;
    private Point position;
    private List<PImage> images;


    public Zombie(
            String id,
            Point position, int actionPeriod,
            int animationPeriod,
            List<PImage> images) {
        this.id = id;
        this.position = position;
        this.action = actionPeriod;
        this.animation = animationPeriod;
        this.images = images;


    }

    public static Zombie createZombie(String id, Point position, int actionPeriod, int animationPeriod, List<PImage> images){
        return new Zombie(id, position, actionPeriod, animationPeriod, images);
    }

    public Point nextPosition(WorldModel world, Point destPos){
        Point next = new Point(1, 1);
        return next;
    }


}
