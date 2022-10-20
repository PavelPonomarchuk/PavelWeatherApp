package ru.ponomarchukpn.pavelweatherapp.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ru.ponomarchukpn.pavelweatherapp.Location;

@Dao
public interface LocationsDao {
    @Query("SELECT * FROM locations")
    LiveData<List<Location>> getAllLocations();

    @Insert
    void insertLocation(Location location);

    @Delete
    void deleteLocation(Location location);

    @Query("DELETE FROM locations")
    void deleteAllLocations();
}
