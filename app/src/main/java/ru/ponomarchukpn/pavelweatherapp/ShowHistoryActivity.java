package ru.ponomarchukpn.pavelweatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
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
    private WeatherDataAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_history);
        recyclerViewHistory = findViewById(R.id.recyclerViewHistory);
        btnGoToMain = findViewById(R.id.buttonToMainFromHistory);
        weatherDataDatabase = WeatherDataDatabase.getInstance(this);
        getData();

        adapter = new WeatherDataAdapter(weatherDataList);
        recyclerViewHistory.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewHistory.setAdapter(adapter);

        btnGoToMain.setOnClickListener(view -> {
            Intent intent = new Intent(ShowHistoryActivity.this, MainActivity.class);
            startActivity(intent);
        });

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                removeFromRecyclerView(viewHolder.getAdapterPosition());
            }
        });
        helper.attachToRecyclerView(recyclerViewHistory);
    }

    private void removeFromRecyclerView(int position) {
        WeatherData weatherData = weatherDataList.get(position);
        weatherDataDatabase.weatherDataDao().deleteWeatherData(weatherData);
        getData();
        adapter.notifyDataSetChanged();
    }

    private void getData() {
        List<WeatherData> dataFromDB = weatherDataDatabase.weatherDataDao().detAllWeatherData();
        weatherDataList.clear();
        weatherDataList.addAll(dataFromDB);
    }
}