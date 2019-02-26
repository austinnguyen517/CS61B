package es.datastructur.synthesizer;
import java.util.Iterator;

public class ArrayRingBuffer<T> implements BoundedQueue<T> {
    private int first;
    private int last;
    private int fillCount;
    private T[] rb;

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ArrayRingBuffer)) {
            return false;
        }
        Iterator<T> myIt = this.iterator();
        Iterator<T> thatIt = ((ArrayRingBuffer) other).iterator();
        while (myIt.hasNext() && thatIt.hasNext()) {
            if (myIt.next() != thatIt.next()) {
                return false;
            }
        }
        return !(myIt.hasNext() || thatIt.hasNext());
    }
    public int capacity() {
        return rb.length;
    }

    public int fillCount() {
        return fillCount;
    }

    public ArrayRingBuffer(int capacity) {
        first = 0;
        last = 0;
        fillCount = 0;
        rb = (T[]) new Object[capacity];
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow").
     */
    public void enqueue(T x) {
        if (fillCount() < rb.length) {
            rb[last] = x;
            last = (last + 1) % rb.length;
            fillCount += 1;
        } else {
            throw new RuntimeException("Ring Buffer overflow");
        }
    }

    public T dequeue() {
        T value = rb[first];
        if (fillCount() > 0) {
            rb[first] = null;
            fillCount -= 1;
            first = (first + 1) % rb.length;
        } else {
            throw new RuntimeException("Ring Buffer underflow");
        }
        return value;
    }

    /**
     * Return oldest item, but don't remove it. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }
        return rb[first];
    }

    public Iterator<T> iterator() {
        return new Thing();
    }

    private class Thing implements Iterator<T> {
        private int count;
        private int total;
        private int i;

        Thing() {
            count = first;
            total = fillCount;
            i = 0;
        }
        public boolean hasNext() {
            return i != total;
        }
        public T next() {
            T returnItem = rb[count];
            count = (count + 1) % rb.length;
            i += 1;
            return returnItem;
        }
    }
}
