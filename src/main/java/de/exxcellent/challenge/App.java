package de.exxcellent.challenge;

import de.exxcellent.challenge.task.FootballTask;
import de.exxcellent.challenge.task.WeatherTask;

public final class App {

    public static void main(String... args) {
        WeatherTask weatherTask = new WeatherTask();
        FootballTask footballTask = new FootballTask();

        weatherTask.run();
        footballTask.run();
    }

}
