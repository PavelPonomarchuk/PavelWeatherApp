package ru.ponomarchukpn.pavelweatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;
import java.util.List;

import ru.ponomarchukpn.pavelweatherapp.utils.LocationsDatabase;
import ru.ponomarchukpn.pavelweatherapp.utils.WeatherDataDatabase;

public class ShowWeatherActivity extends AppCompatActivity {

    private TextView textViewWeatherInfo;
    private TextView textViewLocation;
    private Button btnGoBack;
    private Button btnSaveLocation;
    private Button btnSaveResult;
    private String locationName;
    private String temp;
    private String wind;
    private String description;
    private String date;

    private LocationsDatabase locationsDatabase;
    private WeatherDataDatabase weatherDataDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_weather);
        textViewWeatherInfo = findViewById(R.id.textViewWeatherInfo);
        textViewLocation = findViewById(R.id.textViewLocation);
        btnGoBack = findViewById(R.id.buttonGoBack);
        btnSaveLocation = findViewById(R.id.buttonSaveLocation);
        btnSaveResult = findViewById(R.id.buttonSaveResult);

        Intent intent = getIntent();
        locationName = intent.getStringExtra("name");
        temp = intent.getStringExtra("temp");
        wind = intent.getStringExtra("windStr");
        description = intent.getStringExtra("description");
        date = new Date().toString();
        String weatherInfo = "Температура воздуха: " + temp + "\n"
                + "Скорость ветра: " + wind + " м/с\n"
                + description;

        textViewLocation.setText(locationName);
        textViewWeatherInfo.setText(weatherInfo);

        locationsDatabase = LocationsDatabase.getInstance(this);
        weatherDataDatabase = WeatherDataDatabase.getInstance(this);

        btnGoBack.setOnClickListener(view -> {
            Intent intentBack = new Intent(ShowWeatherActivity.this, MainActivity.class);
            startActivity(intentBack);
        });

        btnSaveLocation.setOnClickListener(view -> {
            List<Location> currentLocations = locationsDatabase.locationsDao().getAllLocations();
            Location location = new Location(locationName);
            if (!currentLocations.contains(location)) {
                locationsDatabase.locationsDao().insertLocation(location);
                Toast.makeText(ShowWeatherActivity.this, R.string.toast_location_saved, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ShowWeatherActivity.this, R.string.toast_location_already_exists, Toast.LENGTH_SHORT).show();
            }
        });

        btnSaveResult.setOnClickListener(view -> {
            WeatherData weatherData = new WeatherData(locationName, date,temp, wind, description);
            weatherDataDatabase.weatherDataDao().insertWeatherData(weatherData);
            Toast.makeText(ShowWeatherActivity.this, R.string.toast_result_has_saved, Toast.LENGTH_SHORT).show();
        });
    }
}