package ru.ponomarchukpn.pavelweatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import ru.ponomarchukpn.pavelweatherapp.pojo.WeatherData;
import ru.ponomarchukpn.pavelweatherapp.utils.JSONUtils;
import ru.ponomarchukpn.pavelweatherapp.utils.NetworkUtils;

public class MainActivity extends AppCompatActivity {

    private EditText editTextLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextLocation = findViewById(R.id.editTextInputLocation);
        Button btnShowWeather = findViewById(R.id.buttonShowWeather);
        Button btnShowLocations = findViewById(R.id.buttonShowLocations);
        Button btnShowHistory = findViewById(R.id.buttonShowHistory);

        btnShowWeather.setOnClickListener(view -> {
            String location = editTextLocation.getText().toString().trim();
            if (!location.equals("")) {
                JSONObject jsonObject = NetworkUtils.getJSONFromNetwork(MainActivity.this, location);
                if (jsonObject != null) {
                    WeatherData data = JSONUtils.getWeatherDataFromJSON(jsonObject);
                    Intent intent = new Intent(MainActivity.this, ShowWeatherActivity.class);
                    intent.putExtra("location", data.getLocation());
                    intent.putExtra("date", data.getDate());
                    intent.putExtra("temperature", data.getTemperature());
                    intent.putExtra("windSpeed", data.getWindSpeed());
                    intent.putExtra("description", data.getDescription());
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, R.string.error_while_getting_data, Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnShowLocations.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ShowLocationsActivity.class);
            startActivity(intent);
        });

        btnShowHistory.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ShowHistoryActivity.class);
            startActivity(intent);
        });
    }
}