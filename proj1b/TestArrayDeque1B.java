import static org.junit.Assert.*;
import org.junit.Test;

/** Unit test for ArrayDeque */
public class TestArrayDeque1B {

    @Test
    public void testDeque() {
        StudentArrayDeque<Integer> sad1 = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> ads1 = new ArrayDequeSolution<>();

        // Tracking operation sequence
        OperationSequence fs = new OperationSequence();
        DequeOperation dequeOp1 = new DequeOperation("ArrayDequeSolution");
        fs.addOperation(dequeOp1);

        // Test add methods
        for (int i = 0; i < 50; i += 1) {
            double numberBetweenZeroAndOne = StdRandom.uniform();
            // generate random number uniformly in range [0, 100)
            int randomInt = StdRandom.uniform(100);

            if (numberBetweenZeroAndOne < 0.5) {
                ads1.addLast(randomInt);
                sad1.addLast(randomInt);
                dequeOp1 = new DequeOperation("addLast", randomInt);
                fs.addOperation(dequeOp1);

            } else {
                ads1.addFirst(randomInt);
                sad1.addFirst(randomInt);
                dequeOp1 = new DequeOperation("addFirst", randomInt);
                fs.addOperation(dequeOp1);

            }
        }

//        ads1.printDeque();
        sad1.printDeque();
//        System.out.println(fs.toString());

        for (int k = 0; k < 40; k += 1) {
            dequeOp1 = new DequeOperation("get", k);
            fs.addOperation(dequeOp1);
            assertEquals(fs.toString(), ads1.get(k), sad1.get(k));

        }

        // Test remove methods
        for (int i = 0; i < 40; i += 1) {
            double numberBetweenZeroAndOne = StdRandom.uniform();


            if (numberBetweenZeroAndOne < 0.5) {
                Integer removedFromSolution = ads1.removeLast();
                Integer removedFromStudent = sad1.removeLast();
                dequeOp1 = new DequeOperation("removeLast");
                fs.addOperation(dequeOp1);
                assertEquals(fs.toString(), removedFromSolution, removedFromStudent);

            } else {
                Integer removedFromSolution = ads1.removeFirst();
                Integer removedFromStudent = sad1.removeFirst();
                dequeOp1 = new DequeOperation("removeFirst");
                fs.addOperation(dequeOp1);
                assertEquals(fs.toString(), removedFromSolution, removedFromStudent);
            }
        }

    }
}