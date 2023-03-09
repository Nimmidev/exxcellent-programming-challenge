package de.exxcellent.challenge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Data structure to represent tabular data i.e. data that is organized into rows and columns.
 * This Data structure assumes every column has a non-blank name.
 * Also every row has to be of equal size and has to be representable as a {@link String}.
 *
 * @author Luca Nimmrichter <mail@nimmi.dev>
 * */
public class Table {

    private String[] columnNames;
    private Map<String, Integer> columnNameToIndex;
    private ArrayList<Row> rows;

    public Table(String[] columnNames) throws FormatException {
        rows = new ArrayList<>();
        columnNameToIndex = new HashMap<>();
        this.columnNames = sanitizeColumnNames(columnNames);

        for(int i = 0; i < this.columnNames.length; i++){
            columnNameToIndex.put(this.columnNames[i], i);
        }
    }

    private String[] sanitizeColumnNames(String[] columnNames) throws MissingColumnNamesException{
        String[] sanatizedColumnNames = Arrays.stream(columnNames)
            .map(v -> v.trim())
            .filter(v -> v.length() != 0)
            .toArray(String[]::new);

        if(sanatizedColumnNames.length != columnNames.length || columnNames.length == 0){
            throw new MissingColumnNamesException();
        }

        return sanatizedColumnNames;
    }

    public void insert(String[] rowData) throws FormatException {
        rows.add(new Row(rowData));
    }

    public boolean isEmpty(){
        return rows.size() == 0;
    }

    public <T extends Comparable<T>> List<Row> getSortedRows(Function<Row, T> mapper){
        List<Row> sortedRows = new ArrayList<>(rows);

        sortedRows.sort((a, b) -> {
            return mapper.apply(a).compareTo(mapper.apply(b));
        });

        return sortedRows;
    }

    public List<Row> getRows(){
        return rows;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(columnNames) * rows.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(this == obj) return true;
        if(this.getClass() != obj.getClass()) return false;

        Table table = (Table) obj;

        return Arrays.equals(this.columnNames, table.columnNames) &&
            rows.equals(table.rows);
    }


    public class Row {

        private String[] data;
        
        private Row(String[] data) throws FormatException {
            if(data.length != columnNames.length) throw new ColumnCountMismatchException();
            this.data = data;
        }

        public String get(String columnName){
            return data[columnNameToIndex.get(columnName)];
        }

        public String[] getData(){
            return data;
        }

        @Override
        public int hashCode() {
            return data.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if(obj == null) return false;
            if(this == obj) return true;
            if(this.getClass() != obj.getClass()) return false;

            Row row = (Row) obj;

            return Arrays.equals(data, row.data);
        }

    }


    /*
     * Exceptions
     * */

    public class FormatException extends Exception {

        public FormatException(String message){
            super(message);
        }

    }

    public class MissingColumnNamesException extends FormatException {

        public MissingColumnNamesException(){
            super("missing column names");
        }

    }

    public class ColumnCountMismatchException extends FormatException {

        public ColumnCountMismatchException(){
            super("column count mismatch");
        }

    }

}
