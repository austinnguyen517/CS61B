package bearmaps.proj2ab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {

    private class Node {
        T value;
        double prior;
        int place;

        private Node(T thing, double pr) {
            value = thing;
            prior = pr;
        }
    }
    private ArrayList<Node> items = new ArrayList<>();
    private HashMap<T, Node> listValues = new HashMap<>();
    private int size = 0;

    private int leftChild(int k) {
        return 2 * k + 1;
    }
    private int rightChild(int k) {
        return 2 * k + 2;
    }
    private int parent(int k) {
        return (k - 1) / 2;
    }
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException("Item already in list");
        }

        Node thing = new Node(item, priority);
        listValues.put(item, thing);
        /*Naively add it into the PQ*/
        items.add(thing);
        size += 1;
        thing.place = size - 1;
        /*Check if its parent has a larger priority. If it does, then change the PQ*/
        swimUp(size - 1);
    }
    private void swimUp(int k) {
        /*Check if its parents exist*/
        /*If they do exist, check parent's priority. If priority is lower, stay where you are*/
        /*Otherwise, swap the places of current and the parent*/
        /*Then, make the new parent swim up*/
        if (parent(k) != k && items.get(parent(k)).prior > items.get(k).prior) {
            /*swap them and swim up*/
            swap(k, parent(k));
            swimUp(parent(k));
        }
    }
    public boolean contains(T item) {
        return listValues.containsKey(item);
    }
    public T getSmallest() {
        if (size() == 0) {
            throw new NoSuchElementException("The PQ is empty");
        }
        return items.get(0).value;
    }
    public T removeSmallest() {
        /*Naively replace the top element with the last element. Size -= 1*/
        if (size() == 0) {
            throw new NoSuchElementException("The PQ is Empty");
        }
        T val = getSmallest();
        listValues.remove(getSmallest());
        items.set(0, items.get(size - 1));
        items.remove(size - 1);
        size -= 1;
        if (size >= 1) {
            items.get(0).place = 0;
        }
        if (size > 1) {
            swimDown(0);
        }
        return val;
    }
    private void swimDown(int k) {
        /*Check if this element has any children...check for out of bounds when compared to size*/
        /*Check if the priority of this top element is greater than its children*/
        /*If it is greater, then swap it with the element with the minimum priority*/
        /*Then have that new child swim down further*/
        double leftPriority = 0;
        double rightPriority = 0;
        int lftChild = leftChild(k);
        int rghtChild = rightChild(k);
        if (lftChild < size) {
            leftPriority = items.get(lftChild).prior;
        }
        if (rghtChild < size) {
            rightPriority = items.get(rghtChild).prior;
            if (items.get(k).prior > leftPriority || items.get(k).prior > rightPriority) {
                if (leftPriority < rightPriority) {
                    swap(k, lftChild);
                    swimDown(lftChild);
                } else {
                    swap(k, rghtChild);
                    swimDown(rghtChild);
                }
            }
        } else {
            if (lftChild < size) {
                if (items.get(k).prior > leftPriority) {
                    swap(k, lftChild);
                    swimDown(lftChild);
                }
            }
        }
    }
    private void swap(int k1, int k2) {
        Node temp = items.get(k1);
        items.set(k1, items.get(k2));
        items.set(k2, temp);
        items.get(k1).place = k1;
        items.get(k2).place = k2;

    }
    public int size() {
        return size;
    }
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException("Element not in PQ");
        }
        int idx = listValues.get(item).place;
        items.get(idx).prior = priority;
        swimUp(idx);
        swimDown(idx);
    }
}
