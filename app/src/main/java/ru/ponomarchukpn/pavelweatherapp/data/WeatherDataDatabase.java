package ru.ponomarchukpn.pavelweatherapp.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import ru.ponomarchukpn.pavelweatherapp.WeatherData;

@Database(entities = {WeatherData.class}, version = 1, exportSchema = false)
public abstract class WeatherDataDatabase extends RoomDatabase {
    private static final String DB_NAME = "weather1.db";
    private static final Object LOCK = new Object();
    private static WeatherDataDatabase database;

    public static WeatherDataDatabase getInstance(Context context) {
        synchronized (LOCK) {
            if (database == null) {
                database = Room.databaseBuilder(context.getApplicationContext(), WeatherDataDatabase.class, DB_NAME)
                        .build();
            }
        }
        return database;
    }

    public abstract WeatherDataDao weatherDataDao();
}
