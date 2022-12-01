import java.util.*;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class DijkstraStrategy
        implements PathingStrategy
{


    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors)
    {
        List<Point> path = new ArrayList<>();

        List<Point> closedList = new LinkedList<>();

        PriorityQueue<DNode> openList = new PriorityQueue<DNode>(Comparator.comparing(DNode::getF));
        openList.add(new DNode(start,null, end));

        while (!openList.isEmpty()){
            DNode current = openList.poll();
            closedList.add(current.getPoint());
            List<Point> addToOpen = potentialNeighbors.apply(current.getPoint())
                    .filter(canPassThrough)
                    .filter(pt ->
                            !pt.equals(start))
                    .collect(Collectors.toList());

            outerloop:
            for (Point point:
                    addToOpen) {
                for (Point closedPoint:
                     closedList) {
                    if (closedPoint.equals(point)){
                        continue outerloop;
                    }
                }

                DNode succ = new DNode(point,current,end);
                if (succ.getPoint().equals(end)){
                    DNode curnode = succ;
                    while (true) {

                        if (curnode.getPrevious() == null) {
                            return path;
                        }
                        curnode = curnode.getPrevious();
                        path.add(curnode.getPoint());
                    }
                }

                for (Point clPoint:
                        closedList) {
                    if (clPoint.equals(succ.getPoint())){
                        continue outerloop;
                    }
                }

                for (DNode openNode:
                     openList) {
                    if (openNode.point.equals(succ.point)){
                        if (openNode.f <= succ.f){
                            continue outerloop;
                        }
                        else{
                            openList.remove(openNode);
                        }
                    }
                }
                openList.add(succ);
            }
        }


        return path;
    }
}
