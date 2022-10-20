package ru.ponomarchukpn.pavelweatherapp.unused;

import android.content.Context;

import ru.ponomarchukpn.pavelweatherapp.R;

public class DownloadTaskBuilder {
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather?";
    private final String apiKey;

    public DownloadTaskBuilder(Context context) {
        apiKey = context.getString(R.string.api_key);
    }

    public String build(String location) {
        return BASE_URL + "q=" + location + "&appid=" + apiKey + "&units=metric" + "&lang=ru";
    }
}
