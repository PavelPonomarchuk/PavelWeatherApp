package ru.ponomarchukpn.pavelweatherapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ru.ponomarchukpn.pavelweatherapp.utils.DownloadTask;
import ru.ponomarchukpn.pavelweatherapp.utils.DownloadTaskBuilder;
import ru.ponomarchukpn.pavelweatherapp.utils.LocationsDBHelper;
import ru.ponomarchukpn.pavelweatherapp.utils.WeatherContract;

public class ShowLocationsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Button btnGoToMain;

    private final ArrayList<Location> locations = new ArrayList<>();
    private LocationsAdapter adapter;
    LocationsDBHelper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_locations);
        recyclerView = findViewById(R.id.recyclerViewLocations);
        btnGoToMain = findViewById(R.id.buttonFromLocationsToMain);

        dbHelper = new LocationsDBHelper(ShowLocationsActivity.this);
        db = dbHelper.getReadableDatabase();
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
        int id = locations.get(position).getId();
        String where = WeatherContract.LocationsEntry._ID + " = ?";
        String[] whereArgs = new String[] {Integer.toString(id)};
        db.delete(WeatherContract.LocationsEntry.TABLE_NAME, where, whereArgs);
        getData();
        adapter.notifyDataSetChanged();
    }

    private void getData(){
        locations.clear();
        Cursor cursor = db.query(WeatherContract.LocationsEntry.TABLE_NAME, null, null, null, null, null, null);
        while(cursor.moveToNext()){
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(WeatherContract.LocationsEntry._ID));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(WeatherContract.LocationsEntry.COLUMN_NAME));
            locations.add(new Location(id, name));
        }
        cursor.close();
    }
}