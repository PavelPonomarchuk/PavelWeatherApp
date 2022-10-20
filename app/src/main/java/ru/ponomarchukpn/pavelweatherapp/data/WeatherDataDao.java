package ru.ponomarchukpn.pavelweatherapp.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ru.ponomarchukpn.pavelweatherapp.WeatherData;

@Dao
public interface WeatherDataDao {
    @Query("SELECT * FROM weather_data")
    LiveData<List<WeatherData>> getAllWeatherData();

    @Insert
    void insertWeatherData(WeatherData weatherData);

    @Delete
    void deleteWeatherData(WeatherData weatherData);

    @Query("DELETE FROM weather_data")
    void deleteAllWeatherData();
}
