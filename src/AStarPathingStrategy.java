import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class AStarPathingStrategy
        implements PathingStrategy {


    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors) {

        //closed list, have already visited
        HashMap<Point, Node> closed = new HashMap<>();
        //open list, have not been to
        HashMap<Point, Node> free = new HashMap<>();
        //(node) -> getF(); Queue of the lowest f val
        PriorityQueue<Node> open = new PriorityQueue<>(Comparator.comparing(Node::getF));
        //path
        List<Point> path = new ArrayList<>();

        Node current = new Node((Math.abs(start.getX() - end.getX()) + Math.abs(start.getY() - end.getY())), 0, Math.abs((start.getX() - end.getX()) + Math.abs(start.getY() - end.getY())), start, null);
        open.add(current);
        free.put(current.point, current);

        while (!open.isEmpty()) { // if open is empty we should have reached our path or there is no valid path

            open.remove(current);

            if (withinReach.test(current.point, end)) { // check if you have reached the end
                return makePath(path, current, start);
            }
            List<Point> neighbors = potentialNeighbors.apply(current.point).filter(canPassThrough).collect(Collectors.toList());

            for (Point a : neighbors) {
                // calculating distance
                double manhattan = Math.sqrt(Math.pow(a.getX() - end.getX(), 2) + Math.pow(a.getY() - end.getY(), 2));

                Node neighborNode = new Node(current.g + 1 + manhattan, current.h + 1, manhattan, a, current);
                List<Node> neighborNodes = new LinkedList<>();
                neighborNodes.add(neighborNode);

                //Begin searching
                if (!closed.containsKey(a)) { //Check to see if in the closed list
                    if (!open.contains(neighborNode.point)) { // add to the open list if it's not there
                        open.add(neighborNode);
                        free.put(neighborNode.point, neighborNode);
                    }

                    double gVal = current.g + 1; //determine g value

                    if (free.get(a) != null && free.get(a).getG() > gVal) {  //move closer if possible
                        free.get(a).setG(gVal);
                        free.get(a).setF(gVal + manhattan); // calculate f value
                    }
                }

               // System.out.println(closed);
               // System.out.println(open);

            }

            closed.put(current.point, current);
            current = open.peek();

        }
        return path;

    }

    public List<Point> makePath(List<Point> path, Node current, Point start){
        while(current.prevNode != null){
            path.add(current.point);
            current = current.prevNode;
        }

        Collections.reverse(path);
        System.out.println(path);
        return path;
    }

}