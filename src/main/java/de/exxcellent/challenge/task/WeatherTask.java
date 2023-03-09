package de.exxcellent.challenge.task;

import java.io.InputStream;
import java.util.List;

import de.exxcellent.challenge.Table;
import de.exxcellent.challenge.parser.CsvParser;
import de.exxcellent.challenge.parser.TableParser;

public class WeatherTask implements Runnable {

    @Override
    public void run(){
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("de/exxcellent/challenge/weather.csv");
            TableParser parser = new CsvParser(inputStream);
            Table table = parser.parse();

            if(!table.isEmpty()){
                List<Table.Row> sortedRows = table.getSortedRows(this::calculateSpread);
                Table.Row row = sortedRows.get(0);
                int spread = calculateSpread(row);
                String msg = String.format("The minimum spread is %d on day %s", spread, row.get("Day"));
                System.out.println(msg);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    private int calculateSpread(Table.Row row){
        int max = Integer.parseInt(row.get("MxT"));
        int min = Integer.parseInt(row.get("MnT"));

        return max - min;
    }

}
