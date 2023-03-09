package de.exxcellent.challenge;

import de.exxcellent.challenge.task.WeatherTask;

public final class App {

    public static void main(String... args) {
        WeatherTask weatherTask = new WeatherTask();
        weatherTask.run();
    }

}
