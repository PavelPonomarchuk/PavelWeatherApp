package ru.ponomarchukpn.pavelweatherapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ru.ponomarchukpn.pavelweatherapp.utils.LocationsDBHelper;
import ru.ponomarchukpn.pavelweatherapp.utils.WeatherContract;

public class ShowWeatherActivity extends AppCompatActivity {

    TextView textViewWeatherInfo;
    TextView textViewLocation;
    Button btnGoBack;
    Button btnSaveLocation;
    LocationsDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_weather);
        textViewWeatherInfo = findViewById(R.id.textViewWeatherInfo);
        textViewLocation = findViewById(R.id.textViewLocation);
        btnGoBack = findViewById(R.id.buttonGoBack);
        btnSaveLocation = findViewById(R.id.buttonSaveLocation);

        Intent intent = getIntent();
        String location = intent.getStringExtra("name");
        String temp = intent.getStringExtra("temp");
        String wind = intent.getStringExtra("windStr");
        String description = intent.getStringExtra("description");
        String weatherInfo = "Температура воздуха: " + temp + "\n"
                + "Скорость ветра: " + wind + " м/с\n"
                + description;

        textViewLocation.setText(location);
        textViewWeatherInfo.setText(weatherInfo);

        dbHelper = new LocationsDBHelper(this);
        SQLiteDatabase dbLocations = dbHelper.getWritableDatabase();

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
    }
}