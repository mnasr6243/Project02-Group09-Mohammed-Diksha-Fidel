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
import java.util.ArrayList; // REQUIRED IMPORT for the empty list
import java.util.Date;
import java.util.List;
import java.util.Locale;

// The Adapter class connects your list of ActivityLog objects to the RecyclerView.
public class ActivityLogAdapter extends RecyclerView.Adapter<ActivityLogAdapter.LogViewHolder> {

    private List<ActivityLog> logList;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault());

    // 1. ADDED: Default, no-argument constructor (Required by DailyLogActivity)
    public ActivityLogAdapter() {
        this.logList = new ArrayList<>(); // Initialize as an empty list
    }

    // 2. Original constructor (Kept for completeness, though not currently used by DailyLogActivity)
    public ActivityLogAdapter(List<ActivityLog> logList) {
        this.logList = logList;
    }

    // Use this method in DailyLogActivity's onResume to update the data
    public void setLogs(List<ActivityLog> newLogs) {
        this.logList = newLogs;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflates the layout you created (item_activity_log.xml)
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
        // Corrected null check to use the empty list from the new constructor
        return logList.size();
    }

    // The ViewHolder holds the views (TextViews) for a single item in the list.
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
            // Show value as a string, formatted to one decimal place
            valueTextView.setText("Value: " + String.format(Locale.getDefault(), "%.1f", log.getValue()));
            timestampTextView.setText("Logged At: " + dateFormat.format(new Date(log.getTimestamp())));
        }
    }
}