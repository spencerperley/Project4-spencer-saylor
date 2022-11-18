import processing.core.PApplet;
import processing.core.PImage;

import java.util.Optional;

public final class WorldView
{
    private PApplet screen;
    private WorldModel world;
    private int tileWidth;
    private int tileHeight;
    private Viewport viewport;

    public WorldView(
            int numRows,
            int numCols,
            PApplet screen,
            WorldModel world,
            int tileWidth,
            int tileHeight)
    {
        this.setScreen(screen);
        this.setWorld(world);
        this.setTileWidth(tileWidth);
        this.setTileHeight(tileHeight);
        this.setViewport(new Viewport(numRows, numCols));
    }

    public void shiftView(int colDelta, int rowDelta) {
        int newCol = Functions.clamp(this.getViewport().getCol() + colDelta, 0,
                this.getWorld().getNumCols() - this.getViewport().getNumCols());
        int newRow = Functions.clamp(this.getViewport().getRow() + rowDelta, 0,
                this.getWorld().getNumRows() - this.getViewport().getNumRows());

        this.getViewport().shift(newCol, newRow);
    }

    public void drawBackground() {
        for (int row = 0; row < this.getViewport().getNumRows(); row++) {
            for (int col = 0; col < this.getViewport().getNumCols(); col++) {
                Point worldPoint = this.getViewport().viewportToWorld(col, row);
                Optional<PImage> image =
                this.getWorld().getBackgroundImage( worldPoint);
                if (image.isPresent()) {
                    this.getScreen().image(image.get(), col * this.getTileWidth(),
                            row * this.getTileHeight());
                }
            }
        }
    }

    public void drawEntities() {
        for (Entity entity : this.getWorld().getEntities()) {
            Point pos = entity.getPosition();

            if (this.getViewport().contains(pos)) {
                Point viewPoint = this.getViewport().worldToViewport(pos.getX(), pos.getY());
                this.getScreen().image(entity.getCurrentImage(),
                        viewPoint.getX() * this.getTileWidth(),
                        viewPoint.getY() * this.getTileHeight());
            }
        }
    }

    public void drawViewport() {
        this.drawBackground();
        this.drawEntities();
    }


    public PApplet getScreen() {
        return screen;
    }

    public void setScreen(PApplet screen) {
        this.screen = screen;
    }

    public WorldModel getWorld() {
        return world;
    }

    public void setWorld(WorldModel world) {
        this.world = world;
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public void setTileWidth(int tileWidth) {
        this.tileWidth = tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public void setTileHeight(int tileHeight) {
        this.tileHeight = tileHeight;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }
}
