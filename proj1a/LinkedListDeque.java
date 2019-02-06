public class LinkedListDeque<T> {
    /* Class definition of LinkedListDeque*/

    private Node sentFirst;
    private int size;

    public LinkedListDeque() {
        /*Constructor for LinkedListDeque Class*/
        /*Makes two sentinels referencing each other*/
        sentFirst = new Node(null, null, null);
        Node sentLast = new Node(null, sentFirst, sentFirst);
        sentFirst.next = sentLast;
        sentFirst.prev = sentLast;
    }
    public class Node {
        private T item;
        private Node prev;
        private Node next;

        public Node(T val, Node before, Node after) {
            item = val;
            prev = before;
            next = after;
        }
    }

    public boolean isEmpty() {
        /*Returns true if there are no nodes between the two sentinels*/
        return sentFirst.next == sentFirst.prev;
    }

    public int size() {
        /*Returns the number of nodes in between the two sentinels*/
        return this.size;
    }

    public void addFirst(T item) {
        /*Adds a Node containing item of type T at beginning of LL*/
        Node addThis = new Node(item, sentFirst, sentFirst.next);
        sentFirst.next = addThis;
        addThis.next.prev = addThis;
        size += 1;
    }

    public void addLast(T item) {
        /*Adds a Node containing item of type T at end of LL*/
        Node addThis = new Node(item, sentFirst.prev.prev, sentFirst.prev);
        sentFirst.prev.prev.next = addThis;
        addThis.next.prev = addThis;
        size += 1;
    }

    public LinkedListDeque(LinkedListDeque other) {
        /*Creates a deep copy of other...making a new LinkedListDeque*/
        /*Get the head of "other"...REFERNCE TO THE OBJECT...head and curr*/
        /*Use methods we have defined to recreate the other*/
        /*THe stopping condition shoudl be while head != curr */

        /*EMPTY LIST CONSTRUCTOR*/
        sentFirst = new Node(null, null, null);
        Node sentLast = new Node(null, sentFirst, sentFirst);
        sentFirst.next = sentLast;
        sentFirst.prev = sentLast;

        Node curr = other.sentinel();
        Node last = curr.prev;
        curr = curr.next;

        while (curr != last) {
            addLast(curr.item);
            curr = curr.next;
        }
    }

    public T getRecursive(int index) {
        /*Gets the ith object T using recursion*/
        return this.recurse(sentFirst.next, sentFirst.prev, index);
    }

    private T recurse(Node curr, Node lastsent, int i) {
        /*Recurses with these condition finding the ith object T*/
        /*Curr: current node traversing
        Last_sent: last_sent...stopping condition
        i: index number*/
        if (curr == lastsent) {
            return null;
        }
        if (i == 0) {
            return curr.item;
        } else {
            return recurse(curr.next, lastsent, i - 1);
        }
    }

    private Node sentinel() {
        return sentFirst;
    }

    public void printDeque() {
        Node curr = sentFirst.next;
        Node sentLast = sentFirst.prev;
        String res = "";
        while (curr != sentLast) {
            res += String.valueOf(curr.item) + " ";
            curr = curr.next;
        }
        System.out.print(res);
    }

    public T removeFirst() {
        /*Removes all references to first node, delete from LL*/
        if (this.isEmpty()) {
            return null;
        }
        Node temp = sentFirst.next;
        sentFirst.next = temp.next;
        sentFirst.next.prev = sentFirst;
        size -= 1;
        return temp.item;
    }

    public T removeLast() {
        /*Removes all references to the last node, delete from LL*/
        if (this.isEmpty()) {
            return null;
        }
        Node temp = sentFirst.prev.prev;
        temp.prev.next = sentFirst.prev;
        sentFirst.prev.prev = temp.prev;
        size -= 1;
        return temp.item;
    }

    public T get(int index) {
        Node curr = sentFirst.next;
        Node lastSent = sentFirst.prev;
        while (index != 0) {
            if (lastSent == curr) {
                return null;
            }
            curr = curr.next;
            index -= 1;
        }

        return curr.item;
    }
}
