package de.exxcellent.challenge.task;

import java.io.InputStream;
import java.util.List;

import de.exxcellent.challenge.Table;
import de.exxcellent.challenge.parser.CsvParser;
import de.exxcellent.challenge.parser.TableParser;

public class FootballTask implements Runnable {

    @Override
    public void run(){
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("de/exxcellent/challenge/football.csv");
            TableParser parser = new CsvParser(inputStream);
            Table table = parser.parse();

            if(!table.isEmpty()){
                List<Table.Row> sortedRows = table.getSortedRows(this::calculateDistance);
                Table.Row row = sortedRows.get(0);
                int distance = calculateDistance(row);
                String msg = String.format("The minimum distance is %d by team %s", distance, row.get("Team"));
                System.out.println(msg);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    private int calculateDistance(Table.Row row){
        int goals = Integer.parseInt(row.get("Goals"));
        int goalsAllowed = Integer.parseInt(row.get("Goals Allowed"));

        return Math.abs(goals - goalsAllowed);

    }

}
