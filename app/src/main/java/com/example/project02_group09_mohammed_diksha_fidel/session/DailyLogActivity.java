package com.example.project02_group09_mohammed_diksha_fidel.session;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
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
        setContentView(R.layout.activity_daily_log);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Daily Activity Log");
        }

        activityRepository = new ActivityRepository(getApplication());
        sessionManager = new SessionManager(this);

        RecyclerView recyclerView = findViewById(R.id.dailyLogRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ActivityLogAdapter();
        recyclerView.setAdapter(adapter);

        // --- NAVIGATION BUTTONS ---

        // 1. Button to go back to the Main/Landing Page
        Button btnBackToMainDaily = findViewById(R.id.btnBackToMainDaily);
        btnBackToMainDaily.setOnClickListener(v -> finish());

        // 2. Button to ADD a new log entry
        // This button is now correctly linked to the ID in the XML
        Button btnAddLog = findViewById(R.id.btnAddLog);
        btnAddLog.setOnClickListener(v -> {
            // CRITICAL LINK: Launch the activity where the user enters new data
            startActivity(new Intent(this, AddLogActivity.class));
        });

        // --- LIVE DATA OBSERVATION ---
        observeDailyLogs();
    }

    // NEW METHOD: Sets up the LiveData observer.
    private void observeDailyLogs() {
        int currentUserId = sessionManager.getUserId();

        // 1. Get the LiveData object from the repository
        activityRepository.getDailyLogsForUser(currentUserId, System.currentTimeMillis())
                .observe(this, new Observer<List<ActivityLog>>() {
                    @Override
                    public void onChanged(List<ActivityLog> logs) {
                        // 2. When data changes (insertion/deletion), update the UI automatically
                        adapter.setLogs(logs);
                    }
                });
    }


    // Existing method: Handles clicks on the Action Bar items (back button)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}