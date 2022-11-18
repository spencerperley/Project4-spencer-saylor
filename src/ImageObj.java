import processing.core.PImage;

import java.util.List;

public abstract class ImageObj {


    private String id;
    private List<PImage> images;
    private int imageIndex;

    public ImageObj(String id, List<PImage> images) {
        this.setId(id);
        this.setImages(images);

    }

    public PImage getCurrentImage() {
            return this.getImages().get(this.getImageIndex());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<PImage> getImages() {
        return images;
    }

    public void setImages(List<PImage> images) {
        this.images = images;
    }

    public int getImageIndex() {
        return imageIndex;
    }

    public void setImageIndex(int imageIndex) {
        this.imageIndex = imageIndex;
    }
}
