package ru.ponomarchukpn.pavelweatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather?";

    EditText editTextLocation;
    Button btnShowWeather;
    Button btnShowLocations;
    Button btnShowHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextLocation = findViewById(R.id.editTextInputLocation);
        btnShowWeather = findViewById(R.id.buttonShowWeather);
        btnShowLocations = findViewById(R.id.buttonShowLocations);
        btnShowHistory = findViewById(R.id.buttonShowHistory);
        String apiKey = getString(R.string.api_key);

        btnShowWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String location = editTextLocation.getText().toString().trim();
                DownloadTask task = new DownloadTask();

                if (!location.equals("")) {
                    StringBuilder builder = new StringBuilder();
                    builder.append(BASE_URL);
                    builder.append("q=").append(location);
                    builder.append("&appid=").append(apiKey);
                    builder.append("&units=metric");
                    builder.append("&lang=ru");
                    task.execute(builder.toString());
                }
            }
        });

        btnShowLocations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ShowLocationsActivity.class);
                startActivity(intent);
            }
        });
    }

    private class DownloadTask extends AsyncTask<String, Void, String> {

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

                    Intent intent = new Intent(MainActivity.this, ShowWeatherActivity.class);
                    intent.putExtra("name", name);
                    intent.putExtra("temp", temp);
                    intent.putExtra("windStr", windStr);
                    intent.putExtra("description", description);
                    startActivity(intent);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}