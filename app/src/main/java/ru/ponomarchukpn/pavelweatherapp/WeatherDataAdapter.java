package ru.ponomarchukpn.pavelweatherapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WeatherDataAdapter extends RecyclerView.Adapter<WeatherDataAdapter.HistoryViewHolder> {
    private final ArrayList<WeatherData> weatherDataList;

    public WeatherDataAdapter(ArrayList<WeatherData> weatherDataList) {
        this.weatherDataList = weatherDataList;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        WeatherData data = weatherDataList.get(position);
        String text = "Город: " + data.getLocation() + "\n" + data.getDate() + "\n" +
                "Температура: " + data.getTemperature() + "\n" +
                "Скорость ветра: " + data.getWindSpeed() + "\n" + data.getDescription();
        holder.textViewWeatherItem.setText(text);
    }

    @Override
    public int getItemCount() {
        return weatherDataList.size();
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewWeatherItem;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewWeatherItem = itemView.findViewById(R.id.textViewWeatherItem);
        }
    }
}
