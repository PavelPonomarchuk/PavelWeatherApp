package ru.ponomarchukpn.pavelweatherapp.utils;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import ru.ponomarchukpn.pavelweatherapp.Location;

@Database(entities = {Location.class}, version = 1, exportSchema = false)
public abstract class LocationsDatabase extends RoomDatabase {
    private static final String DB_NAME = "locations1.db";
    private static final Object LOCK = new Object();
    private static LocationsDatabase database;

    public static LocationsDatabase getInstance(Context context) {
        synchronized (LOCK) {
            if (database == null) {
                //TODO убрать разрешение на запросы в главном потоке!
                //еще какой контекст нужен
                database = Room.databaseBuilder(context.getApplicationContext(), LocationsDatabase.class, DB_NAME)
                        .allowMainThreadQueries()
                        .build();
            }
            return database;
        }
    }

    public abstract LocationsDao locationsDao();
}
