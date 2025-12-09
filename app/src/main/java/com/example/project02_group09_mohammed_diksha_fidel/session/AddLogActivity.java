package com.example.project02_group09_mohammed_diksha_fidel.session;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project02_group09_mohammed_diksha_fidel.R; // Ensure R is imported
import com.example.project02_group09_mohammed_diksha_fidel.data.ActivityLog;
// Import for the Repository class in the repository package
import com.example.project02_group09_mohammed_diksha_fidel.repository.ActivityRepository;


public class AddLogActivity extends AppCompatActivity {

    private EditText activityTypeInput;
    private EditText valueInput;
    private ActivityRepository activityRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Instantiating ActivityRepository
        activityRepository = new ActivityRepository(getApplication());

        activityTypeInput = findViewById(R.id.textViewActivityType);
        valueInput = findViewById(R.id.adminTitle);
        Button saveButton = findViewById(R.id.btnBack);

        saveButton.setOnClickListener(v -> saveActivityLog());
    }

    private void saveActivityLog() {
        String type = activityTypeInput.getText().toString().trim();
        String valueStr = valueInput.getText().toString().trim();

        if (type.isEmpty() || valueStr.isEmpty()) {
            Toast.makeText(this, "Please enter both type and value.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            float value = Float.parseFloat(valueStr);
            int currentUserId = 1;

            // FIX: Only declare newLog once, using the 5-argument constructor
            // with '0' for the auto-generated ID.
            ActivityLog newLog = new ActivityLog(0, currentUserId, type, value, System.currentTimeMillis());

            activityRepository.insert(newLog);

            Toast.makeText(this, "Activity saved successfully!", Toast.LENGTH_SHORT).show();
            finish(); // Closes the activity

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Value must be a number.", Toast.LENGTH_SHORT).show();
        }
    }
}