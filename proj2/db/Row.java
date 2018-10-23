package db;

import java.util.*;

/** Row class object holds the data of an Row and performs row operations */
public class Row {
    private Set<String> columnNames;
//    private Set<String> columnTypes;
    private int totalColumns;

    Map<String, Object> rowData;

    /** Constructor */
    public Row(String[] colNames, String[] colTypes, Object[] colValues) {
        columnNames = new LinkedHashSet<>(Arrays.asList(colNames));
//        columnTypes = new LinkedHashSet<>(Arrays.asList(colTypes));
        totalColumns = colNames.length;

        // populate rowData
        int k = 0;
        for (String name : columnNames) {
            rowData.put(name, colValues[k]);
            k += 1;
        }
    }

    /** Constructor 2, given an existing table object and an array of values */
    public Row(Table tbl, Object[] colValues) {
        columnNames = tbl.columnNames;
//        columnTypes = tbl.columnTypes;
        totalColumns = columnNames.size();
        rowData = new HashMap<>();

        // populate rowData
        int k = 0;
        for (String name : columnNames) {
            rowData.put(name, colValues[k]);
            k += 1;
        }
    }

    /** Constructor 3, given Sets and Map */
    public Row(Set<String> colNames, Map<String, Object> rowMap) {
        columnNames = colNames;
//        columnTypes = colTypes;
        totalColumns = columnNames.size();

        // populate rowData
        rowData = rowMap;
    }

    /** Merge this row with another row, given that the two rows have one or more common variables */
    public Row MergeRows(Row anotherRow, Set<String> commonVariables, Set<String> allVariables) {
        // Check if the commonVariables in both tables match with each other
        boolean matched = true;
        for (String var : commonVariables) {
            matched = matched && (rowData.get(var).equals(anotherRow.rowData.get(var)));
        }

        // If match, merge the two rows and return result in a new row
        if (matched) {
            Map<String, Object> mergedRowMaps = new HashMap<>(rowData);
            mergedRowMaps.putAll(anotherRow.rowData);

            return new Row(allVariables, mergedRowMaps);
        }
        return null;

    }

    /** Merge this row with another row, given that the two rows don't have common variables */
    public Row MergeIndependentRows(Row anotherRow, Set<String> allVariables) {

        // Merge row maps
        Map<String, Object> mergedRowMaps = new HashMap<>(rowData);
        mergedRowMaps.putAll(anotherRow.rowData);

        // If match, merge the two rows and return result in a new row
        return new Row(allVariables, mergedRowMaps);

    }

}
