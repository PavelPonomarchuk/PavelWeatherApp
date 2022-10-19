package ru.ponomarchukpn.pavelweatherapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.ponomarchukpn.pavelweatherapp.utils.DownloadTask;
import ru.ponomarchukpn.pavelweatherapp.utils.DownloadTaskBuilder;
import ru.ponomarchukpn.pavelweatherapp.utils.LocationsViewModel;

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
                DownloadTask task = new DownloadTask(ShowLocationsActivity.this);
                DownloadTaskBuilder builder = new DownloadTaskBuilder(ShowLocationsActivity.this);
                task.execute(builder.build(location));
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