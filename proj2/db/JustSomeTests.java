package db;

import java.util.*;
import org.junit.Test;

public class JustSomeTests {

    /** Test String methods */
    @Test
    public void stringTest() {
        Set<String> set1 = new LinkedHashSet<>();
        set1.add("Hello");
        set1.add("World");
        set1.add("Test");

        String jointest = String.join(",", set1);
        System.out.println(jointest);
    }

//    /** Test try-catch on comparison between int, float and String */
//    @Test
//    public void compTest() {
//        int i = 0;
//        float j = 1.0;
//        String
//    }

    @Test
    public void testTest() {
        Integer i = 5;
        Float j = (float) i;
    }


    public static void main(String[] args) {
        HashMap<String, Object> map1 = new HashMap<>();
        HashMap<String, Object> map2 = new HashMap<>();
        map1.put("X int", 2);
        map1.put("Y int", 5);
        map2.put("X int", 3);
        map2.put("Z int", 4);

        HashMap<String, Object> map3 = new HashMap<>(map1);
        map3.putAll(map2);
        System.out.println(map1);
        System.out.println(map2);
        System.out.println(map3);


        // TableProto test
        HashSet<String> columns1 = new HashSet();
        HashSet<String> columns2 = new HashSet();
        columns1.add("X int");
        columns1.add("Y int");
        columns2.add("X int");
        columns2.add("Z int");
        TableProto t1 = new TableProto(columns1);
        TableProto t2 = new TableProto(columns2);

        HashMap<String, Object> h10 = new HashMap<>();
        HashMap<String, Object> h11 = new HashMap<>();
        HashMap<String, Object> h12 = new HashMap<>();
        h10.put("X int", 2);
        h10.put("Y int", 5);
        h11.put("X int", 8);
        h11.put("Y int", 3);
        h12.put("X int", 13);
        h12.put("Y int", 7);
        t1.addRow(h10);
        t1.addRow(h11);
        t1.addRow(h12);


        HashMap<String, Object> h20 = new HashMap<>();
        HashMap<String, Object> h21 = new HashMap<>();
        HashMap<String, Object> h22 = new HashMap<>();
        HashMap<String, Object> h23 = new HashMap<>();
        h20.put("X int", 2);
        h20.put("Z int", 4);
        h21.put("X int", 8);
        h21.put("Z int", 9);
        h22.put("X int", 10);
        h22.put("Z int", 1);
        h23.put("X int", 11);
        h23.put("Z int", 1);
        t2.addRow(h20);
        t2.addRow(h21);
        t2.addRow(h22);
        t2.addRow(h23);

        t1.printTable();
        t2.printTable();

        TableProto t3 = t1.join(t2);
        t3.printTable();

    }
}
