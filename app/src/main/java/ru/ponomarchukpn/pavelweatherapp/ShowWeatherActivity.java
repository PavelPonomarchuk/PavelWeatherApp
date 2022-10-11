package ru.ponomarchukpn.pavelweatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ShowWeatherActivity extends AppCompatActivity {

    TextView textViewWeatherInfo;
    TextView textViewLocation;
    Button btnGoBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_weather);
        textViewWeatherInfo = findViewById(R.id.textViewWeatherInfo);
        textViewLocation = findViewById(R.id.textViewLocation);
        btnGoBack = findViewById(R.id.buttonGoBack);
        Intent intent = getIntent();

        String location = intent.getStringExtra("name");
        String temp = intent.getStringExtra("temp");
        String wind = intent.getStringExtra("windStr");
        String description = intent.getStringExtra("description");
        String weatherInfo = "Температура воздуха: " + temp + "\n"
                + "Скорость ветра: " + wind + " м/с\n"
                + description;

        textViewLocation.setText(location);
        textViewWeatherInfo.setText(weatherInfo);

        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowWeatherActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}