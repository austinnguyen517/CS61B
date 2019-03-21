package bearmaps;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KDTreeTest {
    @Test
    public void testSimple() {
        List<Point> points = new ArrayList<>();
        Point thing = new Point(15, 15);
        Point otherThing = new Point(16, 16);
        Point anoThing = new Point(-5, 0);
        points.add(thing);
        points.add(otherThing);
        points.add(anoThing);
        NaivePointSet test = new NaivePointSet(points);
        KDTree otherTest = new KDTree(points);
        assertEquals(test.nearest(0, 0), anoThing);
        assertEquals(otherTest.nearest(0, 0), anoThing);
    }
    @Test
    public void testKDRand() {
        Random rand = new Random(50);
        for (int iterations = 0; iterations < 50; iterations += 1) {
            List<Point> points = new ArrayList<>();
            for (int num = 1; num <= 30; num += 1) {
                points.add(new Point(rand.nextDouble(), rand.nextDouble()));
            }
            NaivePointSet expected = new NaivePointSet(points);
            KDTree received = new KDTree(points);
            double x = rand.nextDouble();
            double y = rand.nextDouble();
            assertEquals(expected.nearest(x, y), received.nearest(x, y));
        }
    }
}
