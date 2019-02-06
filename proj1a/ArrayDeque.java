/**Array Deque Class**/
public class ArrayDeque<T> {

    /*Size: # of elements in the array, first: index+1 % entire array representing head of array*/
    /*last: index%entire array representing the tail of array*/
    /*length is the size of the entire array*/
    /*items is the array of items*/
    private int size;
    private int first;
    private int last;
    private int length;
    private T[] items;

    public ArrayDeque() {
        /*Creates an empty arraydeque*/
        /*For simplicity, makes the first and last pointers both 0*/
        first = 0;
        last = 0;
        items = (T[]) new Object[8];
        size = 0;
        length = items.length;
    }

    public boolean isEmpty() {
        /*Returns true if the arraydeque is empty. Returns false otherwise*/
        return size() == 0;
    }

    public int size() {
        /*Returns the size of our arraydeque*/
        return this.size;
    }

    public void addFirst(T item) {
        /*Adds an element to the beginning of the list*/
        /*This adds the element at start MINUS ONE
        since start keeps track of the first element filled*/
        if (first == last && !this.isEmpty()) {
            sizeUp();
        }
        if (first == 0) {
            first = length;
        }
        first -= 1;
        items[first] = item;
        size += 1;
    }

    public void addLast(T item) {
        if (first == last && !this.isEmpty()) {
            sizeUp();
        }
        items[last] = item;
        if (last == length - 1) {
            last = -1;
        }
        last += 1;
        size += 1;
    }

    private void sizeUp() {
        /*Resizes items by a factor of 2 and copies over the elements from items*/
        /*Resets all the elements to start at index 0*/
        T[] larger = (T[]) new Object[length * 2];
        System.arraycopy(items, first, larger, 0, length - first);
        System.arraycopy(items, 0, larger, length - first, first);
        first = 0;
        last = length;
        length = larger.length;
        items = larger;
    }

    public ArrayDeque(ArrayDeque other) {
        /*Creates a copy of other into this arraydeque*/
        length = other.length;
        T[] copy = (T[]) new Object[length];
        this.items = copy;
        if (other.last > other.first) {
            System.arraycopy(other.items, other.first, items, 0, other.last - other.first);
        } else if (!other.isEmpty()) {
            System.arraycopy(other.items, other.first, items, 0, other.length - other.first);
            System.arraycopy(other.items, 0, items, length - other.first, other.last);
        }
        size = other.size;
        first = 0;
        last = other.size;
    }


    public void printDeque() {
        /*Prints the entire array deque starting at first and ending at last*/
        int i = first;
        String res = "";
        while (i % length != last) {
            res += String.valueOf(items[i % length]) + " ";
            i += 1;
        }
        System.out.println(res);
    }

    public T removeFirst() {
        /*Removes the first element from the deque*/
        if (isEmpty()) {
            return null;
        }
        T temp = items[first];
        items[first] = null;
        first = (first + 1) % length;
        size -= 1;
        if (((double) size / length) < .25 && length >= 16) {
            sizeDown();
        }
        return temp;
    }

    public T removeLast() {
        /*Removes the last element from the deque*/
        if (isEmpty()) {
            return null;
        }
        T temp;
        last = ((last - 1) + length) % length;
        items[last] = null;
        temp = items[last];
        size -= 1;
        if (((double) size / length) < .25 && length >= 16) {
            sizeDown();
        }
        return temp;
    }

    private void sizeDown() {
        T[] smaller = (T[]) new Object[length / 2];
        /*We NEED to make two cases for this due to the fact that some of the elements may be null*/
        if (last > first) {
            System.arraycopy(items, first, smaller, 0, last - first);
        } else if (!isEmpty()) {
            System.arraycopy(items, first, smaller, 0, length - first);
            System.arraycopy(items, 0, smaller, length - first, last);
        }
        first = 0;
        last = size;
        length = smaller.length;
        items = smaller;
    }

    public T get(int index) {
        /*Gets the element at the place index...with respect to first's index*/
        int newInd = (first + index) % items.length;
        if (isEmpty()) {
            return null;
        }
        if ((last > first && !(newInd >= first && newInd < last)) ||
                (first > last && (newInd < first && newInd >= last))) {
            /*If last is larger than first and the new
            index is not between it, it is not in the deque*/
            /* or if first is larger than last and the new
            index IS in between the two nodes */
            return null;
        }

        return items[newInd];
    }
}