/** Implementation of Deque using array as core data structure */
public class ArrayDeque<T> {

    private T[] items;
    private int size;
    private static final int RESIZE_FACTOR = 2;

    /** constructor for an empty deque */
    public ArrayDeque() {
        size = 0;
        items = (T []) new Object[8];
    }

    /** Helper method to resize the array for addLast to given capacity */
    private void resizeAddLast(int capacity) {
        T[] a = (T []) new Object[capacity];
        System.arraycopy(items, 0, a, 0, size);
        items = a;
    }

    /** Helper method to resize the array for addFirst */
    private void resizeAddFirst(int capacity) {
        T[] a = (T []) new Object[capacity];
        System.arraycopy(items, 0, a, 1, size);
        items = a;
    }

    /** Helper method to resize the array for removeFirst */
    private void resizeRemoveFirst(int capacity) {
        T[] a = (T []) new Object[capacity];
        System.arraycopy(items, 1, a, 0, size);
        items = a;
    }


    /** Adds an item of type T to the front of the deque */
    public void addFirst(T i) {
        if (size == items.length) {
            resizeAddFirst(size * RESIZE_FACTOR);
        } else {
            resizeAddFirst(items.length);
        }
        items[0] = i;
        size += 1;
    }

    /** Adds an item of type T to the back of the deque */
    public void addLast(T i) {
        if (size == items.length) {
            resizeAddLast(size * RESIZE_FACTOR);
        }
        items[size] = i;
        size += 1;
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
        int k = 0;
        while (k < size) {
            System.out.print(items[k] + " ");
            k += 1;
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
            T result = items[0];
            // remove first item
            resizeRemoveFirst(items.length);
            // check usage and resize if necessary
            double usage = items.length / (double)size;
            while (items.length >= 16 && usage < 0.25) {
                resizeAddLast(items.length / 2);
            }
            return result;
        }
    }

    /** Removes and returns the item at the front of the deque. If no such item
     *  exists, return null.
     */
    public T removeLast() {
        // check if array is already empty
        if (size == 0) {
            return null;
        } else {
            // null out the item to be deleted to save memory
            items[size] = null;

            size -= 1;
            // check usage and resize if necessary
            double usage = items.length / (double)size;
            while (items.length >= 16 && usage < 0.25) {
                resizeAddLast(items.length / 2);
            }
            return items[size];
        }
    }

    /** Gets the item at the given index, where 0 is the front, 1 is the next item,
     *  and so forth. If no such item exists, returns null. Must not alter the deque.
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        } else {
            return items[index];
        }
    }

}
