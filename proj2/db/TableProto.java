package db;

import java.util.*;

//TODO: simplify Table instantiation, hide inner data structures from user/clients
//TODO: HashMap to Map, HashSet to Set
//TODO: control columns' order
public class TableProto {
    HashSet<String> colNames;
    LinkedList<HashMap<String, Object>> rows;
    private int totalRows;
    private int totalCols;

    public TableProto(HashSet<String> variableNames) {
        this.colNames = variableNames;
        totalRows = 0;
        totalCols = variableNames.size();
        rows = new LinkedList<>();
    }

    public void addRow(HashMap<String, Object> rowData) {
        rows.add(rowData);
        totalRows += 1;
    }

    public TableProto join(TableProto tbl) {
        // search for common variables
        // and get full set of variables at the same time
        HashSet<String> commonVar = new HashSet<>();
        HashSet<String> allVar = new HashSet<>(tbl.colNames);
        for (String name : colNames) {
            if (tbl.colNames.contains(name)) {
                commonVar.add(name);
            }
            allVar.add(name);
        }
        if (commonVar.size() == 0) {
            return null;
        }

        /* search for rows that can be joined together
           i.e., rows with matched common variable values,
           and then add to new table.
         */

        TableProto returnTable = new TableProto(allVar);
        for (HashMap<String, Object> row : rows) {
            HashMap<String, Object> rowCopy = new HashMap<>(row);
            for (HashMap<String, Object> row2 : tbl.rows) {
                boolean matched = true;
                for (String var : commonVar) {
                    if (row.get(var) != row2.get(var)) {
                        matched = false;
                        break;
                    }
                }
                if (matched) {
                    // join rows
                    rowCopy.putAll(row2);
                    // add to returnTable
                    returnTable.addRow(rowCopy);
                }
            }
        }

        return returnTable;
    }

    public void printTable() {
        // print column names
        for (String name : colNames) {
            System.out.print(name + ", ");
        }
        System.out.println();

        // print rows
        for (HashMap row : rows) {
            for (String var : colNames) {
                System.out.print(row.get(var) + ", ");
            }
            System.out.println();
        }

    }

}
