package bearmaps;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class ArrayHeapMinPQTest {
    @Test
    public void sanity() {
        ArrayHeapMinPQ test = new ArrayHeapMinPQ();
        assertEquals(0, test.size());
        test.add(5, 1);
        assertEquals(1, test.size());
        assertEquals(5, test.getSmallest());
        assertTrue(test.contains(5));
        assertEquals(5, test.removeSmallest());
        assertEquals(0, test.size());
    }

    @Test
    public void tree() {
        ArrayHeapMinPQ test = new ArrayHeapMinPQ();
        test.add(1, 4);
        test.add(2, 2);
        assertEquals(2, test.getSmallest());
        assertFalse(test.contains(4));
        test.changePriority(1, 1);
        assertEquals(1, test.getSmallest());
    }

    @Test
    public void complexTree() {
        ArrayHeapMinPQ test = new ArrayHeapMinPQ();
        test.add(1, 4);
        test.add(2, 3);
        test.add(3, 7);
        test.add(6, 1);
        test.add(9, 7);
        test.add(10, 2);
        assertEquals(6, test.getSmallest());
        assertEquals(6, test.size());
        test.removeSmallest();
        assertEquals(5, test.size());
        assertEquals(10, test.getSmallest());
        test.removeSmallest();
        assertEquals(2, test.getSmallest());
        test.add(0, 0);
        assertEquals(0, test.getSmallest());
        test.changePriority(0, 99);
        assertEquals(2, test.getSmallest());
    }
}
