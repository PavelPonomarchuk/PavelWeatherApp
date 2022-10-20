package ru.ponomarchukpn.pavelweatherapp.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ru.ponomarchukpn.pavelweatherapp.pojo.Location;

public class LocationsViewModel extends AndroidViewModel {
    public static LocationsDatabase locationsDatabase;
    public LiveData<List<Location>> locations;


    public LocationsViewModel(@NonNull Application application) {
        super(application);
        locationsDatabase = LocationsDatabase.getInstance(getApplication());
        locations = locationsDatabase.locationsDao().getAllLocations();
    }

    public LiveData<List<Location>> getLocations() {
        return locations;
    }

    public void insertLocation(Location location) {
        new InsertTask().execute(location);
    }

    public void deleteLocation(Location location) {
        new DeleteTask().execute(location);
    }

    public void deleteAllLocations() {
        new DeleteAllTask().execute();
    }

    private static class InsertTask extends AsyncTask<Location, Void, Void> {

        @Override
        protected Void doInBackground(Location... locationsParams) {
            if (locationsParams != null && locationsParams.length > 0) {
                locationsDatabase.locationsDao().insertLocation(locationsParams[0]);
            }
            return null;
        }
    }

    private static class DeleteTask extends AsyncTask<Location, Void, Void> {

        @Override
        protected Void doInBackground(Location... locationsParams) {
            if (locationsParams != null && locationsParams.length > 0) {
                locationsDatabase.locationsDao().deleteLocation(locationsParams[0]);
            }
            return null;
        }
    }

    private static class DeleteAllTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... locationsParams) {
            locationsDatabase.locationsDao().deleteAllLocations();
            return null;
        }
    }
}
