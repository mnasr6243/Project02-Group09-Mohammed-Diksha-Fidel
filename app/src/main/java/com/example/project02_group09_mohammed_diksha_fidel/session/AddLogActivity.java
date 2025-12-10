package com.example.project02_group09_mohammed_diksha_fidel.session;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project02_group09_mohammed_diksha_fidel.R;
import com.example.project02_group09_mohammed_diksha_fidel.data.ActivityLog;
import com.example.project02_group09_mohammed_diksha_fidel.repository.ActivityRepository;

// This activity handles the UI and logic for adding or editing a activity log entry.
public class AddLogActivity extends AppCompatActivity {

    private EditText activityTypeInput;
    private EditText valueInput;
    private ActivityRepository activityRepository;
    private SessionManager sessionManager;

    // If not -1, we are editing an existing log
    private int editingLogId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_log);

        activityRepository = new ActivityRepository(getApplication());
        sessionManager = new SessionManager(this);

        activityTypeInput = findViewById(R.id.editTextActivityType);
        valueInput = findViewById(R.id.editTextValue);
        Button saveButton = findViewById(R.id.buttonSave);

        // Check if we received a LOG_ID (edit mode)
        editingLogId = getIntent().getIntExtra("LOG_ID", -1);
        if (editingLogId != -1) {
            // Load existing log and prefill fields
            activityRepository.getLogById(editingLogId, log -> {
                if (log != null) {
                    runOnUiThread(() -> {
                        activityTypeInput.setText(log.getType());
                        valueInput.setText(String.valueOf(log.getValue()));
                    });
                }
            });
        }

        saveButton.setOnClickListener(v -> saveActivityLog());
    }

    // Handles the validation and saving/updating of the activity log data.
    private void saveActivityLog() {
        String type = activityTypeInput.getText().toString().trim();
        String valueStr = valueInput.getText().toString().trim();

        if (type.isEmpty() || valueStr.isEmpty()) {
            Toast.makeText(this, "Please enter both type and value.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            float value = Float.parseFloat(valueStr);
            int currentUserId = sessionManager.getCurrentUserId();

            if (editingLogId == -1) {
                // New log
                ActivityLog newLog = new ActivityLog(
                        currentUserId,
                        type,
                        value,
                        System.currentTimeMillis()
                );
                activityRepository.insert(newLog);
                Toast.makeText(this, "Activity saved successfully!", Toast.LENGTH_SHORT).show();
            } else {
                // Update existing log
                ActivityLog updatedLog = new ActivityLog(
                        currentUserId,
                        type,
                        value,
                        System.currentTimeMillis()
                );
                // Set the existing primary key so Room updates instead of inserting
                updatedLog.setId(editingLogId);
                activityRepository.updateLog(updatedLog);
                Toast.makeText(this, "Activity updated successfully!", Toast.LENGTH_SHORT).show();
            }

            finish(); // Close the screen and return to DailyLogActivity.

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Value must be a number.", Toast.LENGTH_SHORT).show();
        }
    }
}