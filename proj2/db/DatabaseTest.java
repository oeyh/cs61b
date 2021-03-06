package db;

import org.junit.Test;

import java.io.IOException;
import java.util.Scanner;

public class DatabaseTest {

    /** Test at commands level */
    @Test
    public void commandTest() {
        Database db = new Database();
        db.transact("load fans");
        db.transact("load records");
        db.transact("load teams");
        db.transact("select Mascot, YearEstablished from teams where YearEstablished > 1942");
        db.transact("create table seasonRatios as select City, Season, Wins/Losses as Ratio from teams, records");
        db.transact("print seasonRatios");
    }


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

        // Test string type and float type
        db2.loadTable("float.tbl");
        Table f = db2.dataBase.get("float");
        System.out.println(f);

        db2.loadTable("fans.tbl");
        Table f2 = db2.dataBase.get("fans");
//        System.out.print(f2);
//        try {
//            db2.storeTable("fans");
//        }
//        catch (IOException e) {
//            System.out.println("IOException");
//        }
//        System.out.println(f2);
//        db2.insertRow("fans", "'Yan','Hai','Houston Rockets'");
//        db2.printTable("fans");


//        for (Row r : t1.rows) {
//            for (String col : t1.columnNames) {
//                System.out.print(r.rowData.get(col) + ",");
//            }
//
//            System.out.println();
//        }


    }

    @Test
    public void parseTest() {

        Database db3 = new Database();
        // read commandline input
        Scanner scan = new Scanner(System.in);
        String query = scan.nextLine();

        while (!query.equals("exit")) {
            db3.transact(query);
            query = scan.nextLine();
        }
    }

    @Test
    public void selectTest() {
        Database db5 = new Database();
        db5.loadTable("records");
        db5.printTable("records");
        db5.select("Wins - Losses as WLD from records");

    }

    @Test
    public void subTableTest() {
        Database db5 = new Database();
        db5.loadTable("records");
        db5.dataBase.get("records").subTable("a > b");

    }


    public static void main(String[] args) {

//        Database db1 = new Database();
//
//        String tableName = "myFirstTable";
//        String columnsAndTypes = "X int, Y int, Z int";
//        String[] ct = columnsAndTypes.trim().split("\\s*,\\s*");
//
//        db1.createTable(tableName, ct);
//
//        Database db2 = new Database();
//        db2.loadTable("t1.tbl");

        Database db3 = new Database();
        final String PROMPT = "Type your command, 'exit' to quit >>> ";
        System.out.print(PROMPT);
        // read commandline input
        Scanner scan = new Scanner(System.in);
        String query = scan.nextLine();

        while (!query.equals("exit")) {
            db3.transact(query);
            System.out.print(PROMPT);
            query = scan.nextLine();
        }
    }
}
