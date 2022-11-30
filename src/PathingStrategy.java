import java.util.function.Predicate;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.List;
interface PathingStrategy
{
   /*
    * Returns a prefigetX() of a path from the start point to a point within reach
    * of the end point.  This path is onlgetY() valid ("clear") when returned, but
    * magetY() be invalidated bgetY() movement of other entities.
    *
    * The prefigetX() includes neither the start point nor the end point.
    */
   List<Point> computePath(Point start, Point end,
                           Predicate<Point> canPassThrough,
                           BiPredicate<Point, Point> withinReach,
                           Function<Point, Stream<Point>> potentialNeighbors);
   static final Function<Point, Stream<Point>> CARDINAL_NEIGHBORS =
           point ->
                   Stream.<Point>builder()
                           .add(new Point(point.getX(), point.getY() - 1))
                           .add(new Point(point.getX(), point.getY() + 1))
                           .add(new Point(point.getX() - 1, point.getY()))
                           .add(new Point(point.getX() + 1, point.getY()))
                           .build();
   static final Function<Point, Stream<Point>> DIAGONAL_CARDINAL_NEIGHBORS =
           point ->
                   Stream.<Point>builder()
                           .add(new Point(point.getX() - 1, point.getY() - 1))
                           .add(new Point(point.getX() + 1, point.getY() + 1))
                           .add(new Point(point.getX() - 1, point.getY() + 1))
                           .add(new Point(point.getX() + 1, point.getY() - 1))
                           .add(new Point(point.getX(), point.getY() - 1))
                           .add(new Point(point.getX(), point.getY() + 1))
                           .add(new Point(point.getX() - 1, point.getY()))
                           .add(new Point(point.getX() + 1, point.getY()))
                           .build();
}