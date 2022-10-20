package ru.ponomarchukpn.pavelweatherapp.pojo;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "weather_data")
public class WeatherData {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String location;
    private String date;
    private String temperature;
    private String windSpeed;
    private String description;

    public WeatherData(int id, String location, String date, String temperature, String windSpeed, String description) {
        this.id = id;
        this.location = location;
        this.date = date;
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.description = description;
    }

    @Ignore
    public WeatherData(String location, String date, String temperature, String windSpeed, String description) {
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

    public String getDate() {
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

    public void setId(int id) {
        this.id = id;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeatherData that = (WeatherData) o;
        return location.equals(that.location) && date.equals(that.date) && temperature.equals(that.temperature)
                && windSpeed.equals(that.windSpeed) && description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location, date, temperature, windSpeed, description);
    }
}
