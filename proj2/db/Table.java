package db;

import java.util.*;

/** Table class represents a table, with fixed column names and types,
 * and hold a number of Row objects.
 */
public class Table {

    Set<String> columnNames;
    List<String> columnTypes;
    List<Row> rows;  // all rows in the table form a linked list
    Map<String, String> nameVsType;
    int totalRows;
    int totalCols;

    /** Constructor, given array of variable names. */
    public Table(String[] variableNames, String[] variableTypes) {
        columnNames = new LinkedHashSet<>(Arrays.asList(variableNames)); // use linkedhashset because it has order
        columnTypes = new ArrayList<>(Arrays.asList(variableTypes));
        totalRows = 0;
        totalCols = variableNames.length;
        rows = new LinkedList<>();

        nameVsType = new HashMap<>();
        int k = 0;
        for (String name : columnNames) {
            nameVsType.put(name, columnTypes.get(k));
            k += 1;
        }
    }

//    /** Constructor, given set of variable names. */
//    public Table(Set<String> variableNames, List<String> variableTypes) {
//        columnNames = new LinkedHashSet<>(variableNames); // use linkedhashset because it has order
//        columnTypes = new ArrayList<>(variableTypes);
//        totalRows = 0;
//        totalCols = variableNames.size();
//        rows = new LinkedList<>();
//    }

    /** Constructor, given set of variable names. */
    public Table(Set<String> variableNames, List<String> variableTypes) {
        columnNames = new LinkedHashSet<>(variableNames); // use linkedhashset because it has order
        columnTypes = new ArrayList<>(variableTypes);
        totalRows = 0;
        totalCols = variableNames.size();
        rows = new LinkedList<>();

        nameVsType = new HashMap<>();
        int k = 0;
        for (String name : columnNames) {
            nameVsType.put(name, columnTypes.get(k));
            k += 1;
        }
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
        List<String> commonVarTypes = new ArrayList<>();
        Set<String> allVar = new LinkedHashSet<>();
        List<String> allVarTypes = new ArrayList<>();

        for (String name : columnNames) {
            if (anotherTable.columnNames.contains(name)) {
                commonVar.add(name);
                commonVarTypes.add(nameVsType.get(name));
                allVar.add(name);
            }
        }
        // Now only common variables are in allVar.
        // Add the rest of variables in order
        allVar.addAll(columnNames);
        allVar.addAll(anotherTable.columnNames);

        // add variable types
        for (String name : allVar) {
            if (nameVsType.get(name) == null) {
                allVarTypes.add(anotherTable.nameVsType.get(name));
            } else {
                allVarTypes.add(nameVsType.get(name));
            }
        }

        // If no common variables, should return Cartesian Product of the 2 tables
        if (commonVar.size() == 0) {
            return cartesianProduct(anotherTable);
        }
        // Otherwise, try to merge the two tables
        // Create a new table with all variables
        Table returnTable = new Table(allVar, allVarTypes);

        // Merge all rows
        for (Row r1 : rows) {
            for (Row r2 : anotherTable.rows) {
                Row mergedRow = r1.MergeRows(r2, commonVar, commonVarTypes, allVar, allVarTypes);
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
        List<String> allColumnTypes = new ArrayList<>(columnTypes);
        allColumnNames.addAll(anotherTable.columnNames);
        allColumnTypes.addAll(anotherTable.columnTypes);

        Table returnTable = new Table(allColumnNames, allColumnTypes);

        // merge rows and add to returnTable
        for (Row r1 : rows) {
            for (Row r2: anotherTable.rows) {
                Row rowToAdd = r1.MergeIndependentRows(r2, allColumnNames, allColumnTypes);
                returnTable.addRow(rowToAdd);
            }
        }

        return returnTable;
    }

    /** Returns a string presenting the table object, can be used to print the table or write to file */
    @Override
    public String toString() {
        // combine column names and types
        String[] columnNamesAndTypes = new String[columnNames.size()];
        int k = 0;
        for (String name : columnNames) {
            columnNamesAndTypes[k] = (name + " " + columnTypes.get(k));
            k += 1;
        }

        String returnString = String.join(",", columnNamesAndTypes);
        returnString += "\n";

        for (Row r : rows) {
            returnString += r.toString();
        }
        return returnString;
    }
}
