package de.exxcellent.challenge.parser;

import java.io.IOException;

import de.exxcellent.challenge.Table;

/**
 * This interface is the base for all parsers that are able to parse a target format into a {@link Table}.
 *
 * @author Luca Nimmrichter <mail@nimmi.dev>
 * */
public interface TableParser {

    Table parse() throws IOException, Table.FormatException;

}
