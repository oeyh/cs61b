package db;

import java.util.*;

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

    /** Load the table stored in the file tablefileName.tbl,
     *  create a table with the same name and add to dataBase.
     * @param tablefileName
     */
    public void load(String tablefileName) {
        
    }
}
