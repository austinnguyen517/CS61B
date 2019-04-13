import edu.princeton.cs.algs4.Queue;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class TestSortAlgs {

    @Test
    public void testQuickSort() {
        Queue<Integer> example = new Queue<>();
        example.enqueue(2);
        example.enqueue(1);
        example.enqueue(3);
        example.enqueue(1);
        example.enqueue(7);
        example.enqueue(0);
        example.enqueue(4);
        Queue<Integer> sorted = QuickSort.quickSort(example);
        assertTrue(isSorted(sorted));
    }

    @Test
    public void testMergeSort() {
        Queue<Integer> example = new Queue<>();
        example.enqueue(2);
        example.enqueue(1);
        example.enqueue(3);
        example.enqueue(1);
        example.enqueue(7);
        example.enqueue(0);
        example.enqueue(4);
        Queue<Integer> sorted = MergeSort.mergeSort(example);
        assertTrue(isSorted(sorted));
    }

    /**
     * Returns whether a Queue is sorted or not.
     *
     * @param items  A Queue of items
     * @return       true/false - whether "items" is sorted
     */
    private <Item extends Comparable> boolean isSorted(Queue<Item> items) {
        if (items.size() <= 1) {
            return true;
        }
        Item curr = items.dequeue();
        Item prev = curr;
        while (!items.isEmpty()) {
            prev = curr;
            curr = items.dequeue();
            if (curr.compareTo(prev) < 0) {
                return false;
            }
        }
        return true;
    }
}
