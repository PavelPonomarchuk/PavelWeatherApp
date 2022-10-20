package ru.ponomarchukpn.pavelweatherapp.utils;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import ru.ponomarchukpn.pavelweatherapp.R;

public class NetworkUtils {
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather?";

    private static final String PARAM_LOCATION = "q";
    private static final String PARAM_API_KEY = "appid";
    private static final String PARAM_UNITS = "units";
    private static final String PARAM_LANGUAGE = "lang";

    private static final String VALUE_UNITS = "metric";
    private static final String VALUE_LANGUAGE = "ru";

    public static JSONObject getJSONFromNetwork(Context context, String location) {
        JSONObject result = null;
        URL url = buildURL(context, location);
        try {
            result = new JSONLoadTask().execute(url).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static URL buildURL(Context context, String location) {
        URL result = null;
        String apiKey = context.getString(R.string.api_key);

        Uri uri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_LOCATION, location)
                .appendQueryParameter(PARAM_API_KEY, apiKey)
                .appendQueryParameter(PARAM_UNITS, VALUE_UNITS)
                .appendQueryParameter(PARAM_LANGUAGE, VALUE_LANGUAGE)
                .build();
        try {
            result = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static class JSONLoadTask extends AsyncTask<URL, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(URL... urls) {
            JSONObject result = null;
            if (urls == null || urls.length == 0) {
                return result;
            }
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) urls[0].openConnection();
                InputStream in = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(in);
                BufferedReader reader = new BufferedReader(inputStreamReader);
                StringBuilder builder = new StringBuilder();
                String line = reader.readLine();
                while (line != null) {
                    builder.append(line);
                    line = reader.readLine();
                }
                result = new JSONObject(builder.toString());
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return result;
        }
    }
}