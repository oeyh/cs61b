public class LinkedListDeque<T> {

    /** node class that is element of the deque */
    private class Node {
        Node prev;
        T item;
        Node next;

        /** constructor 1 */
        public Node(Node p, T i, Node n) {
            prev = p;
            item = i;
            next = n;
        }

    }

    private Node sentinel;
    private int size;

    /** constructor for deque */
    public LinkedListDeque(T item) {
        size = 1;
        sentinel = new Node(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = new Node(sentinel, item, null);

    }

    /** constructor for an empty deque */
    public LinkedListDeque() {
        size = 0;
        sentinel = new Node(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;

    }

    /** Adds an item of type T to the front of the deque */
    public void addFirst(T item) {
        size += 1;
        // insert a node
        Node p = new Node(sentinel, item, sentinel.next);
        // change pointers to this node
        sentinel.next.prev = p;
        sentinel.next = p;
    }

    /** Adds an item of type T to the back of the deque */
    public void addLast(T item) {
        size += 1;
        // insert a node
        Node p = new Node(sentinel.prev, item, sentinel);
        sentinel.prev.next = p;
        sentinel.prev = p;
    }

    /** Returns true if deque is empty, false otherwise */
    public boolean isEmpty() {
        return (size == 0);
    }

    /** Returns the number of items in the deque */
    public int size() {
        return size;
    }

    /** Prints the items in the deque from first to last, separated by space */
    public void printDeque() {
        Node p = sentinel.next;
        while (p.item != null) {
            System.out.print(p.item + " ");
            p = p.next;
        }
    }

    /** Removes and returns the item at the front of the deque. If no such item
     *  exists, return null.
     */
    public T removeFirst() {
        if (size == 0) {
            return null;
        } else {
            size -= 1;
            Node p = sentinel.next;
            p.next.prev = sentinel;
            sentinel.next = p.next;
            return p.item;
        }
    }

    /** Removes and returns the item at the front of the deque. If no such item
     *  exists, return null.
     */
    public T removeLast() {
        if (size == 0) {
            return null;
        } else {
            size -= 1;
            Node p = sentinel.prev;
            p.prev.next = sentinel;
            sentinel.prev = p.prev;
            return p.item;
        }
    }

    /** Gets the item at the given index, where 0 is the front, 1 is the next item,
     *  and so forth. If no such item exists, returns null. Must not alter the deque.
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        } else {
            int k = 0;
            Node p = sentinel.next;
            while (k != index) {
                p = p.next;
                k += 1;
            }
            return p.item;
        }
    }

    /** Helper function for the below getRecursive method */
    private Node getNode(int index) {
        if (index == 0) {
            return sentinel.next;
        }
        return getNode(index - 1).next;
    }

    /** Similar to get, but uses recursion */
    public T getRecursive(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return getNode(index).item;
    }
}
