package ru.ponomarchukpn.pavelweatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class ShowLocationsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Button btnGoToMain;

    private ArrayList<Location> locations = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_locations);
        recyclerView = findViewById(R.id.recyclerViewLocations);
        btnGoToMain = findViewById(R.id.buttonFromLocationsToMain);

        //test data
        locations.add(new Location("Троицк"));
        locations.add(new Location("Челябинск"));
        locations.add(new Location("Катав-Ивановск"));
        locations.add(new Location("Москва"));
        locations.add(new Location("Лондон"));
        locations.add(new Location("Санкт-Петербург"));
        locations.add(new Location("Екатеринбург"));
        locations.add(new Location("Вологда"));
        locations.add(new Location("Чебаркуль"));
        locations.add(new Location("Феодосия"));
        locations.add(new Location("Миасс"));
        //end

        LocationsAdapter adapter = new LocationsAdapter(locations);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setOnLocationClickListener(new LocationsAdapter.OnLocationClickListener() {
            @Override
            public void onLocationClick(int position) {
                //to test items' interactivity
                Toast.makeText(ShowLocationsActivity.this, locations.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });

        btnGoToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowLocationsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}