package db;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Table class represents a table, with fixed column names and types,
 * and hold a number of Row objects.
 */
public class Table {

    Set<String> columnNames;
    List<String> columnTypes;
    List<Row> rows;  // all rows in the table form a linked list
    // TODO: try rewriting nameVsType as a Map, more specifically, a LinkedHashMap.
    Map<String, String> nameVsType;
    int totalRows;
    int totalCols;

    /** Constructor, given set of variable names. */
    public Table(Map<String, String> namesAndTypes) {
        nameVsType = new LinkedHashMap<>(namesAndTypes);
        rows = new LinkedList<>();
        totalRows = 0;
        totalCols = nameVsType.size();
        // Fixme: columnNames may not be needed.
        columnNames = new LinkedHashSet<>(nameVsType.keySet());
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
        Map<String, String> commonNameAndType = new LinkedHashMap<>();
        Map<String, String> allNameAndType = new LinkedHashMap<>();

        for (String name : nameVsType.keySet()) {
            if (anotherTable.nameVsType.containsKey(name)) {
                commonNameAndType.put(name, nameVsType.get(name));
                allNameAndType.put(name, nameVsType.get(name));
            }
        }
        // Now only common variables are in allVar.
        // Add the rest of variables in order
        // FIXME: New value will replace old value if key exists, but order should be maintained. If not as expected, fix me
        allNameAndType.putAll(nameVsType);
        allNameAndType.putAll(anotherTable.nameVsType);

        // If no common variables, should return Cartesian Product of the 2 tables
        if (commonNameAndType.size() == 0) {
            return cartesianProduct(anotherTable);
        }

        // Otherwise, try to merge the two tables
        // Create a new table with all variables
        Table returnTable = new Table(allNameAndType);

        // Merge all rows
        for (Row r1 : rows) {
            for (Row r2 : anotherTable.rows) {
                Row mergedRow = r1.MergeRows(r2, commonNameAndType, allNameAndType);
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
        // combine column variable maps
        Map<String, String> allNameAndType = new LinkedHashMap<>(nameVsType);
        allNameAndType.putAll(anotherTable.nameVsType);

        // creates new table
        Table returnTable = new Table(allNameAndType);

        // merge rows and add to returnTable
        for (Row r1 : rows) {
            for (Row r2: anotherTable.rows) {
                Row rowToAdd = r1.MergeIndependentRows(r2, allNameAndType);
                returnTable.addRow(rowToAdd);
            }
        }

        return returnTable;
    }
    /** Returns selected columns as a new table from this table */
    public Table subTable(String[] columnNameArray) {

        Map<String, String> subTableNameVsType = new LinkedHashMap<>();
        for (String columnName : columnNameArray) {
            subTableNameVsType.put(columnName, nameVsType.get(columnName));
        }

        Table resultTable = new Table(subTableNameVsType);

        for (Row r : rows) {
            resultTable.addRow(r.subRow(subTableNameVsType));
        }

        return resultTable;
    }

    /** Helper method to decide if given two columns OR a column and a literal are comparable. */
    private boolean typeComparable(String op1, String op2) {

        if (nameVsType.containsKey(op2)) {
            // both op1 and op2 are columns
            if (nameVsType.get(op1) == "string" && nameVsType.get(op2) != "string") {
                return false;
            }
            if (nameVsType.get(op2) == "string" && nameVsType.get(op1) != "string") {
                return false;
            }
            return true;
        } else {
            // op2 is a literal
            if (nameVsType.get(op1) == "string") {
                return true;
            } else {
                try {
                    Float.parseFloat(op2);
                    return true;
                } catch (Exception e) {
                    return false;
                }
            }
        }

    }

    /** Helper method to evaluate true/false based on given operands and operator
     *  val1 and val2 are both the results from the Row.get() method.
     *  */
    private boolean evalCondition(Object val1, Object val2, String op) {

        if (val1 instanceof String) {

            int k = val1.toString().compareTo(val2.toString());

            switch (op) {
                case "==": return val1.equals(val2);
                case "!=": return !val1.equals(val2);
                case ">": return k == 0;
                case ">=": return k >= 0;
                case "<": return k < 0;
                case "<=": return k <= 0;
            }
        } else if (val1 instanceof Integer) {
            int v1 = (int) val1;
            int v2 = (int) val2;

            switch (op) {
                case "==": return v1 == v2;
                case "!=": return v1 != v2;
                case ">": return v1 > v2;
                case ">=": return v1 >= v2;
                case "<": return v1 < v2;
                case "<=": return v1 <= v2;
            }
        } else {
            float v1 = (float) val1;
            float v2 = (float) val2;

            switch (op) {
                case "==": return v1 == v2;
                case "!=": return v1 != v2;
                case ">": return v1 > v2;
                case ">=": return v1 >= v2;
                case "<": return v1 < v2;
                case "<=": return v1 <= v2;
            }

        }

        System.err.print("Error in evalCondition() method!\n");
        return false;
    }

    /** Helper method to evaluate true/false based on given operands and operator
     *  val1 is the return value from Row.get(), val2 is a literal,
     *  if val1 is String, use val2 as a String literal
     *  if val1 is int or float, use val2 as a float.
     *  */
    private boolean evalCondition(Object val1, String val2, String op) {

        if (val1 instanceof String) {
            // if string
            String v1 = val1.toString();
            String v2 = val2;
            int k = v1.compareTo(v2);

            switch (op) {
                case "==": return v1.equals(v2);
                case "!=": return !v1.equals(v2);
                case ">": return k == 0;
                case ">=": return k >= 0;
                case "<": return k < 0;
                case "<=": return k <= 0;
            }
        } else {
            // if int or float
            if (val1 instanceof Integer) {
                int v1 = (int) val1;
                int v2 = Integer.parseInt(val2);
                switch (op) {
                    case "==": return v1 == v2;
                    case "!=": return v1 != v2;
                    case ">": return v1 > v2;
                    case ">=": return v1 >= v2;
                    case "<": return v1 < v2;
                    case "<=": return v1 <= v2;
                }
            } else {
                float v1 = (Float) val1;
                float v2 = Float.parseFloat(val2);
                switch (op) {
                    case "==": return v1 == v2;
                    case "!=": return v1 != v2;
                    case ">": return v1 > v2;
                    case ">=": return v1 >= v2;
                    case "<": return v1 < v2;
                    case "<=": return v1 <= v2;
                }
            }
        }

        System.err.print("Error in evalCondition() method!\n");
        return false;
    }

    /** Returns selected rows based on given conditions as a new table */
    public Table subTable(String conds) {

        final Pattern MC = Pattern.compile("\\s*(\\w+\\s*(?:==|!=|>|<|>=|<=)\\s*\\w+)\\s*");
        final Pattern SC = Pattern.compile("(\\w+)\\s*(==|!=|>|<|>=|<=)\\s*(\\w+)");

        Matcher m1 = SC.matcher(conds);

        if (!m1.matches()) {
            System.err.print("No matches in conditional statement!\n");
            return null;
        }
        //System.out.println(m1.group(1) + " " + m1.group(2) + " " + m1.group(3));
        String op1 = m1.group(1);
        String operator = m1.group(2);
        String op2 = m1.group(3);

        Table resultTable = new Table(nameVsType);
        if (!nameVsType.containsKey(op1)) {
            System.err.print("No such column name in the table!\n");
            return null;
        } else if (nameVsType.containsKey(op2)){
            // op1 and op2 are both column names
            if (typeComparable(op1, op2)) {
                for (Row r : rows) {
                    if (evalCondition(r.get(op1), r.get(op2), operator)) {
                        resultTable.addRow(r);
                    }
                }
            } else {
                System.err.printf("Column %s and Column %s are not comparable. \n", op1, op2);
            }

        } else if (typeComparable(op1, op2)) { // op2 is literal and comparable with op1
            for (Row r : rows) {
                if (evalCondition(r.get(op1), op2, operator)) {
                    resultTable.addRow(r);
                }
            }
        } else {
            System.err.printf("Column %s and Literal %s are not comparable. \n", op1, op2);
        }


        return resultTable;
    }

    /** Returns a single-column table as a result of 2-column operation */
    public Table columnOperation(String[] operationColumnNames, char operator, String newColumnName) {
        Map<String, String> nameAndType = new HashMap<>();
        nameAndType.put(newColumnName, nameVsType.get(operationColumnNames[0]));

        Table resultTable = new Table(nameAndType);
        for (Row r : rows) {
            resultTable.addRow(r.columnOperation(operationColumnNames, operator, newColumnName));
        }

        return resultTable;
    }
    /** Returns a string presenting the table object, can be used to print the table or write to file */
    @Override
    public String toString() {
        // combine column names and types
        List<String> columnNamesAndTypes = new ArrayList<>(totalCols);
        for (String name : nameVsType.keySet()) {
            columnNamesAndTypes.add(name + " " + nameVsType.get(name));
        }

        // use StringBuilder due to multiple string concatenations
        StringBuilder sb = new StringBuilder();
        sb.append(String.join(",", columnNamesAndTypes));
        sb.append("\n");
        for (Row r : rows) {
            sb.append(r.toString());
        }
        String returnString = sb.toString();
        // remove trailing newline and return
        return returnString.substring(0, returnString.length() - 1);
    }
}
