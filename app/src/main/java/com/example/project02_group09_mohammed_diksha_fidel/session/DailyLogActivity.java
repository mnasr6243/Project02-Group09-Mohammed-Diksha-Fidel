package com.example.project02_group09_mohammed_diksha_fidel.session;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project02_group09_mohammed_diksha_fidel.R;
import com.example.project02_group09_mohammed_diksha_fidel.adapters.ActivityLogAdapter;
import com.example.project02_group09_mohammed_diksha_fidel.data.ActivityLog;
import com.example.project02_group09_mohammed_diksha_fidel.repository.ActivityRepository;

import java.util.List;

public class DailyLogActivity extends AppCompatActivity {

    private ActivityRepository activityRepository;
    private ActivityLogAdapter adapter;
    private RecyclerView recyclerView;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // NOTE: Ensure your layout XML file for this activity is named activity_daily_log.xml
        setContentView(R.layout.activity_daily_log);

        // Initialize helpers
        activityRepository = new ActivityRepository(getApplication());
        sessionManager = new SessionManager(this);

        // Setup RecyclerView
        recyclerView = findViewById(R.id.dailyLogRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize and Set Adapter
        adapter = new ActivityLogAdapter();
        recyclerView.setAdapter(adapter);

        // Load data on startup
        loadDailyLogs();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDailyLogs();
    }

    private void loadDailyLogs() {
        // Fetch the logged-in user's ID
        int currentUserId = sessionManager.getCurrentUserId();

        activityRepository.getDailyLogsForUser(currentUserId, System.currentTimeMillis(), new ActivityRepository.OnLogsLoadedListener() {
            @Override
            public void onLogsLoaded(List<ActivityLog> logs) {
                // Must switch back to the main thread to update the UI
                runOnUiThread(() -> {
                    adapter.setLogs(logs);
                });
            }
        });
    }
}