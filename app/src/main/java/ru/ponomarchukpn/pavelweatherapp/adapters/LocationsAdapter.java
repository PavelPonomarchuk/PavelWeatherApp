package ru.ponomarchukpn.pavelweatherapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ru.ponomarchukpn.pavelweatherapp.R;
import ru.ponomarchukpn.pavelweatherapp.pojo.Location;

public class LocationsAdapter extends RecyclerView.Adapter<LocationsAdapter.LocationsViewHolder> {
    private final ArrayList<Location> locations;
    private OnLocationClickListener onLocationClickListener;

    public LocationsAdapter(ArrayList<Location> locations) {
        this.locations = locations;
    }

    public interface OnLocationClickListener {
        void onLocationClick(int position);
    }

    public void setOnLocationClickListener(OnLocationClickListener onLocationClickListener) {
        this.onLocationClickListener = onLocationClickListener;
    }

    @NonNull
    @Override
    public LocationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_item, parent, false);
        return new LocationsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationsViewHolder holder, int position) {
        holder.textViewLocationName.setText(locations.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    class LocationsViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewLocationName;

        public LocationsViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewLocationName = itemView.findViewById(R.id.textViewLocationName);
            itemView.setOnClickListener(view -> {
                if (onLocationClickListener != null){
                    onLocationClickListener.onLocationClick(getAdapterPosition());
                }
            });
        }
    }
}
