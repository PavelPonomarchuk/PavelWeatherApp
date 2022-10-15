package ru.ponomarchukpn.pavelweatherapp.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class LocationsDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "locations.db";
    private static final int DB_VERSION = 1;

    public LocationsDBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(WeatherContract.LocationsEntry.CREATE_COMMAND);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(WeatherContract.LocationsEntry.DROP_COMMAND);
        onCreate(sqLiteDatabase);
    }
}
