import java.util.*;

import processing.core.PApplet;
import processing.core.PImage;

public final class ImageStore
{
    private static final String OBSTACLE_KEY = "obstacle";
    private static final String STUMP_KEY = "stump";
    private static final String TREE_KEY = "tree";
    private static final String SAPLING_KEY = "sapling";
    private static final String ZOMBIE_KEY = "zombie";
    private static final int COLOR_MASK = 0xffffff;
    private static final int KEYED_IMAGE_MIN = 5;
    private static final int KEYED_RED_IDX = 2;
    private static final int KEYED_GREEN_IDX = 3;
    private static final int KEYED_BLUE_IDX = 4;

    private static final int PROPERTY_KEY = 0;
    private static final String HOUSE_KEY = "house";
    private static final String FAIRY_KEY = "fairy";
    private static final String DUDE_KEY = "dude";
    private static final String BGND_KEY = "background";
    private Map<String, List<PImage>> images;
    private List<PImage> defaultImages;

    public ImageStore(PImage defaultImage) {
        this.setImages(new HashMap<>());
        setDefaultImages(new LinkedList<>());
        getDefaultImages().add(defaultImage);
    }

    public static void setAlpha(PImage img, int maskColor, int alpha) {
        int alphaValue = alpha << 24;
        int nonAlpha = maskColor & getColorMask();
        img.format = PApplet.ARGB;
        img.loadPixels();
        for (int i = 0; i < img.pixels.length; i++) {
            if ((img.pixels[i] & getColorMask()) == nonAlpha) {
                img.pixels[i] = alphaValue | nonAlpha;
            }
        }
        img.updatePixels();
    }

    public static int getColorMask() {
        return COLOR_MASK;
    }

    public static String getZombieKey() {
        return ZOMBIE_KEY;
    }

    public static int getKeyedImageMin() {
        return KEYED_IMAGE_MIN;
    }

    public static int getPropertyKey() {
        return PROPERTY_KEY;
    }

    public static String getHouseKey() {
        return HOUSE_KEY;
    }

    public static String getFairyKey() {
        return FAIRY_KEY;
    }

    public static String getDudeKey() {
        return DUDE_KEY;
    }

    public static String getBgndKey() {
        return BGND_KEY;
    }

    public static String getStumpKey() {
        return STUMP_KEY;
    }

    public static String getTreeKey() {
        return TREE_KEY;
    }

    public static String getSaplingKey() {
        return SAPLING_KEY;
    }

    public boolean processLine(
            String line, WorldModel world) {
        String[] properties = line.split("\\s");
        if (properties.length > 0) {
            switch (properties[getPropertyKey()]) {
                case BGND_KEY:
                    return Background.parseBackground(properties, world,this);
                case DUDE_KEY:
                    return DudeEntity.parseDude(properties, world,this);
                case OBSTACLE_KEY:
                    return Obstacle.parseObstacle(properties, world,this);
                case FAIRY_KEY:
                    return Fairy.parseFairy(properties, world,this);
                case HOUSE_KEY:
                    return House.parseHouse(properties, world,this);
                case TREE_KEY:
                    return Tree.parseTree(properties, world,this);
                case SAPLING_KEY:
                    return Sapling.parseSapling(properties, world,this);
            }
        }

        return false;
    }

    public static void processImageLine(
            Map<String, List<PImage>> images, String line, PApplet screen) {
        String[] attrs = line.split("\\s");
        if (attrs.length >= 2) {
            String key = attrs[0];
            PImage img = screen.loadImage(attrs[1]);
            if (img != null && img.width != -1) {
                List<PImage> imgs = getImages(images, key);
                imgs.add(img);

                if (attrs.length >= getKeyedImageMin()) {
                    int r = Integer.parseInt(attrs[KEYED_RED_IDX]);
                    int g = Integer.parseInt(attrs[KEYED_GREEN_IDX]);
                    int b = Integer.parseInt(attrs[KEYED_BLUE_IDX]);
                    ImageStore.setAlpha(img, screen.color(r, g, b), 0);
                }
            }
        }
    }


    public void loadImages(
            Scanner in,  PApplet screen) {
        int lineNumber = 0;
        while (in.hasNextLine()) {
            try {
                processImageLine(this.getImages(), in.nextLine(), screen);
            } catch (NumberFormatException e) {
                System.out.println(
                        String.format("Image format error on line %d",
                                lineNumber));
            }
            lineNumber++;
        }
    }

    public List<PImage> getImageList( String key) {
        return this.getImages().getOrDefault(key, this.getDefaultImages());
    }

    public static List<PImage> getImages(
            Map<String, List<PImage>> images, String key)
    {
        List<PImage> imgs = images.get(key);
        if (imgs == null) {
            imgs = new LinkedList<>();
            images.put(key, imgs);
        }
        return imgs;
    }


    public Map<String, List<PImage>> getImages() {
        return images;
    }

    public List<PImage> getDefaultImages() {
        return defaultImages;
    }

    public void setImages(Map<String, List<PImage>> images) {
        this.images = images;
    }

    public void setDefaultImages(List<PImage> defaultImages) {
        this.defaultImages = defaultImages;
    }

    public static String getObstacleKey() {
        return OBSTACLE_KEY;
    }
}
