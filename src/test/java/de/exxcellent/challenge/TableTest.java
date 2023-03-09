package de.exxcellent.challenge;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import de.exxcellent.challenge.Table.FormatException;


class TableTest {

    @Test
    void constructor() throws FormatException {
        String[] columnNames = new String[]{ "a", "b" };
        new Table(columnNames);
    }

    @Test
    void constructor_missingColumnNames() throws FormatException {
        String[] columnNames = new String[0];
        assertThrows(Table.MissingColumnNamesException.class, () -> new Table(columnNames));
    }

    @Test
    void constructor_emptyColumnNames() throws FormatException {
        String[] columnNames = new String[]{ " ", "" };
        assertThrows(Table.MissingColumnNamesException.class, () -> new Table(columnNames));
    }

    @Test
    void constructor_leadingOrTrailingWhitespace() throws FormatException {
        String[] columnNames = new String[]{ " a", "b ", " c ", "d" };
        Table table = new Table(columnNames);

        String[] sanitizedColumnNames = Arrays
            .stream(columnNames)
            .map(v -> v.trim())
            .toArray(String[]::new);

        assertEquals(table, new Table(sanitizedColumnNames));
    }

    @Test
    void insert() throws FormatException {
        Table table = new Table(new String[]{ "a" });
        table.insert(new String[]{ "1" });
    }

    @Test
    void insert_columnCountMismatch() throws FormatException {
        Table table = new Table(new String[]{ "a" });
        assertThrows(Table.ColumnCountMismatchException.class, () -> table.insert(new String[]{ "1", "2" }));
    }

    @Test
    void getSortedRows() throws FormatException {
        Table table = new Table(new String[]{ "a", "b" });
        String[] row1 = new String[]{ "1", "123" };
        String[] row2 = new String[]{ "0", "123" };
        String[] row3 = new String[]{ "3", "123" };

        table.insert(row1);
        table.insert(row2);
        table.insert(row3);

        List<Table.Row> rows = table.getSortedRows(v -> Integer.parseInt(v.get("a")));

        assertArrayEquals(rows.get(0).getData(), row2);
        assertArrayEquals(rows.get(1).getData(), row1);
        assertArrayEquals(rows.get(2).getData(), row3);
    }

    @Test
    void getSortedRows_empty() throws FormatException {
        Table table = new Table(new String[]{ "a" });
        table.getSortedRows(v -> 0);
        assertEquals(table.getRows().size(), 0);
    }

    @Test
    void isEmpty() throws FormatException {
        String[] columnNames = new String[]{ "a" };
        Table table1 = new Table(columnNames);
        Table table2 = new Table(columnNames);

        table1.insert(new String[]{ "1" });

        assertFalse(table1.isEmpty());
        assertTrue(table2.isEmpty());
    }

    @Test
    void hashcode() throws FormatException {
        String[] columnNames = new String[]{ "a" };
        String[] row1 = new String[]{ "1" };
        Table table1 = new Table(columnNames);
        Table table2 = new Table(columnNames);
        Table table3 = new Table(columnNames);

        table1.insert(row1);
        table2.insert(row1);

        assertEquals(table1.hashCode(), table1.hashCode());
        assertEquals(table3.hashCode(), table3.hashCode());

        assertEquals(table1.hashCode(), table2.hashCode());
        assertNotEquals(table1.hashCode(), table3.hashCode());
    }

    @Test
    void equals() throws FormatException {
        String[] columnNames = new String[]{ "a", "b" };
        String[] row1 = new String[]{ "1", "2" };
        Table table1 = new Table(columnNames);
        Table table2 = new Table(columnNames);
        Table table3 = new Table(columnNames);

        table1.insert(row1);
        table2.insert(row1);

        assertNotEquals(table1, null);
        assertNotEquals(table1, table3);
        assertNotEquals(table1, 0);
        assertEquals(table1, table1);
        assertEquals(table1, table2);
    }

}
