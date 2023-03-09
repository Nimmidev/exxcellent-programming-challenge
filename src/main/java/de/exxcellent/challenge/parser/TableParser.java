package de.exxcellent.challenge.parser;

import java.io.IOException;
import java.io.InputStream;

import de.exxcellent.challenge.Table;

/**
 * This abstract class is the base class for all parsers that are able to parse an {@link InputStream} into a {@link Table}.
 *
 * @author Luca Nimmrichter <mail@nimmi.dev>
 * */
public abstract class TableParser {

    protected InputStream inputStream;

    protected TableParser(InputStream inputStream){
        this.inputStream = inputStream;
    }

    public abstract Table parse() throws IOException, Table.FormatException;

}
