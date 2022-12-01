public class DNode {
    protected int g;
    protected int f;
    protected Point point;
    protected DNode previous;

    public DNode(Point point, DNode previous, Point end) {
        if (previous == null) {
            this.g = 0;
        } else {
            this.g = previous.g + 1;
        }
        this.f =  this.g;
        this.point = point;
        this.previous = previous;
    }

    public int manhattan(Point other) {
        int xdist = Math.abs(other.getX() - this.point.getX());
        int ydist = Math.abs(other.getY() - this.point.getY());
        return xdist + ydist;
    }

    @Override
    public String toString() {
        return  point +"    f= " + f;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getF() {
        return f;
    }

    public void setF(int f) {
        this.f = f;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public DNode getPrevious() {
        return previous;
    }

    public void setPrevious(DNode previous) {
        this.previous = previous;
    }
}
