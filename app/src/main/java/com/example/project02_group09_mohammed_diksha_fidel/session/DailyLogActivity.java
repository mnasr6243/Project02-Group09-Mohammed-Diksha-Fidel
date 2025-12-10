package com.example.project02_group09_mohammed_diksha_fidel.session;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
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

        Button btnBackToMainDaily = findViewById(R.id.btnBackToMainDaily);
        btnBackToMainDaily.setOnClickListener(v -> finish());

        Button btnAddLog = findViewById(R.id.btnAddLog);
        btnAddLog.setOnClickListener(v ->
                startActivity(new Intent(DailyLogActivity.this, AddLogActivity.class))
        );

        loadDailyLogs();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDailyLogs();
    }

    private void loadDailyLogs() {
        int currentUserId = sessionManager.getCurrentUserId();

        activityRepository.getDailyLogsForUser(currentUserId, System.currentTimeMillis(),
                new ActivityRepository.OnLogsLoadedListener() {
                    @Override
                    public void onLogsLoaded(List<ActivityLog> logs) {
                        runOnUiThread(() -> adapter.setLogs(logs));
                    }
                });
    }
}