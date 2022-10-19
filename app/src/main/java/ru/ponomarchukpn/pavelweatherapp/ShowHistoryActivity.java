package ru.ponomarchukpn.pavelweatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.ponomarchukpn.pavelweatherapp.utils.WeatherDataDatabase;

public class ShowHistoryActivity extends AppCompatActivity {
    private final ArrayList<WeatherData> weatherDataList = new ArrayList<>();
    private RecyclerView recyclerViewHistory;
    private Button btnGoToMain;
    private WeatherDataDatabase weatherDataDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_history);
        recyclerViewHistory = findViewById(R.id.recyclerViewHistory);
        btnGoToMain = findViewById(R.id.buttonToMainFromHistory);
        weatherDataDatabase = WeatherDataDatabase.getInstance(this);
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
        List<WeatherData> datafromDB = weatherDataDatabase.weatherDataDao().detAllWeatherData();
        weatherDataList.clear();
        weatherDataList.addAll(datafromDB);
    }
}