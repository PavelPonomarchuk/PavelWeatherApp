package ru.ponomarchukpn.pavelweatherapp.unused;

import android.provider.BaseColumns;

public class WeatherContract {
    public static final class LocationsEntry implements BaseColumns {
        public static final String TABLE_NAME = "locations";
        public static final String COLUMN_NAME = "name";
        public static final String TYPE_TEXT = "TEXT";
        public static final String TYPE_INTEGER = "INTEGER";

        public static final String CREATE_COMMAND = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                "(" + _ID + " " + TYPE_INTEGER + " PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME +
                " " + TYPE_TEXT + ")";

        public static final String DROP_COMMAND = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static final class WeatherDataEntry implements BaseColumns {
        public static final String TABLE_NAME = "weather_data";

        public static final String COLUMN_LOCATION = "location";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_TEMPERATURE = "temperature";
        public static final String COLUMN_WIND_SPEED = "wind_speed";
        public static final String COLUMN_DESCRIPTION = "description";

        public static final String TYPE_TEXT = "TEXT";
        public static final String TYPE_INTEGER = "INTEGER";

        public static final String CREATE_COMMAND = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                "(" + _ID + " " + TYPE_INTEGER + " PRIMARY KEY AUTOINCREMENT, " + COLUMN_LOCATION +
                " " + TYPE_TEXT + ", " + COLUMN_DATE + " " + TYPE_TEXT + ", " + COLUMN_TEMPERATURE +
                " " + TYPE_TEXT + ", " + COLUMN_WIND_SPEED + " " + TYPE_TEXT + ", " + COLUMN_DESCRIPTION +
                " " + TYPE_TEXT + ")";

        public static final String DROP_COMMAND = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}