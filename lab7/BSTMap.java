import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private Node root = null;
    private int size = 0;

    private class Node {
        private K key;
        private V value;
        private Node left;
        private Node right;
        private int size;

        private Node(K k, V val) {
            this.key = k;
            this.value = val;
        }

    }

    public void clear() {
        this.root = null;
        this.size = 0;
    }

    public void printInOrder() {
        prnt(root);
    }

    private void prnt(Node curr) {
        if (curr == null) {
            return;
        }
        prnt(curr.left);
        System.out.print(curr.value);
        prnt(curr.right);
    }

    public boolean containsKey(K key) {
        return search(key, root);
    }

    private boolean search(K key, Node curr) {
        if (curr == null) {
            return false;
        }
        if (key.compareTo(curr.key) == 0) {
            return true;
        }
        if (key.compareTo(curr.key) < 0) {
            return search(key, curr.left);
        }
        if (key.compareTo(curr.key) > 0) {
            return search(key, curr.right);
        }
        return false;
    }

    public V get(K key) {
        return retrieve(key, root);
    }

    private V retrieve(K key, Node curr) {
        if (curr == null) {
            return null;
        }
        if (key.compareTo(curr.key) == 0) {
            return curr.value;
        }
        if (key.compareTo(curr.key) < 0) {
            return retrieve(key, curr.left);
        }
        if (key.compareTo(curr.key) > 0) {
            return retrieve(key, curr.right);
        }
        return null;
    }

    public int size() {
        return size;
    }

    public void put(K key, V value) {
        root = add(key, value, root);
        size += 1;
    }

    private Node add(K key, V value, Node curr) {
        if (curr == null) {
            return new Node(key, value);
        }
        if (key.compareTo(curr.key) < 0) {
            curr.left = add(key, value, curr.left);
        }
        if (key.compareTo(curr.key) >= 0) {
            curr.right = add(key, value, curr.right);
        }
        return curr;
    }

    public V remove(K key) {
        throw new UnsupportedOperationException("Invalid");
    }

    public V remove(K key, V value) {
        throw new UnsupportedOperationException("invalid");
    }

    public Set<K> keySet() {
        throw new UnsupportedOperationException("Invalid");
    }

    public Iterator<K> iterator() {
        throw new UnsupportedOperationException("Invalid");
    }
}
