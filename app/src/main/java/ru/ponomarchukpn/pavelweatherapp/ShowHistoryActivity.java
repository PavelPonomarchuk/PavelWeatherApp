package ru.ponomarchukpn.pavelweatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Date;

public class ShowHistoryActivity extends AppCompatActivity {
    private final ArrayList<WeatherData> weatherDataList = new ArrayList<>();
    private WeatherDataAdapter adapter;

    private RecyclerView recyclerViewHistory;
    private Button btnGoToMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_history);
        recyclerViewHistory = findViewById(R.id.recyclerViewHistory);
        btnGoToMain = findViewById(R.id.buttonToMainFromHistory);

        //test data
        WeatherData data = new WeatherData(0, "Троицк", new Date(), "9.2", "3", "Облачно");
        weatherDataList.add(data);
        //end

        adapter = new WeatherDataAdapter(weatherDataList);
        recyclerViewHistory.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewHistory.setAdapter(adapter);

        btnGoToMain.setOnClickListener(view -> {
            Intent intent = new Intent(ShowHistoryActivity.this, MainActivity.class);
            startActivity(intent);
        });

    }
}