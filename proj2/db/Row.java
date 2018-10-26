package db;

import java.util.*;

/** Row class object holds the data of an Row and performs row operations */
public class Row {

    Map<String, String> nameVsType;
    private Set<String> columnNames;
    private int totalCols;
    Map<String, Object> rowData;

    /** Constructor */
    public Row(String[] colNames, String[] colTypes, Object[] colValues) {
        columnNames = new LinkedHashSet<>(Arrays.asList(colNames));
        totalCols = colNames.length;

        // populate rowData
        int k = 0;
        for (String name : columnNames) {
            rowData.put(name, colValues[k]);
            k += 1;
        }
    }

    /** Constructor 2, given an existing table object and an array of values */
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

    /** Constructor 3, given Sets and Map */
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

    /** Returns a string presenting the Row object */
    @Override
    public String toString() {

        List<String> orderedColumnValues = new ArrayList<>(totalCols);

        for (String name : columnNames) {
            orderedColumnValues.add(rowData.get(name).toString());
        }

        return String.join(",", orderedColumnValues) + "\n";
    }

}
