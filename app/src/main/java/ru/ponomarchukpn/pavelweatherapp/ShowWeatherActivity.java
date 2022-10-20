package ru.ponomarchukpn.pavelweatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.ponomarchukpn.pavelweatherapp.data.LocationsViewModel;
import ru.ponomarchukpn.pavelweatherapp.data.WeatherDataViewModel;

public class ShowWeatherActivity extends AppCompatActivity {

    private String locationName;
    private String temp;
    private String wind;
    private String description;
    private String date;

    private LocationsViewModel locationsViewModel;
    private WeatherDataViewModel weatherDataViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_weather);
        TextView textViewWeatherInfo = findViewById(R.id.textViewWeatherInfo);
        TextView textViewLocation = findViewById(R.id.textViewLocation);
        Button btnGoBack = findViewById(R.id.buttonGoBack);
        Button btnSaveLocation = findViewById(R.id.buttonSaveLocation);
        Button btnSaveResult = findViewById(R.id.buttonSaveResult);

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

        locationsViewModel = new ViewModelProvider(this).get(LocationsViewModel.class);
        weatherDataViewModel = new ViewModelProvider(this).get(WeatherDataViewModel.class);

        Location location = new Location(locationName);
        LiveData<List<Location>> locations = locationsViewModel.getLocations();
        List<Location> locationsList = new ArrayList<>();
        locations.observe(this, locationsFromLiveData -> {
            locationsList.clear();
            locationsList.addAll(locationsFromLiveData);
        });

        btnGoBack.setOnClickListener(view -> {
            Intent intentBack = new Intent(ShowWeatherActivity.this, MainActivity.class);
            startActivity(intentBack);
        });

        btnSaveLocation.setOnClickListener(view -> {
            if (!locationsList.contains(location)) {
                locationsViewModel.insertLocation(location);
                Toast.makeText(ShowWeatherActivity.this, R.string.toast_location_saved, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ShowWeatherActivity.this, R.string.toast_location_already_exists, Toast.LENGTH_SHORT).show();
            }
        });

        btnSaveResult.setOnClickListener(view -> {
            WeatherData weatherData = new WeatherData(locationName, date, temp, wind, description);
            weatherDataViewModel.insertWeatherData(weatherData);
            Toast.makeText(ShowWeatherActivity.this, R.string.toast_result_has_saved, Toast.LENGTH_SHORT).show();
        });
    }
}