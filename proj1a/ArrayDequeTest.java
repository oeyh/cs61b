/** Performs some basic array based deque tests. */
public class ArrayDequeTest {

    /* Utility method for printing out empty checks. */
    public static boolean checkEmpty(boolean expected, boolean actual) {
        if (expected != actual) {
            System.out.println("isEmpty() returned " + actual + ", but expected: " + expected);
            return false;
        }
        return true;
    }

    /* Utility method for printing out empty checks. */
    public static boolean checkSize(int expected, int actual) {
        if (expected != actual) {
            System.out.println("size() returned " + actual + ", but expected: " + expected);
            return false;
        }
        return true;
    }
    
    /* Prints a nice message based on whether a test passed.
     * The \n means newline. */
    public static void printTestStatus(boolean passed) {
        if (passed) {
            System.out.println("Test passed!\n");
        } else {
            System.out.println("Test failed!\n");
        }
    }



    /** Adds a few things to the list, checking isEmpty() and size() are correct, 
     * finally printing the results. 
     *
     * && is the "and" operation. */
    public static void addIsEmptySizeTest() {
        System.out.println("Running add/isEmpty/Size test.");

        ArrayDeque<String> ad1 = new ArrayDeque<String>();

        boolean passed = checkEmpty(true, ad1.isEmpty());

        ad1.addFirst("front");

        // The && operator is the same as "and" in Python.
        // It's a binary operator that returns true if both arguments true, and false otherwise.
        passed = checkSize(1, ad1.size()) && passed;
        passed = checkEmpty(false, ad1.isEmpty()) && passed;

        ad1.addLast("middle");
        passed = checkSize(2, ad1.size()) && passed;

        ad1.addLast("back");
        passed = checkSize(3, ad1.size()) && passed;

        // Test integer array
        ArrayDeque<Integer> ad2 = new ArrayDeque<Integer>();

        ad2.addFirst(0);

        // The && operator is the same as "and" in Python.
        // It's a binary operator that returns true if both arguments true, and false otherwise.
        passed = checkSize(1, ad2.size()) && passed;
        passed = checkEmpty(false, ad2.isEmpty()) && passed;

        ad2.addLast(1);
        passed = checkSize(2, ad2.size()) && passed;

        ad2.addLast(2);
        passed = checkSize(3, ad2.size()) && passed;

//        System.out.println("Printing out deque: ");
//        ad1.printDeque();

        printTestStatus(passed);

    }

    /** Adds an item, then removes an item, and ensures that dll is empty afterwards. */
    public static void addRemoveTest() {

        System.out.println("Running add/remove test.");

        ArrayDeque<Integer> ad1 = new ArrayDeque<Integer>();
        // should be empty 
        boolean passed = checkEmpty(true, ad1.isEmpty());

        ad1.addFirst(10);
        ad1.addFirst(20);
        ad1.addFirst(30);
        // should not be empty 
        passed = checkEmpty(false, ad1.isEmpty()) && passed;




        int r1 = ad1.removeFirst();
        System.out.println("Removed " + r1);
        passed = checkEmpty(false, ad1.isEmpty()) && passed;
        int r2 = ad1.removeLast();
        System.out.println("Removed " + r2);
        passed = checkEmpty(false, ad1.isEmpty()) && passed;
        int r3 = ad1.removeFirst();
        System.out.println("Removed " + r3);
        // should be empty 
        passed = checkEmpty(true, ad1.isEmpty()) && passed;

        printTestStatus(passed);

    }

    /** Test get item and print item method */
    public static void getPrintTest() {
        System.out.println("Running get/print test.");

        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
        ad1.addFirst(9);
        ad1.addFirst(7);
        ad1.addFirst(5);
        ad1.addFirst(3);
        ad1.printDeque();
        System.out.println();
        boolean passed = (ad1.get(0)==3) && (ad1.get(1)==5) && (ad1.get(2)==7) && (ad1.get(3)==9);
        printTestStatus(passed);
    }
    
    public static void main(String[] args) {
        System.out.println("Running tests.\n");
        addIsEmptySizeTest();
        addRemoveTest();
        getPrintTest();
    }
}
