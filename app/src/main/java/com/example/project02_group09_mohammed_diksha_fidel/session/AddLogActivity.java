package com.example.project02_group09_mohammed_diksha_fidel.session;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project02_group09_mohammed_diksha_fidel.R;
import com.example.project02_group09_mohammed_diksha_fidel.data.ActivityLog;
import com.example.project02_group09_mohammed_diksha_fidel.repository.ActivityRepository;

// This activity handles the UI and logic for adding a new activity log entry.
public class AddLogActivity extends AppCompatActivity {

    private EditText activityTypeInput;
    private EditText valueInput;
    private ActivityRepository activityRepository;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_log);

        // Init helpers
        activityRepository = new ActivityRepository(getApplication());
        sessionManager = new SessionManager(this);

        // Bind views
        activityTypeInput = findViewById(R.id.editTextActivityType);
        valueInput = findViewById(R.id.editTextValue);
        Button saveButton = findViewById(R.id.buttonSave);

        saveButton.setOnClickListener(v -> saveActivityLog());
    }

    // Handles the validation and saving of the activity log data.
    private void saveActivityLog() {
        String type = activityTypeInput.getText().toString().trim();
        String valueStr = valueInput.getText().toString().trim();

        if (type.isEmpty() || valueStr.isEmpty()) {
            Toast.makeText(this, "Please enter both type and value.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            float value = Float.parseFloat(valueStr);

            // Uses the logged-in user’s ID
            int currentUserId = sessionManager.getCurrentUserId();

            // Make sure ActivityLog has this constructor:
            // ActivityLog(int userId, String type, float value, long timestamp)
            ActivityLog newLog = new ActivityLog(
                    currentUserId,
                    type,
                    value,
                    System.currentTimeMillis()
            );

            activityRepository.insert(newLog);

            Toast.makeText(this, "Activity saved successfully!", Toast.LENGTH_SHORT).show();
            finish(); // returns to DailyLogActivity

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Value must be a number.", Toast.LENGTH_SHORT).show();
        }
    }
}