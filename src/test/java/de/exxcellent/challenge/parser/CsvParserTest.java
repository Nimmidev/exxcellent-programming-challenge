package de.exxcellent.challenge.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import de.exxcellent.challenge.Table;
import de.exxcellent.challenge.Table.FormatException;

class CsvTableParserTest {

    @Test
    void read() throws IOException, FormatException {
        String[] columnNames = new String[]{ "a", "b", "c" };
        String[] rowA = new String[]{ "124", "252", "424" };
        String[] rowB = new String[]{ "83", "2", "163" };
        String[] rowC = new String[]{ "4", "8", "184" };

        String csv = buildCsv(",", columnNames, rowA, rowB, rowC);
        CsvParser parser = new CsvParser(new ByteArrayInputStream(csv.getBytes()));
        Table outTable = parser.parse();

        Table inTable = new Table(columnNames);
        inTable.insert(rowA);
        inTable.insert(rowB);
        inTable.insert(rowC);

        assertEquals(inTable, outTable);
    }

    @Test
    void read_emptyStream() throws IOException {
        CsvParser parser = new CsvParser(new ByteArrayInputStream(new byte[0]));
        assertThrows(IOException.class, () -> parser.parse());
    }

    @Test
    void read_emptyColumnNames() throws IOException {
        String[] columnNames = new String[]{};
        String csv = buildCsv(",", columnNames);
        CsvParser parser = new CsvParser(new ByteArrayInputStream(csv.getBytes()));
        assertThrows(Table.MissingColumnNamesException.class, () -> parser.parse());
    }

    @Test
    void read_columnCountMismatch() throws IOException {
        String[] columnNames = new String[]{ "a", "b", "c" };
        String[] rowA = new String[]{ "124", "252", "513", "asdf" };
        String[] rowB = new String[]{ "83", "2", "9" };

        String csv = buildCsv(",", columnNames, rowA, rowB);
        CsvParser parser = new CsvParser(new ByteArrayInputStream(csv.getBytes()));

        assertThrows(Table.ColumnCountMismatchException.class, () -> parser.parse());
    }

    private String buildCsv(String delimiter, String[] columnNames, String[]... rows){
        StringBuilder builder = new StringBuilder(128);
        String line = String.join(delimiter, columnNames);

        builder.append(line);
        builder.append('\n');

        if(columnNames.length != 0){
            for(int i = 0; i < rows.length; i++){
                builder.append(String.join(delimiter, rows[i]));
                if(i < rows.length - 1) builder.append('\n');
            }
        }

        return builder.toString();
    }

}
