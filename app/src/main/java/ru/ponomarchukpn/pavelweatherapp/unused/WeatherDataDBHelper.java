package ru.ponomarchukpn.pavelweatherapp.unused;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import ru.ponomarchukpn.pavelweatherapp.unused.WeatherContract;

public class WeatherDataDBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "weather.db";
    public static final int DB_VERSION = 1;

    public WeatherDataDBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(WeatherContract.WeatherDataEntry.CREATE_COMMAND);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(WeatherContract.WeatherDataEntry.DROP_COMMAND);
        onCreate(sqLiteDatabase);
    }
}
