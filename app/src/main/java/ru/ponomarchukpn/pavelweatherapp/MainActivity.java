package ru.ponomarchukpn.pavelweatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import ru.ponomarchukpn.pavelweatherapp.utils.DownloadTask;
import ru.ponomarchukpn.pavelweatherapp.utils.DownloadTaskBuilder;

public class MainActivity extends AppCompatActivity {

    private EditText editTextLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextLocation = findViewById(R.id.editTextInputLocation);
        Button btnShowWeather = findViewById(R.id.buttonShowWeather);
        Button btnShowLocations = findViewById(R.id.buttonShowLocations);
        Button btnShowHistory = findViewById(R.id.buttonShowHistory);

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