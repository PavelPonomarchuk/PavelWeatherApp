package ru.ponomarchukpn.pavelweatherapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

import ru.ponomarchukpn.pavelweatherapp.utils.LocationsDBHelper;
import ru.ponomarchukpn.pavelweatherapp.utils.WeatherContract;
import ru.ponomarchukpn.pavelweatherapp.utils.WeatherDataDBHelper;

public class ShowWeatherActivity extends AppCompatActivity {

    TextView textViewWeatherInfo;
    TextView textViewLocation;
    Button btnGoBack;
    Button btnSaveLocation;
    Button btnSaveResult;
    private String location;
    private String temp;
    private String wind;
    private String description;
    private String date;

    private LocationsDBHelper locationsDBHelper;
    private WeatherDataDBHelper weatherDBHelper;

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
        location = intent.getStringExtra("name");
        temp = intent.getStringExtra("temp");
        wind = intent.getStringExtra("windStr");
        description = intent.getStringExtra("description");
        date = new Date().toString();
        String weatherInfo = "Температура воздуха: " + temp + "\n"
                + "Скорость ветра: " + wind + " м/с\n"
                + description;

        textViewLocation.setText(location);
        textViewWeatherInfo.setText(weatherInfo);

        locationsDBHelper = new LocationsDBHelper(this);
        weatherDBHelper = new WeatherDataDBHelper(this);
        SQLiteDatabase dbLocations = locationsDBHelper.getWritableDatabase();
        SQLiteDatabase dbWeather = weatherDBHelper.getWritableDatabase();

        btnGoBack.setOnClickListener(view -> {
            Intent intentBack = new Intent(ShowWeatherActivity.this, MainActivity.class);
            startActivity(intentBack);
        });

        btnSaveLocation.setOnClickListener(view -> {
            String query = "SELECT * FROM " + WeatherContract.LocationsEntry.TABLE_NAME + " WHERE " +
                    WeatherContract.LocationsEntry.COLUMN_NAME + " = \"" + location + "\";";
            Cursor cursor = dbLocations.rawQuery(query, null);
            boolean valueExists = cursor.getCount() > 0;
            cursor.close();
            if (!valueExists) {
                ContentValues values = new ContentValues();
                values.put(WeatherContract.LocationsEntry.COLUMN_NAME, location);
                dbLocations.insert(WeatherContract.LocationsEntry.TABLE_NAME, null, values);
                Toast.makeText(ShowWeatherActivity.this, R.string.toast_location_saved, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ShowWeatherActivity.this, R.string.toast_location_already_exists, Toast.LENGTH_SHORT).show();
            }
        });

        btnSaveResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues values = new ContentValues();
                values.put(WeatherContract.WeatherDataEntry.COLUMN_LOCATION, location);
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