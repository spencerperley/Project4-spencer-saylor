import processing.core.PImage;

import java.util.*;

/**
 * Represents the 2D World in which this simulation is running.
 * Keeps track of the size of the world, the background image for each
 * location in the world, and the amount of entities that populate the world.
 */
public final class WorldModel
{
    private int numRows;
    private int numCols;
    private Background[][] background;
    private Entity[][] occupancy;
    private Set<Entity> entities;

    public WorldModel(int numRows, int numCols, Background defaultBackground) {
        this.setNumRows(numRows);
        this.setNumCols(numCols);
        this.setBackground(new Background[numRows][numCols]);
        this.setOccupancy(new Entity[numRows][numCols]);
        this.setEntities(new HashSet<>());

        for (int row = 0; row < numRows; row++) {
            Arrays.fill(this.getBackground()[row], defaultBackground);
        }
    }




    public Optional<Entity> findNearest(
             Point pos, List<Class> kinds) {
        List<Entity> ofType = new LinkedList<>();
        for (Class kind : kinds) {
            for (Entity entity : this.getEntities()) {
                if (entity.getClass() == kind) {
                    ofType.add(entity);
                }
            }
        }

        return pos.nearestEntity(ofType);
    }

    /*
       Assumes that there is no entity currently occupying the
       intended destination cell.
    */
    public void addEntity( Entity entity) {
        if (this.withinBounds(entity.getPosition())) {
            this.setOccupancyCell(entity.getPosition(), entity);
            this.getEntities().add(entity);
        }
    }

    public void moveEntity( Entity entity, Point pos) {
        Point oldPos = entity.getPosition();
        if (this.withinBounds( pos) && !pos.equals(oldPos)) {
            this.setOccupancyCell(oldPos, null);
            this.removeEntityAt(pos);
            this.setOccupancyCell( pos, entity);
            entity.setPosition(pos);
        }
    }

    public  void removeEntity( Entity entity) {
        this.removeEntityAt(entity.getPosition());
    }

    public void removeEntityAt( Point pos) {
        if (this.withinBounds( pos) && this.getOccupancyCell( pos) != null) {
            Entity entity = this.getOccupancyCell( pos);

            /* This moves the entity just outside of the grid for
             * debugging purposes. */
            entity.setPosition(new Point(-1, -1));
            this.getEntities().remove(entity);
            this.setOccupancyCell( pos, null);
        }
    }

    public Optional<PImage> getBackgroundImage(Point pos) {
        if (this.withinBounds( pos)) {
            return Optional.of(this.getBackgroundCell( pos).getCurrentImage());
        } else {
            return Optional.empty();
        }
    }

    public  void setBackground(
            Point pos, Background background) {
        if (this.withinBounds(pos)) {
            this.setBackgroundCell( pos, background);
        }
    }

    public Optional<Entity> getOccupant( Point pos) {
        if (this.isOccupied(pos)) {
            return Optional.of(this.getOccupancyCell( pos));
        } else {
            return Optional.empty();
        }
    }

    public Entity getOccupancyCell( Point pos) {
        return this.getOccupancy()[pos.getY()][pos.getX()];
    }

    public void setOccupancyCell(
            Point pos, Entity entity) {
        this.getOccupancy()[pos.getY()][pos.getX()] = entity;
    }

    public Background getBackgroundCell( Point pos) {
        return this.getBackground()[pos.getY()][pos.getX()];
    }

    public void setBackgroundCell(
             Point pos, Background background) {
        this.getBackground()[pos.getY()][pos.getX()] = background;
    }

    public void tryAddEntity( Entity entity) {
        if (this.isOccupied(entity.getPosition())) {
            // arguably the wrong type of exception, but we are not
            // defining our own exceptions yet
            throw new IllegalArgumentException("position occupied");
        }

        this.addEntity(entity);
    }

    public boolean withinBounds(Point pos) {
        return pos.getY() >= 0 && pos.getY() < this.getNumRows() && pos.getX() >= 0
                && pos.getX() < this.getNumCols();
    }

    public boolean isOccupied(Point pos) {
        return this.withinBounds(pos) && this.getOccupancyCell( pos) != null;
    }






    public void load(
            Scanner in, ImageStore imageStore) {
        int lineNumber = 0;
        while (in.hasNextLine()) {
            try {
                if (!imageStore.processLine(in.nextLine(), this)) {
                    System.err.println(String.format("invalid entry on line %d",
                            lineNumber));
                }
            } catch (NumberFormatException e) {
                System.err.println(
                        String.format("invalid entry on line %d", lineNumber));
            } catch (IllegalArgumentException e) {
                System.err.println(
                        String.format("issue on line %d: %s", lineNumber,
                                e.getMessage()));
            }
            lineNumber++;
        }
    }


    public int getNumRows() {
        return numRows;
    }

    public void setNumRows(int numRows) {
        this.numRows = numRows;
    }

    public int getNumCols() {
        return numCols;
    }

    public void setNumCols(int numCols) {
        this.numCols = numCols;
    }

    public Background[][] getBackground() {
        return background;
    }

    public void setBackground(Background[][] background) {
        this.background = background;
    }

    public Entity[][] getOccupancy() {
        return occupancy;
    }

    public void setOccupancy(Entity[][] occupancy) {
        this.occupancy = occupancy;
    }

    public Set<Entity> getEntities() {
        return entities;
    }

    public void setEntities(Set<Entity> entities) {
        this.entities = entities;
    }
}
