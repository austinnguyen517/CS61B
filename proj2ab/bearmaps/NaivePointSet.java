package bearmaps;

import java.util.Iterator;
import java.util.List;

public class NaivePointSet implements PointSet {
    private List<Point> places;

    public NaivePointSet(List<Point> points) {
        places = points;
    }

    public Point nearest(double x, double y) {
        Point ref = new Point(x, y);
        Point best = null;
        double bestDist = 0;
        double currDist;
        Iterator it = places.iterator();
        while (it.hasNext()) {
            Point candidate = (Point) it.next();
            if (best == null) {
                best = candidate;
                bestDist = Point.distance(ref, candidate);
            } else {
                currDist = Point.distance(ref, candidate);
                if (currDist < bestDist) {
                    best = candidate;
                    bestDist = currDist;
                }
            }
        }
        return best;
    }
}
