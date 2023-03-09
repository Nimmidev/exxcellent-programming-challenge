package de.exxcellent.challenge.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import de.exxcellent.challenge.Table;

/**
 * This class is used to parse an {@link InputStream} into a {@link Table}.
 *
 * @author Luca Nimmrichter <mail@nimmi.dev>
 * */
public class CsvParser extends TableParser {

    private String delimiter;
    private Table table = null;

    public CsvParser(InputStream inputStream){
        this(inputStream, ",");
    }

    public CsvParser(InputStream inputStream, String delimiter){
        super(inputStream);
        this.delimiter = delimiter;
    }

    public Table parse() throws IOException, Table.FormatException {
        if(inputStream.available() == 0) throw new IOException();

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))){
            String line;
            boolean headerParsed = false;

            while((line = reader.readLine()) != null){
                if(!headerParsed){
                    headerParsed = true;
                    table = new Table(parseColumnNames(line));
                } else {
                    table.insert(parseRow(line));
                }
            }

            return table;
        } catch(IOException e){
            throw e;
        }
    }

    private String[] parseColumnNames(String line){
        String[] columnNames = line.split(delimiter);

        for(int i = 0; i < columnNames.length; i++){
            columnNames[i] = columnNames[i].trim();
        }

        return columnNames;
    }

    private String[] parseRow(String line) {
        String[] values = line.split(delimiter);

        for(int i = 0; i < values.length; i++){
            values[i] = values[i].trim();
        }

        return values;
    }

}
