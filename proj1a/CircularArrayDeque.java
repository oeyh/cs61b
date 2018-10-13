/** Implementation of Deque using array as core data structure */
public class CircularArrayDeque<T> {

    private T[] items;
    private int nextFirst;
    private int nextLast;
    private int size;
    private static final int RESIZE_FACTOR = 2;

    /** constructor for an empty deque */
    public CircularArrayDeque() {
        size = 0;
        items = (T []) new Object[8];
        // nextFirst points to the next addFirst position, and indexIncr(nextFirst) always points to the current first item
        // nextLast points to the next addLast position, and indexDecr(nextLast) always points to the current last item
        nextFirst = 4;
        nextLast = 5;
    }

    /** Helper method to resize the array to given capacity */
    private void resize(int capacity) {
        T[] a = (T []) new Object[capacity];
        // copy items to a, starting at capacity / 4
        int j = 0;
        int k = indexIncr(nextFirst);
        int m = capacity / 4; // position of first item in the new array a
        while (j < size) {
            a[m] = items[k];
            m += 1; // assuming m will not be bigger than length of a
            k = indexIncr(k);
            j += 1;
        }

        // update nextFirst and nextLast
        nextFirst = capacity / 4 - 1;
        nextLast = capacity / 4 + size;
        items = a;
    }

    /** Helper method to increment index */
    private int indexIncr(int index) {
        if (index < (items.length - 1)) {
            return index + 1;
        }
        return 0;
    }

    /** Helper method to decrement index */
    private int indexDecr(int index) {
        if (index == 0) {
            return items.length - 1;
        }
        return index - 1;
    }

    /** Test utility method to return length of items (allocated array size) */
    public int itemSize() {
        return items.length;
    }

    /** Adds an item of type T to the front of the deque */
    public void addFirst(T i) {
        if (size == items.length) {
            resize(size * RESIZE_FACTOR);
        }
        items[nextFirst] = i;
        size += 1;
        nextFirst = indexDecr(nextFirst);
    }

    /** Adds an item of type T to the back of the deque */
    public void addLast(T i) {
        if (size == items.length) {
            resize(size * RESIZE_FACTOR);
        }
        items[nextLast] = i;
        size += 1;
        nextLast = indexIncr(nextLast);
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
        int j = indexIncr(nextFirst);
        while (k < size) {
            System.out.print(items[j] + " ");
            k += 1;
            j = indexIncr(j);
        }
        System.out.println();
    }

    /** Removes and returns the item at the front of the deque. If no such item
     *  exists, return null.
     */
    public T removeFirst() {
        if (size == 0) {
            return null;
        } else {
            nextFirst = indexIncr(nextFirst);
            T result = items[nextFirst];
            items[nextFirst] = null; // null out references
            size -= 1;

            // check usage and shrink if necessary
            double usage = size / (double)items.length;
            if (items.length >= 16 && usage < 0.25) {
                resize(items.length / RESIZE_FACTOR);
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
            nextLast = indexDecr(nextLast);
            T result = items[nextLast];
            items[nextLast] = null; // null out references
            size -= 1;

            // check usage and shrink if necessary
            double usage = size / (double)items.length;
            if (items.length >= 16 && usage < 0.25) {
                resize(items.length / RESIZE_FACTOR);
            }
            return result;
        }
    }

    /** Gets the item at the given index, where 0 is the front, 1 is the next item,
     *  and so forth. If no such item exists, returns null. Must not alter the deque.
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        } else {
            int first = indexIncr(nextFirst);
            if (first + index > items.length - 1) {
                return items[first + index - items.length];
            }
            return items[first + index];
        }
    }
}
