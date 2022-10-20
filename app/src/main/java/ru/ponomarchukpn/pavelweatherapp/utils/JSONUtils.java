package ru.ponomarchukpn.pavelweatherapp.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import ru.ponomarchukpn.pavelweatherapp.pojo.WeatherData;

public class JSONUtils {
    private static final String KEY_NAME = "name";
    private static final String KEY_MAIN = "main";
    private static final String KEY_TEMPERATURE = "temp";
    private static final String KEY_WIND_DATA = "wind";
    private static final String KEY_WIND_SPEED = "speed";
    private static final String KEY_WEATHER = "weather";
    private static final String KEY_DESCRIPTION = "description";

    public static WeatherData getWeatherDataFromJSON(JSONObject jsonObject) {
        WeatherData weatherData = null;
        if (jsonObject == null) {
            return weatherData;
        }
        try {
            String locationName = jsonObject.getString(KEY_NAME);
            JSONObject main = jsonObject.getJSONObject(KEY_MAIN);
            String temperature = main.getString(KEY_TEMPERATURE);
            JSONObject wind = jsonObject.getJSONObject(KEY_WIND_DATA);
            String windSpeed = wind.getString(KEY_WIND_SPEED);
            JSONArray weather = jsonObject.getJSONArray(KEY_WEATHER);
            JSONObject weatherItem = weather.getJSONObject(0);
            String description = weatherItem.getString(KEY_DESCRIPTION);
            String date = new Date().toString();
            weatherData = new WeatherData(locationName, date, temperature, windSpeed, description);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return weatherData;
    }
}
