package bearmaps;

import java.util.HashMap;
import java.util.List;

public class KDTree implements PointSet {
    /*Make a hash map that maps every point to a 2 dim array that represents its children*/
    /*Initialize some kind of root*/
    private Point root = null;
    private HashMap<Point, Point[]> hashPoints = new HashMap<>();


    public KDTree(List<Point> points) {
        /*Take one point. Add it to the hashmap*/
        for (Point curr: points) {
            if (isEmpty()) {
                root = curr;
                hashPoints.put(curr, new Point[2]);
            } else {
                add(root, curr, 0);
            }
        }
    }

    private boolean isEmpty() {
        return hashPoints.size() == 0;
    }
    public Point nearest(double x, double y) {
        /*Use the helper function near and just return the value*/
        Point comp = new Point(x, y);
        if (isEmpty()) {
            return null;
        } else {
            return near(root, comp, 0, Point.distance(root, comp), root);
        }
    }

    private void add(Point curr, Point addThis, int depth) {
        double addComp;
        double currComp;
        int idx;
        if (depth % 2 == 0) { /*Compare x values*/
            addComp = addThis.getX();
            currComp = curr.getX();
        } else {
            addComp = addThis.getY();
            currComp = curr.getY();
        }
        if (addComp > currComp) {
            idx = 1;
        } else {
            idx = 0;
        }
        if (hashPoints.get(curr)[idx] == null) {
            hashPoints.get(curr)[idx] = addThis;
            hashPoints.put(addThis, new Point[2]);
        } else {
            add(hashPoints.get(curr)[idx], addThis, depth + 1);
        }
    }

    private Point near(Point curr, Point comp, int depth, double best, Point closest) {
        /*If curr is null, then return closest*/
        if (curr == null) {
            return closest;
        }
        double refComp;
        double currComp;
        int goodChild;
        int badChild;
        /*If depth is odd, compare y values. Else, compare x values*/
        if (depth % 2 == 0) { /*Compare x values*/
            refComp = comp.getX();
            currComp = curr.getX();
        } else {
            refComp = comp.getY();
            currComp = curr.getY();
        }
        /*Calc distance from curr to the x,y*/
        double distCurr = Point.distance(curr, comp);
        if (distCurr < best) {
            closest = curr;
            best = distCurr;
        }
        /*Check the children*/
        if (refComp > currComp) {  /*Determine the good child by comparing x and y to curr*/
            goodChild = 1;
            badChild = 0;
        } else {
            goodChild = 0;
            badChild = 1;
        }
        Point closeGood =  near(hashPoints.get(curr)[goodChild], comp, depth + 1, best, closest);
        /*Reassign closest by comparing closeGood to closest*/
        if (Point.distance(closeGood, comp) < best) {
            closest = closeGood;
            best = Point.distance(closest, comp);
        } /*Determine whether or not to prune the bad child*/
        if (Math.pow(refComp - currComp, 2) < best) {
            Point closeBad = near(hashPoints.get(curr)[badChild], comp, depth + 1, best, closest);
            if (Point.distance(closeBad, comp) < best) {
                closest = closeBad;
            }
        }
        return closest;
    }
}
