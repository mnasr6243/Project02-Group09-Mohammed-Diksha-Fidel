package com.example.project02_group09_mohammed_diksha_fidel.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project02_group09_mohammed_diksha_fidel.R;
import com.example.project02_group09_mohammed_diksha_fidel.data.ActivityLog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

// Adapter connects the activity data to the list view (RecyclerView).
public class ActivityLogAdapter extends RecyclerView.Adapter<ActivityLogAdapter.LogViewHolder> {

    private List<ActivityLog> logList;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault());

    // Basic constructor for the adapter
    public ActivityLogAdapter() {
        this.logList = new ArrayList<>(); // Start with an empty list
    }

    // Constructor that takes a list of data
    public ActivityLogAdapter(List<ActivityLog> logList) {
        this.logList = logList;
    }

    // Update the data in the list and refresh the view
    public void setLogs(List<ActivityLog> newLogs) {
        this.logList = newLogs;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Loads the single item layout (item_activity_log.xml)
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_activity_log, parent, false);
        return new LogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LogViewHolder holder, int position) {
        ActivityLog currentLog = logList.get(position);
        holder.bind(currentLog, dateFormat);
    }

    @Override
    public int getItemCount() {
        // Returns how many items are in the list
        return logList.size();
    }

    // This class holds the view elements for one item.
    static class LogViewHolder extends RecyclerView.ViewHolder {
        private final TextView typeTextView;
        private final TextView valueTextView;
        private final TextView timestampTextView;

        public LogViewHolder(@NonNull View itemView) {
            super(itemView);
            typeTextView = itemView.findViewById(R.id.textViewActivityType);
            valueTextView = itemView.findViewById(R.id.textViewActivityValue);
            timestampTextView = itemView.findViewById(R.id.textViewTimestamp);
        }

        public void bind(ActivityLog log, SimpleDateFormat dateFormat) {
            typeTextView.setText("Activity Type: " + log.getType());
            // Format the value to one decimal place
            valueTextView.setText("Value: " + String.format(Locale.getDefault(), "%.1f", log.getValue()));
            // Format the timestamp for display
            timestampTextView.setText("Logged At: " + dateFormat.format(new Date(log.getTimestamp())));
        }
    }
}