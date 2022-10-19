package ru.ponomarchukpn.pavelweatherapp.utils;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ru.ponomarchukpn.pavelweatherapp.WeatherData;

public class WeatherDataViewModel extends AndroidViewModel {
    private static WeatherDataDatabase database;
    private final LiveData<List<WeatherData>> weatherData;

    public WeatherDataViewModel(@NonNull Application application) {
        super(application);
        database = WeatherDataDatabase.getInstance(getApplication());
        weatherData = database.weatherDataDao().getAllWeatherData();
    }

    public LiveData<List<WeatherData>> getWeatherData() {
        return weatherData;
    }

    public void insertWeatherData(WeatherData data) {
        new InsertTask().execute(data);
    }

    public void deleteWeatherData(WeatherData data) {
        new DeleteTask().execute(data);
    }

    public void deleteAllWeatherData() {
        new DeleteAllTask().execute();
    }

    private static class InsertTask extends AsyncTask<WeatherData, Void, Void> {

        @Override
        protected Void doInBackground(WeatherData... weatherDataParams) {
            if (weatherDataParams != null && weatherDataParams.length > 0) {
                database.weatherDataDao().insertWeatherData(weatherDataParams[0]);
            }
            return null;
        }
    }

    private static class DeleteTask extends AsyncTask<WeatherData, Void, Void> {

        @Override
        protected Void doInBackground(WeatherData... weatherDataParams) {
            if (weatherDataParams != null && weatherDataParams.length > 0) {
                database.weatherDataDao().deleteWeatherData(weatherDataParams[0]);
            }
            return null;
        }
    }

    private static class DeleteAllTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... weatherDataParams) {
            database.weatherDataDao().deleteAllWeatherData();
            return null;
        }
    }
}