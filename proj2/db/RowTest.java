package db;

import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class RowTest {

    @Test
    public void testToString() {

        Database db = new Database();
        db.loadTable("teams");
        Table t1 = db.dataBase.get("teams");
        Row r1 = t1.rows.get(4);
        System.out.println(r1);
    }
}
