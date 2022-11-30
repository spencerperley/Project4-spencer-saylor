import java.util.Objects;

public class Node {
    double f;
    double g;
    double h;
    Point point;
    Node prevNode;

    public Node(double f, double g, double h, Point point, Node prevNode){
        this.f = f;
        this.g = g;
        this.h = h;
        this.point = point;
        this.prevNode = prevNode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Double.compare(node.f, f) == 0 && Double.compare(node.g, g) == 0 && Double.compare(node.h, h) == 0 && Objects.equals(point, node.point) && Objects.equals(prevNode, node.prevNode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(f, g, h, point, prevNode);
    }

    public double getF() {
        return f;
    }

    public double getG() {
        return g;
    }

    public void setF(double f) {
        this.f = f;
    }

    public void setG(double g) {
        this.g = g;
    }
}
