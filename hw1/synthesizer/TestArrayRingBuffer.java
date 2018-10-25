package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug, Hai Yan
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer<Double> arb = new ArrayRingBuffer<>(4);
        assertTrue(arb.isEmpty());
        arb.enqueue(9.3);
        arb.enqueue(15.1);
        arb.enqueue(31.2);

        assertFalse(arb.isFull());

        arb.enqueue(-3.1);
        assertTrue(arb.isFull());

        arb.dequeue();
        assertEquals((Double) 15.1, arb.peek());

        // test iterator
        for (Double element : arb) {
            System.out.print(element + " ");
        }
        System.out.println();

        arb.enqueue(1.0);

        for (Double element : arb) {
            System.out.print(element + " ");
        }
        System.out.println();

        // test nested iterations
        int[] someInts = new int[]{1, 2, 3};
        for (int x : someInts) {
            for (int y: someInts) {
                System.out.println("x: " + x +  ", y:" + y);
            }
        }

    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
