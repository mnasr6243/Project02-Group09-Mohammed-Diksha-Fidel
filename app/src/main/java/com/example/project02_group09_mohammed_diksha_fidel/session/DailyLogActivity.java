package com.example.project02_group09_mohammed_diksha_fidel.session;

import android.os.Bundle;
import android.view.MenuItem; // NEW: Import for the back button handler
import android.widget.Button;

import androidx.annotation.NonNull; // NEW: Import for the back button handler
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project02_group09_mohammed_diksha_fidel.R;
import com.example.project02_group09_mohammed_diksha_fidel.adapters.ActivityLogAdapter;
import com.example.project02_group09_mohammed_diksha_fidel.data.ActivityLog;
import com.example.project02_group09_mohammed_diksha_fidel.repository.ActivityRepository;

import java.util.List;

// This activity displays the list of user activity logs for the current day.
public class DailyLogActivity extends AppCompatActivity {

    private ActivityRepository activityRepository;
    private ActivityLogAdapter adapter;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the screen layout
        setContentView(R.layout.activity_daily_log);

        // FIX 1: Enable the Up button (back arrow) in the Action Bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Daily Activity Log");
        }

        // Get the helper classes
        activityRepository = new ActivityRepository(getApplication());
        sessionManager = new SessionManager(this);

        // Set up the list view (RecyclerView)
        RecyclerView recyclerView = findViewById(R.id.dailyLogRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // CRITICAL FIX: Adapter MUST be initialized before being attached to the RecyclerView
        adapter = new ActivityLogAdapter();

        // Find the custom "Back to Main" button and set its listener
        Button btnBackToMainDaily = findViewById(R.id.btnBackToMainDaily);
        btnBackToMainDaily.setOnClickListener(v -> finish()); // Calling finish() returns to the previous activity

        // Attach the adapter to the RecyclerView
        recyclerView.setAdapter(adapter);

        // Load the logs on startup
        loadDailyLogs();
    }

    // NEW METHOD: Handles clicks on the Action Bar items (specifically the Up button)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Checks if the clicked item is the Up button (standard Android ID)
        if (item.getItemId() == android.R.id.home) {
            finish(); // Close this activity and return to the previous one (LandingPage)
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Called when the activity is about to become visible (e.g., when returning from AddLogActivity)
    @Override
    protected void onResume() {
        super.onResume();
        // Reload data whenever the screen comes into focus to show new logs
        loadDailyLogs();
    }

    // Retrieves the log data from the repository.
    private void loadDailyLogs() {
        // FIX: SessionManager does NOT track userId yet → use placeholder userId = 1
        int currentUserId = 1;

        // Request logs from the repository for the current user and current day.
        activityRepository.getDailyLogsForUser(currentUserId, System.currentTimeMillis(), new ActivityRepository.OnLogsLoadedListener() {
            @Override
            public void onLogsLoaded(List<ActivityLog> logs) {
                // Must update the list on the main screen thread (UI Thread)
                runOnUiThread(() -> {
                    adapter.setLogs(logs); // Updates the adapter with the new log list.
                });
            }
        });
    }
}