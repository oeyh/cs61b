package db;

import java.util.*;

/** Table class represents a table, with fixed column names and types,
 * and hold a number of Row objects.
 */
public class Table {

    Set<String> columnNames;
    List<String> columnTypes;
    LinkedList<Row> rows;  // all rows in the table form a linked list
    int totalRows;
    int totalCols;

    /** Constructor, given array of variable names. */
    public Table(String[] variableNames) {
        columnNames = new LinkedHashSet<>(Arrays.asList(variableNames)); // use linkedhashset because it has order
        totalRows = 0;
        totalCols = variableNames.length;
        rows = new LinkedList<>();
    }

    /** Constructor, given set of variable names. */
    public Table(Set<String> variableNames) {
        columnNames = new LinkedHashSet<>(variableNames); // use linkedhashset because it has order
        totalRows = 0;
        totalCols = variableNames.size();
        rows = new LinkedList<>();
    }

    /** Constructor, given set of variable names. */
    public Table(Set<String> variableNames, List<String> variableTypes) {
        columnNames = new LinkedHashSet<>(variableNames); // use linkedhashset because it has order
        columnTypes = new ArrayList<>(variableTypes);
        totalRows = 0;
        totalCols = variableNames.size();
        rows = new LinkedList<>();
    }

    /** Add a row to the end of the table */
    public void addRow(Row rowData) {
        rows.add(rowData);
        totalRows += 1;
    }

    /** Joins two tables following the rule of natural inner join.
     *  Returns a new table.
     */
    public Table join(Table anotherTable) {
        // Arrange variables for the combined table.
        // Common variables, then variables from this, then var from anotherTable
        Set<String> commonVar = new LinkedHashSet<>();
        Set<String> allVar = new LinkedHashSet<>();
        for (String name : columnNames) {
            if (anotherTable.columnNames.contains(name)) {
                commonVar.add(name);
                allVar.add(name);
            }
        }
        // Now only common variables are in allVar.
        // Add the rest of variables in order
        allVar.addAll(columnNames);
        allVar.addAll(anotherTable.columnNames);

        // If no common variables, should return Cartesian Product of the 2 tables
        if (commonVar.size() == 0) {
            return cartesianProduct(anotherTable);
        }
        // Otherwise, try to merge the two tables
        // Create a new table with all variables
        Table returnTable = new Table(allVar);

        // Merge all rows
        for (Row r1 : rows) {
            for (Row r2 : anotherTable.rows) {
                Row mergedRow = r1.MergeRows(r2, commonVar, allVar);
                if (mergedRow != null) {
                    returnTable.addRow(mergedRow);
                }
            }
        }

        return returnTable;
    }

    /** Forms the Cartesian Product of this table and another table,
     *  and return the new table.
     */
    public Table cartesianProduct(Table anotherTable) {
        // create new Table
        Set<String> allColumnNames = new LinkedHashSet<>(columnNames);
        allColumnNames.addAll(anotherTable.columnNames);
        Table returnTable = new Table(allColumnNames);

        // merge rows and add to returnTable
        for (Row r1 : rows) {
            for (Row r2: anotherTable.rows) {
                Row rowToAdd = r1.MergeIndependentRows(r2, allColumnNames);
                returnTable.addRow(rowToAdd);
            }
        }

        return returnTable;
    }

}
