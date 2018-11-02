package db;

import java.util.*;

/** Row class object holds the data of an Row and performs row operations */
public class Row {

    private Map<String, String> nameVsType;
    private Set<String> columnNames;
    private int totalCols;
    private Map<String, Object> rowData;

    /** Constructor, given an existing table object and an array of values */
    public Row(Table tbl, Object[] colValues) {
        nameVsType = tbl.nameVsType;
        columnNames = tbl.columnNames;
        totalCols = columnNames.size();
        rowData = new LinkedHashMap<>();

        // populate rowData
        int k = 0;
        for (String name : nameVsType.keySet()) {
            rowData.put(name, colValues[k]);
            k += 1;
        }
    }

    /** Constructor, given Maps */
    public Row(Map<String, String> nameAndType, Map<String, Object> rowMap) {
        nameVsType = nameAndType;
        columnNames = new LinkedHashSet<>(nameAndType.keySet());
        totalCols = columnNames.size();

        // populate rowData
        rowData = rowMap;
    }

    /** Merge this row with another row, given that the two rows have one or more common variables */
    public Row MergeRows(Row anotherRow, Map<String, String> commonNameAndType, Map<String, String> allNameAndType) {
        // Check if the commonVariables in both tables match with each other
        boolean matched = true;
        for (String var : commonNameAndType.keySet()) {
            matched = matched && (rowData.get(var).equals(anotherRow.rowData.get(var)));
        }

        // If match, merge the two rows and return result in a new row
        if (matched) {
            Map<String, Object> mergedRowMaps = new LinkedHashMap<>(rowData);
            mergedRowMaps.putAll(anotherRow.rowData);

            return new Row(allNameAndType, mergedRowMaps);
        }
        return null;

    }

    // Todo: see if both merge method can be merged.
    /** Merge this row with another row, given that the two rows don't have common variables */
    public Row MergeIndependentRows(Row anotherRow, Map<String, String> allNameAndType) {

        // Merge row maps
        Map<String, Object> mergedRowMaps = new LinkedHashMap<>(rowData);
        mergedRowMaps.putAll(anotherRow.rowData);

        // If match, merge the two rows and return result in a new row
        return new Row(allNameAndType, mergedRowMaps);

    }

    /** Returns selected columns as a new row from this row */
    public Row subRow(Map<String, String> nameAndType) {

        Map<String, Object> subRowData = new LinkedHashMap<>();
        for (String name : nameAndType.keySet()) {
            subRowData.put(name, rowData.get(name));
        }
        return new Row(nameAndType, subRowData);
    }

    /** Returns a single-column row from the operation of two columns */
    public Row columnOperation(String[] operationColumnNames, char operator, String newColumnName) {
        Map<String, Object> newRowData = new LinkedHashMap<>();
        Map<String, String> nameAndType = new LinkedHashMap<>();

        String type = nameVsType.get(operationColumnNames[0]);
        nameAndType.put(newColumnName, type);

        if (type.equals("int")) {
            int op1 = (int) rowData.get(operationColumnNames[0]);
            int op2 = (int) rowData.get(operationColumnNames[1]);
            switch (operator) {
                case '+':
                    newRowData.put(newColumnName, op1 + op2);
                    break;
                case '-':
                    newRowData.put(newColumnName, op1 - op2);
                    break;
                case '*':
                    newRowData.put(newColumnName, op1 * op2);
                    break;
                case '/':
                    newRowData.put(newColumnName, op1 / op2);
                    break;
            }
        } else if (type.equals("float")) {
            float op3 = (float) rowData.get(operationColumnNames[0]);
            float op4 = (float) rowData.get(operationColumnNames[1]);
            switch (operator) {
                case '+':
                    newRowData.put(newColumnName, op3 + op4);
                    break;
                case '-':
                    newRowData.put(newColumnName, op3 - op4);
                    break;
                case '*':
                    newRowData.put(newColumnName, op3 * op4);
                    break;
                case '/':
                    newRowData.put(newColumnName, op3 / op4);
                    break;
            }
        }

        return new Row(nameAndType, newRowData);
    }
    /** Get value in a row given column name */
    public Object get(String colName) {
        return rowData.get(colName);
    }


    /** Returns a string presenting the Row object */
    @Override
    public String toString() {

        List<String> orderedColumnValues = new ArrayList<>(totalCols);

        for (String name : columnNames) {
            switch (nameVsType.get(name)) {
                case "float":
                    orderedColumnValues.add(String.format("%.3f", Float.parseFloat(rowData.get(name).toString())));
                    break;
                case "string":
                    orderedColumnValues.add(String.format("'%s'", rowData.get(name).toString()));
                    break;
                default:
                    orderedColumnValues.add(rowData.get(name).toString());
                    break;
            }
        }

        return String.join(",", orderedColumnValues) + "\n";
    }

}
