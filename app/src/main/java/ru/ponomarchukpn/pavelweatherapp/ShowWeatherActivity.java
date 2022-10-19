package ru.ponomarchukpn.pavelweatherapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;
import java.util.List;

import ru.ponomarchukpn.pavelweatherapp.utils.LocationsDatabase;
import ru.ponomarchukpn.pavelweatherapp.utils.WeatherContract;
import ru.ponomarchukpn.pavelweatherapp.utils.WeatherDataDBHelper;

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

    private WeatherDataDBHelper weatherDBHelper;

    private LocationsDatabase database;

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

        weatherDBHelper = new WeatherDataDBHelper(this);
        SQLiteDatabase dbWeather = weatherDBHelper.getWritableDatabase();

        database = LocationsDatabase.getInstance(this);

        btnGoBack.setOnClickListener(view -> {
            Intent intentBack = new Intent(ShowWeatherActivity.this, MainActivity.class);
            startActivity(intentBack);
        });

        btnSaveLocation.setOnClickListener(view -> {
            List<Location> currentLocations = database.locationsDao().getAllLocations();
            Location location = new Location(locationName);
            if (!currentLocations.contains(location)) {
                database.locationsDao().insertLocation(location);
                Toast.makeText(ShowWeatherActivity.this, R.string.toast_location_saved, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ShowWeatherActivity.this, R.string.toast_location_already_exists, Toast.LENGTH_SHORT).show();
            }
        });

        btnSaveResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues values = new ContentValues();
                values.put(WeatherContract.WeatherDataEntry.COLUMN_LOCATION, locationName);
                values.put(WeatherContract.WeatherDataEntry.COLUMN_DATE, date);
                values.put(WeatherContract.WeatherDataEntry.COLUMN_TEMPERATURE, temp);
                values.put(WeatherContract.WeatherDataEntry.COLUMN_WIND_SPEED, wind);
                values.put(WeatherContract.WeatherDataEntry.COLUMN_DESCRIPTION, description);
                dbWeather.insert(WeatherContract.WeatherDataEntry.TABLE_NAME, null, values);
                Toast.makeText(ShowWeatherActivity.this, R.string.toast_result_has_saved, Toast.LENGTH_SHORT).show();
            }
        });
    }
}