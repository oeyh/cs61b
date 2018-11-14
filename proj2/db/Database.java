package db;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.princeton.cs.algs4.*;

public class Database {

    Map<String, Table> dataBase;

    // Various common constructs, simplifies parsing.
    private static final String REST  = "\\s*(.*)\\s*",
            COMMA = "\\s*,\\s*",
            AND   = "\\s+and\\s+";

    // Stage 1 syntax, contains the command name.
    private static final Pattern CREATE_CMD = Pattern.compile("create table " + REST),
            LOAD_CMD   = Pattern.compile("load " + REST),
            STORE_CMD  = Pattern.compile("store " + REST),
            DROP_CMD   = Pattern.compile("drop table " + REST),
            INSERT_CMD = Pattern.compile("insert into " + REST),
            PRINT_CMD  = Pattern.compile("print " + REST),
            SELECT_CMD = Pattern.compile("select " + REST);

    // Stage 2 syntax, contains the clauses of commands.
    private static final Pattern CREATE_NEW  = Pattern.compile("(\\S+)\\s+\\(\\s*(\\S+\\s+\\S+\\s*" +
            "(?:,\\s*\\S+\\s+\\S+\\s*)*)\\)"),
            SELECT_CLS  = Pattern.compile("([^,]+?(?:,[^,]+?)*)\\s+from\\s+" +
                    "(\\S+\\s*(?:,\\s*\\S+\\s*)*)(?:\\s+where\\s+" +
                    "([\\w\\s+\\-*/'<>=!.]+?(?:\\s+and\\s+" +
                    "[\\w\\s+\\-*/'<>=!.]+?)*))?"),
            CREATE_SEL  = Pattern.compile("(\\S+)\\s+as select\\s+" +
                    SELECT_CLS.pattern()),
            INSERT_CLS  = Pattern.compile("(\\S+)\\s+values\\s+(.+?" +
                    "\\s*(?:,\\s*.+?\\s*)*)");

    /** Constructor */
    public Database() {
        // YOUR CODE HERE
        dataBase = new HashMap<>();

    }

    /** Parse query commands and execute */
    public String transact(String query) {
        Matcher m;
        if ((m = CREATE_CMD.matcher(query)).matches()) {
            createNewTable(m.group(1));
        } else if ((m = LOAD_CMD.matcher(query)).matches()) {
            loadTable(m.group(1));
        } else if ((m = STORE_CMD.matcher(query)).matches()) {
            storeTable(m.group(1));
        } else if ((m = DROP_CMD.matcher(query)).matches()) {
            dropTable(m.group(1));
        } else if ((m = INSERT_CMD.matcher(query)).matches()) {
            insertRow(m.group(1));
        } else if ((m = PRINT_CMD.matcher(query)).matches()) {
            printTable(m.group(1));
        } else if ((m = SELECT_CMD.matcher(query)).matches()) {
            select(m.group(1));
        } else {
            System.err.printf("Malformed query: %s\n", query);
        }


        return "";
    }

    private void createNewTable(String expr) {
        Matcher m;
        if ((m = CREATE_NEW.matcher(expr)).matches()) {
            createTable(m.group(1), m.group(2).split(COMMA));
        } else if ((m = CREATE_SEL.matcher(expr)).matches()) {
            createSelectedTable(m.group(1), m.group(2), m.group(3), m.group(4));
        } else {
            System.err.printf("Malformed create: %s\n", expr);
        }
    }

    // Todo: all following methods should not be void, should return String message.
    /** Create a new table and add to database. */
    public String createTable(String tableName, String[] columnAndTypeNames) {
        // table should not exist in database and columnAndTypeNames cannot be null
        if (dataBase.containsKey(tableName) || columnAndTypeNames == null) {
            return "Fail to create table. \n";
        }

        // Parse column names and types
        Map<String, String> nameAndTypes = new LinkedHashMap<>();

        for (String columnAndType : columnAndTypeNames) {
            String[] nt = columnAndType.split(" ");
            nameAndTypes.put(nt[0], nt[1]);
        }

        // create new table with column names and types
        Table newTable = new Table(nameAndTypes);

        // Add new table to dataBase
        dataBase.put(tableName, newTable);

        return "";
    }

    /** Create a new table from select clause */
    public String createSelectedTable(String tableName, String exprs, String tables, String conds) {

        if (dataBase.containsKey(tableName)) {
            System.out.println("Fail to create table: table already exists!\n");
            return "Fail to create table: table already exists!\n";
        }
        Table newTable = select(exprs, tables, conds);
        dataBase.put(tableName, newTable);
        return "";

    }

    /** Load the table stored in the file tableFilename.tbl,
     *  create a table with the same name and add to dataBase.
     */
    public String loadTable(String tableName) {

        String tableFilename = tableName + ".tbl";
        In in;
        try {
            in = new In("examples/" + tableFilename);
        } catch (IllegalArgumentException e) {
            System.err.println("ERROR: TBL file not found: " + tableFilename);
            return "Fail to open table file. \n";
        }

        // read column names and types
        String columnsAndTypes = in.readLine();
        String[] columnAndTypeNames = columnsAndTypes.trim().split("\\s*,\\s*");

        // Parse column names and types
        Map<String, String> nameAndTypes = new LinkedHashMap<>();

        for (String columnAndType : columnAndTypeNames) {
            String[] nt = columnAndType.split(" ");
            nameAndTypes.put(nt[0], nt[1]);
        }

        // create new table with column names and types
        Table newTable = new Table(nameAndTypes);


        while (!in.isEmpty()) {
            String[] columnValues = in.readLine().split("\\s*,\\s*");
            Object[] columnValuesCast = new Object[columnValues.length];

            int k = 0;
            for (String name : nameAndTypes.keySet()) {
                if (nameAndTypes.get(name).equals("int")) {
                    columnValuesCast[k] = Integer.parseInt(columnValues[k]);
                } else if (nameAndTypes.get(name).equals("float")) {
                    columnValuesCast[k] = Float.parseFloat(columnValues[k]);
                } else {
                    columnValuesCast[k] = columnValues[k].substring(1, columnValues[k].length()-1);
                }
                k += 1;
            }
            // add row to table
            newTable.addRow(new Row(newTable, columnValuesCast));

        }

        // Add new table to dataBase
//        String tableName = tableFilename.split("\\.")[0]; // strip suffix from filename
        dataBase.put(tableName, newTable);

        return "";
    }

    /** Store the given table to file named by the table's name. */
    public String storeTable(String tableName) {
        // try-with-resource statement will auto-close the resource
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tableName + ".tbl"))) {
            writer.write(dataBase.get(tableName).toString());
            writer.close();
            return "";
        }
        catch (IOException e) {
            return "Fail to write to file. \n";
        }

    }

    /** Deletes the table with given name from database.
     * return empty string on success and error message if fails. */
    public String dropTable(String tableName) {
        if (dataBase.remove(tableName) == null) {
            return "No such table in the database. \n";
        }
        return "";
    }

    /** Inserts a row into a specified table */
    public String insertRow(String expr) {
        // additional parsing needed
        Matcher m = INSERT_CLS.matcher(expr);
        if (!m.matches()) {
            System.err.printf("Malformed insert: %s\n", expr);
            return "Fail to insert row. \n";
        }
        String tableName = m.group(1);
        String valueString = m.group(2);

        Table tbl = dataBase.get(tableName);
        Object[] columnValuesCast = new Object[tbl.totalCols];

        String[] valueArray = valueString.split("\\s*,\\s*");
        if (valueArray.length != tbl.totalCols) {
            return "Fail to insert row. \n";
        }

        int k = 0;
        for (String name : tbl.nameVsType.keySet()) {
            if (tbl.nameVsType.get(name).equals("int")) {
                columnValuesCast[k] = Integer.parseInt(valueArray[k]);
            } else if (tbl.nameVsType.get(name).equals("float")) {
                columnValuesCast[k] = Float.parseFloat(valueArray[k]);
            } else {
                columnValuesCast[k] = valueArray[k].substring(1, valueArray[k].length() - 1);
            }
            k += 1;
        }
        // add row to table
        tbl.addRow(new Row(tbl, columnValuesCast));

        return "";

    }
    /** Select methods, select columns from table/tables. */
    public String select(String expr) {
        Matcher m = SELECT_CLS.matcher(expr);
        if (!m.matches()) {
            System.err.printf("Malformed select: %s\n", expr);
            return "Fail to execute select command. \n";
        }

        select(m.group(1), m.group(2), m.group(3));
        return "";
    }

    /** Select methods, select columns from table/tables with certain condition. */
    private Table select(String exprs, String tables, String conds) {
        final Pattern COLS = Pattern.compile("\\s*\\w+(,\\s*\\w+\\s*)*");
        final Pattern COL_OPERATION = Pattern.compile("(\\w+)\\s*([-+*/])\\s*(\\w+)\\s+as\\s+(\\w+)");

        // m1 is matched when specific column names are specified in select clause
        Matcher m1 = COLS.matcher(exprs);
        // m2 is matched when column operations are specified in select clause
        Matcher m2 = COL_OPERATION.matcher(exprs);

//        Pattern CONDS = Pattern.compile("(\\s*)(\\s+and\\s+[\\w\\s+\\-*/'<>=!.]+?)*");

        // join all tables no matter what
        Table joinTable;
        String[] tableNameArray = tables.split("\\s*,\\s*");
        joinTable = dataBase.get(tableNameArray[0]);
        for (int k = 1; k < tableNameArray.length; k++) {
            joinTable = joinTable.join(dataBase.get(tableNameArray[k]));
        }

        Table returnTable;

        if (exprs.equals("*")) {
            if (conds != null) {
                System.out.println(joinTable.subTable(conds));
                returnTable = joinTable.subTable(conds);
            } else {
                System.out.println(joinTable);
                returnTable = joinTable;
            }
        } else if (m1.matches()) {
            String[] columnNameArray = exprs.split("\\s*,\\s*");
            Table selectTable = joinTable.subTable(columnNameArray);
            if (conds != null) {
                System.out.println(selectTable.subTable(conds));
                returnTable = selectTable.subTable(conds);
            } else {
                System.out.println(selectTable);
                returnTable = selectTable;
            }
        } else if (m2.matches()) {
            String[] operationColumnArray = new String[]{m2.group(1), m2.group(3)};
            char operator = m2.group(2).charAt(0);
            String newColumnName = m2.group(4);

            Table oneColumnTable = joinTable.columnOperation(operationColumnArray, operator, newColumnName);
            if (conds != null) {
                System.out.println(oneColumnTable.subTable(conds));
                returnTable = oneColumnTable.subTable(conds);
            } else {
                System.out.println(oneColumnTable);
                returnTable = oneColumnTable;
            }
        } else {
            returnTable = null;
        }


        return returnTable;
    }

    /** Prints a table given table name */
    public String printTable(String tableName) {
        Table tbl = dataBase.get(tableName);

        if (!dataBase.containsKey(tableName)) {
            return "Fail to print table.";
        }

        System.out.println(tbl);
        return "";
    }
}
