package ru.ponomarchukpn.pavelweatherapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ru.ponomarchukpn.pavelweatherapp.utils.WeatherContract;
import ru.ponomarchukpn.pavelweatherapp.utils.WeatherDataDBHelper;

public class ShowHistoryActivity extends AppCompatActivity {
    private final ArrayList<WeatherData> weatherDataList = new ArrayList<>();
    private RecyclerView recyclerViewHistory;
    private Button btnGoToMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_history);
        recyclerViewHistory = findViewById(R.id.recyclerViewHistory);
        btnGoToMain = findViewById(R.id.buttonToMainFromHistory);
        getData();

        WeatherDataAdapter adapter = new WeatherDataAdapter(weatherDataList);
        recyclerViewHistory.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewHistory.setAdapter(adapter);

        btnGoToMain.setOnClickListener(view -> {
            Intent intent = new Intent(ShowHistoryActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    private void getData() {
        weatherDataList.clear();
        WeatherDataDBHelper dbHelper = new WeatherDataDBHelper(this);
        SQLiteDatabase dbWeather = dbHelper.getReadableDatabase();
        Cursor cursor = dbWeather.query(WeatherContract.WeatherDataEntry.TABLE_NAME, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(WeatherContract.WeatherDataEntry._ID));
            @SuppressLint("Range") String location = cursor.getString(cursor.getColumnIndex(WeatherContract.WeatherDataEntry.COLUMN_LOCATION));
            @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex(WeatherContract.WeatherDataEntry.COLUMN_DATE));
            @SuppressLint("Range") String temperature = cursor.getString(cursor.getColumnIndex(WeatherContract.WeatherDataEntry.COLUMN_TEMPERATURE));
            @SuppressLint("Range") String wind = cursor.getString(cursor.getColumnIndex(WeatherContract.WeatherDataEntry.COLUMN_WIND_SPEED));
            @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex(WeatherContract.WeatherDataEntry.COLUMN_DESCRIPTION));
            WeatherData data = new WeatherData(id, location, date, temperature, wind, description);
            weatherDataList.add(data);
        }
        cursor.close();
    }
}