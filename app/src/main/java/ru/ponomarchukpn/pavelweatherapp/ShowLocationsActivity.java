package ru.ponomarchukpn.pavelweatherapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ru.ponomarchukpn.pavelweatherapp.data.LocationsViewModel;
import ru.ponomarchukpn.pavelweatherapp.pojo.Location;
import ru.ponomarchukpn.pavelweatherapp.pojo.WeatherData;
import ru.ponomarchukpn.pavelweatherapp.utils.JSONUtils;
import ru.ponomarchukpn.pavelweatherapp.utils.NetworkUtils;

public class ShowLocationsActivity extends AppCompatActivity {

    private final ArrayList<Location> locations = new ArrayList<>();
    private LocationsAdapter adapter;
    private LocationsViewModel locationsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_locations);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewLocations);
        Button btnGoToMain = findViewById(R.id.buttonFromLocationsToMain);

        locationsViewModel = new ViewModelProvider(this).get(LocationsViewModel.class);
        getData();

        adapter = new LocationsAdapter(locations);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setOnLocationClickListener(position -> {
            String location = locations.get(position).getName();
            if (!location.equals("")) {
                JSONObject jsonObject = NetworkUtils.getJSONFromNetwork(ShowLocationsActivity.this, location);
                if (jsonObject != null) {
                    WeatherData data = JSONUtils.getWeatherDataFromJSON(jsonObject);
                    Intent intent = new Intent(ShowLocationsActivity.this, ShowWeatherActivity.class);
                    intent.putExtra("location", data.getLocation());
                    intent.putExtra("date", data.getDate());
                    intent.putExtra("temperature", data.getTemperature());
                    intent.putExtra("windSpeed", data.getWindSpeed());
                    intent.putExtra("description", data.getDescription());
                    startActivity(intent);
                } else {
                    Toast.makeText(ShowLocationsActivity.this, R.string.error_while_getting_data, Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnGoToMain.setOnClickListener(view -> {
            Intent intent = new Intent(ShowLocationsActivity.this, MainActivity.class);
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
        helper.attachToRecyclerView(recyclerView);
    }

    private void removeFromRecyclerView(int position){
        Location location = locations.get(position);
        locationsViewModel.deleteLocation(location);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void getData(){
        LiveData<List<Location>> locationsFromDB = locationsViewModel.getLocations();
        locationsFromDB.observe(this, locationsFromLiveData -> {
            locations.clear();
            locations.addAll(locationsFromLiveData);
            adapter.notifyDataSetChanged();
        });
    }
}