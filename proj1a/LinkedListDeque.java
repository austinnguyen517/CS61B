public class LinkedListDeque<T> {
    /* Class definition of LinkedListDeque*/

    private Node sent_first;
    private int size;

    public LinkedListDeque(){
        /*Constructor for LinkedListDeque Class*/
        /*Makes two sentinels referencing each other*/
        sent_first = new Node(null, null, null);
        Node sent_last = new Node(null, sent_first, sent_first);
        sent_first.next = sent_last;
        sent_first.prev = sent_last;
    }
    public class Node {
        public T item;
        public Node prev;
        public Node next;

        public Node(T val, Node before, Node after){
            item = val;
            prev = before;
            next = after;
        }
    }

    public boolean isEmpty(){
        /*Returns true if there are no nodes between the two sentinels*/
        return sent_first.next == sent_first.prev;
    }

    public int size() {
        /*Returns the number of nodes in between the two sentinels*/
        return this.size;
    }

    public void addFirst(T item){
        /*Adds a Node containing item of type T at beginning of LL*/
        Node add_this = new Node(item, sent_first, sent_first.next);
        sent_first.next = add_this;
        add_this.next.prev = add_this;
        size += 1;
    }

    public void addLast (T item){
        /*Adds a Node containing item of type T at end of LL*/
        Node add_this = new Node(item, sent_first.prev.prev, sent_first.prev);
        sent_first.prev.prev.next = add_this;
        add_this.next.prev = add_this;
        size += 1;
    }

    public LinkedListDeque(LinkedListDeque other){
        /*Creates a deep copy of other...making a new LinkedListDeque*/
        /*Get the head of "other"...REFERNCE TO THE OBJECT...head and curr*/
        /*Use methods we have defined to recreate the other*/
        /*THe stopping condition shoudl be while head != curr */

        /*EMPTY LIST CONSTRUCTOR*/
        sent_first = new Node(null, null, null);
        Node sent_last = new Node(null, sent_first, sent_first);
        sent_first.next = sent_last;
        sent_first.prev = sent_last;

        Node curr = other.Sentinel();
        Node last = curr.prev;
        curr = curr.next;

        while (curr != last){
            addLast(curr.item);
            curr = curr.next;
        }
    }

    public T getRecursive(int index){
        /*Gets the ith object T using recursion*/
        return this.Recurse(sent_first.next, sent_first.prev, index);
    }

    private T Recurse (Node curr, Node last_sent, int i){
        /*Recurses with these condition finding the ith object T*/
        /*Curr: current node traversing, Last_sent: last_sent...stopping condition, i: index number*/
        if (curr == last_sent){
            return null;
        } if (i == 0){
            return curr.item;
        } else {
            return Recurse(curr.next, last_sent, i - 1);
        }
    }

    private Node Sentinel (){
        return sent_first;
    }

    public void printDeque(){
        Node curr = sent_first.next;
        Node sent_last = sent_first.prev;
        String res = "";
        while (curr != sent_last){
            res += String.valueOf(curr.item) + " ";
            curr = curr.next;
        }
        System.out.print(res);
    }

    public T removeFirst(){
        /*Removes all references to first node, delete from LL*/
        if (this.isEmpty()){
            return null;
        }
        Node temp = sent_first.next;
        sent_first.next = temp.next;
        sent_first.next.prev = sent_first;
        size -= 1;
        return temp.item;
    }

    public T removeLast(){
        /*Removes all references to the last node, delete from LL*/
        if (this.isEmpty()){
            return null;
        }
        Node temp = sent_first.prev.prev;
        temp.prev.next = sent_first.prev;
        sent_first.prev.prev = temp.prev;
        size -= 1;
        return temp.item;
    }

    public T get(int index){
        Node curr = sent_first.next;
        Node last_sent = sent_first.prev;
        while (index != 0) {
            if (last_sent == curr){
                return null;
            }
            curr = curr.next;
            index -= 1;
        }

        return curr.item;
    }
}
