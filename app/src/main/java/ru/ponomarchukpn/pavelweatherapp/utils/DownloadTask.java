package ru.ponomarchukpn.pavelweatherapp.utils;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import ru.ponomarchukpn.pavelweatherapp.ShowWeatherActivity;

public class DownloadTask extends AsyncTask<String, Void, String> {
    Context context;
    //TODO need to prevent leak of context object

    public DownloadTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        URL url = null;
        HttpURLConnection urlConnection = null;
        StringBuilder builder = new StringBuilder();
        String result = null;

        try {
            url = new URL(strings[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = urlConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(in);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                builder.append(line);
                line = reader.readLine();
            }
            result = builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        if (!s.equals("")) {
            try {
                JSONObject response = new JSONObject(s);
                String name = response.getString("name");
                JSONObject main = response.getJSONObject("main");
                String temp = main.getString("temp");
                JSONObject wind = response.getJSONObject("wind");
                String windStr = wind.getString("speed");
                JSONArray weather = response.getJSONArray("weather");
                JSONObject weatherItem = weather.getJSONObject(0);
                String description = weatherItem.getString("description");

                Intent intent = new Intent(context, ShowWeatherActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("temp", temp);
                intent.putExtra("windStr", windStr);
                intent.putExtra("description", description);
                context.startActivity(intent);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
