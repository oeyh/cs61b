package synthesizer;

public abstract class AbstractBoundedQueue<T> implements BoundedQueue<T> {

    protected int capacity;
    protected int fillCount;

    /** return size of the buffer */
    @Override
    public int capacity() {
        return capacity;
    }

    /** return number of items currently in the buffer */
    @Override
    public int fillCount() {
        return fillCount;
    }

    /** The following methods are inherited from BoundedQueue interface and
     * do not need an explicit declaration.
     */
//    /** return true if fillCount is zero */
//    public boolean isEmpty();
//
//    /** return true if fillCount equals to capacity */
//    public boolean isFull();
//
//    /** return the item at the front */
//    public abstract T peek();
//
//    /** remove and return the item at the front */
//    public abstract T dequeue();
//
//    /** add item to the back */
//    public abstract void enqueue(T x);
}
