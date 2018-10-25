package db;

import java.util.*;
import edu.princeton.cs.algs4.*;

public class Database {

    Map<String, Table> dataBase;

    /** Constructor */
    public Database() {
        // YOUR CODE HERE
        dataBase = new HashMap<>();
    }

    /** Parse query commands and execute */
    public String transact(String query) {
        return "YOUR CODE HERE";
    }

    /** Create a new table and add to database. */
    public void createTable(String tableName, String[] columnAndTypeNames) {
        // Parse column names and types
        Set<String> columnNames = new LinkedHashSet<>();
        List<String> columnTypes = new ArrayList<>();

        for (String columnAndType : columnAndTypeNames) {
            String[] nt = columnAndType.split(" ");
            columnNames.add(nt[0]);
            columnTypes.add(nt[1]);
        }

        // create new table with column names and types
        Table newTable = new Table(columnNames, columnTypes);

        // Add new table to dataBase
        dataBase.put(tableName, newTable);
    }

    /** Load the table stored in the file tableFilename.tbl,
     *  create a table with the same name and add to dataBase.
     * @param tableFilename
     */
    public void loadTable(String tableFilename) {
        In in = new In("examples/" + tableFilename);
//        In in = new In(tableFilename);

        // read column names and types
        String columnsAndTypes = in.readLine();
        String[] columnAndTypeNames = columnsAndTypes.trim().split("\\s*,\\s*");

        // Parse column names and types
        // TODO: make this a helper method, maybe a static method in Table class
        Set<String> columnNames = new LinkedHashSet<>();
        List<String> columnTypes = new ArrayList<>();

        for (String columnAndType : columnAndTypeNames) {
            String[] nt = columnAndType.split(" ");
            columnNames.add(nt[0]);
            columnTypes.add(nt[1]);
        }

        // create new table with column names and types
        Table newTable = new Table(columnNames, columnTypes);


        while (!in.isEmpty()) {
            String[] columnValues = in.readLine().split("\\s*,\\s*");
            // TODO: casting to int, float or keep as string
            Object[] columnValuesCast = new Object[columnValues.length];
            int k = 0;
            for (String value : columnValues) {
                if (columnTypes.get(k).equals("int")) {
                    columnValuesCast[k] = Integer.parseInt(value);
                } else if (columnTypes.get(k).equals("float")) {
                    columnValuesCast[k] = Float.parseFloat(value);
                } else {
                    columnValuesCast[k] = value;
                }
                k += 1;
            }
            // add row to table
            newTable.addRow(new Row(newTable, columnValuesCast));

        }

        // Add new table to dataBase
        String tableName = tableFilename.split("\\.")[0]; // strip suffix from filename
        dataBase.put(tableName, newTable);
    }
}
