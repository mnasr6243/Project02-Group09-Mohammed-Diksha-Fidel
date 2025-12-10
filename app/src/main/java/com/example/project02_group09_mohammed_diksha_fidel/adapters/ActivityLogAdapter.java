package com.example.project02_group09_mohammed_diksha_fidel.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project02_group09_mohammed_diksha_fidel.R;
import com.example.project02_group09_mohammed_diksha_fidel.data.ActivityLog;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// Adapter to display ActivityLog entries in the Daily Log RecyclerView.
public class ActivityLogAdapter extends RecyclerView.Adapter<ActivityLogAdapter.LogViewHolder> {

    private final List<ActivityLog> logs = new ArrayList<>();
    private OnLogClickListener logClickListener;

    public void setOnLogClickListener(OnLogClickListener listener) {
        this.logClickListener = listener;
    }

    public void setLogs(List<ActivityLog> newLogs) {
        logs.clear();
        if (newLogs != null) {
            logs.addAll(newLogs);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_activity_log, parent, false);
        return new LogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LogViewHolder holder, int position) {
        ActivityLog log = logs.get(position);

        holder.typeView.setText(log.getType());
        holder.valueView.setText(String.valueOf(log.getValue()));

        String formattedTime = DateFormat.getDateTimeInstance().format(new Date(log.getTimestamp()));
        holder.timeView.setText(formattedTime);

        // When the user taps a log, notify listener so we can open Edit screen
        holder.itemView.setOnClickListener(v -> {
            if (logClickListener != null) {
                logClickListener.onLogClicked(log);
            }
        });
    }

    @Override
    public int getItemCount() {
        return logs.size();
    }

    // Listener for when a log is tapped (for editing)
    public interface OnLogClickListener {
        void onLogClicked(ActivityLog log);
    }

    static class LogViewHolder extends RecyclerView.ViewHolder {
        TextView typeView;
        TextView valueView;
        TextView timeView;

        LogViewHolder(@NonNull View itemView) {
            super(itemView);
            typeView = itemView.findViewById(R.id.textViewActivityType);
            valueView = itemView.findViewById(R.id.textViewActivityValue);
            timeView = itemView.findViewById(R.id.textViewTimestamp);
        }
    }
}