package db;

import org.junit.Test;

public class DatabaseTest {

    /** Test loadTable method */
    @Test
    public void loadTest() {
        Database db2 = new Database();
        db2.loadTable("t1.tbl");
        db2.loadTable("t2.tbl");
        db2.loadTable("t4.tbl");
        Table t1 = db2.dataBase.get("t1");
        Table t2 = db2.dataBase.get("t2");
        Table t4 = db2.dataBase.get("t4");

        System.out.println("t1: ");
        System.out.println(t1);
        System.out.println("t2: ");
        System.out.println(t2);
        System.out.println("t4: ");
        System.out.println(t4);

        // Test join
        Table t3 = t1.join(t2);
        System.out.println(t3);
        Table t5 = t3.join(t4);
        System.out.println(t5);

//        for (Row r : t1.rows) {
//            for (String col : t1.columnNames) {
//                System.out.print(r.rowData.get(col) + ",");
//            }
//
//            System.out.println();
//        }


    }


    public static void main(String[] args) {

        Database db1 = new Database();

        String tableName = "myFirstTable";
        String columnsAndTypes = "X int, Y int, Z int";
        String[] ct = columnsAndTypes.trim().split("\\s*,\\s*");

        db1.createTable(tableName, ct);

        Database db2 = new Database();
        db2.loadTable("t1.tbl");
    }
}
