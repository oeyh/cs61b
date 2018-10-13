/** Performs some basic array based deque tests. */
public class CircularArrayDequeTest {
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

        CircularArrayDeque<String> ad1 = new CircularArrayDeque<String>();

        boolean passed = checkEmpty(true, ad1.isEmpty());

        ad1.addLast("front");

        // The && operator is the same as "and" in Python.
        // It's a binary operator that returns true if both arguments true, and false otherwise.
        passed = checkSize(1, ad1.size()) && passed;
        passed = checkEmpty(false, ad1.isEmpty()) && passed;

        ad1.addLast("middle");
        passed = checkSize(2, ad1.size()) && passed;

        ad1.addLast("back");
        passed = checkSize(3, ad1.size()) && passed;

        // Test integer array
        CircularArrayDeque<Integer> ad2 = new CircularArrayDeque<Integer>();

        ad2.addLast(0);

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

        CircularArrayDeque<Integer> ad1 = new CircularArrayDeque<Integer>();
        // should be empty 
        boolean passed = checkEmpty(true, ad1.isEmpty());

        for (int k = 0; k < 16; k += 1) {
            ad1.addLast(k * 2);
//            ad1.addFirst(k * 2 + 1);
        }

        for (int k = 0; k < 16; k += 1) {
            ad1.addFirst(k * 2 + 1);
        }

        // test printDeque()
        ad1.printDeque();

        // should not be empty 
        passed = checkEmpty(false, ad1.isEmpty()) && passed;

        // remove first test
        for (int k = 0; k < 26; k += 1) {
            System.out.print(ad1.removeFirst() + " removed. ");
            System.out.println("Current allocated array size is " + ad1.itemSize());
        }

        // remove first test
        for (int k = 0; k < 6; k += 1) {
            System.out.print(ad1.removeLast() + " removed. ");
            System.out.println("Current allocated array size is " + ad1.itemSize());
        }


        // should be empty
        passed = checkEmpty(true, ad1.isEmpty()) && passed;

        printTestStatus(passed);

    }

    /** Test get item and print item method */
    public static void getPrintTest() {
        System.out.println("Running get/print test.");

        CircularArrayDeque<Integer> ad1 = new CircularArrayDeque<>();
        for (int k = 0; k < 9; k += 1) {
            ad1.addLast(k * 2);
//            ad1.addFirst(k * 2 + 1);
        }

        for (int k = 0; k < 20; k += 1) {
            ad1.addFirst(k * 2 + 1);
        }

        ad1.printDeque();

        for (int k = 0; k < ad1.size(); k += 1) {
            System.out.print(ad1.get(k) + " ");
        }
        System.out.println();

//        boolean passed = (ad1.get(0)==39) && (ad1.get(1)==37) && (ad1.get(2)==35) && (ad1.get(3)==33);
//        printTestStatus(passed);
    }

    public static void main(String[] args) {
        System.out.println("Running tests.\n");
        addIsEmptySizeTest();
        addRemoveTest();
        getPrintTest();
    }
}
