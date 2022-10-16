package ru.ponomarchukpn.pavelweatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
import java.lang.ref.WeakReference;
import java.net.ContentHandler;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import ru.ponomarchukpn.pavelweatherapp.utils.DownloadTask;
import ru.ponomarchukpn.pavelweatherapp.utils.DownloadTaskBuilder;

public class MainActivity extends AppCompatActivity {

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

        btnShowWeather.setOnClickListener(view -> {
            String location = editTextLocation.getText().toString().trim();
            if (!location.equals("")) {
                DownloadTask task = new DownloadTask(MainActivity.this);
                DownloadTaskBuilder builder = new DownloadTaskBuilder(MainActivity.this);
                task.execute(builder.build(location));
            }
        });

        btnShowLocations.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ShowLocationsActivity.class);
            startActivity(intent);
        });

        btnShowHistory.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ShowHistoryActivity.class);
            startActivity(intent);
        });
    }
}