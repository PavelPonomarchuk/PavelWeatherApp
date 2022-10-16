package ru.ponomarchukpn.pavelweatherapp;

import java.util.Date;

public class WeatherData {
    private final int id;
    private final String location;
    private final Date date;
    private final String temperature;
    private final String windSpeed;
    private final String description;

    public WeatherData(int id, String location, Date date, String temperature, String windSpeed, String description) {
        this.id = id;
        this.location = location;
        this.date = date;
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public Date getDate() {
        return date;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public String getDescription() {
        return description;
    }
}
