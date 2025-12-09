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

    // The activity's creation lifecycle method.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // FIX: Use the correct layout for adding a log
        setContentView(R.layout.activity_add_log);

        // Instantiating ActivityRepository using the application context.
        activityRepository = new ActivityRepository(getApplication());

        // Linking UI elements to Java variables using their resource IDs.
        activityTypeInput = findViewById(R.id.editTextActivityType);
        valueInput = findViewById(R.id.editTextValue);
        Button saveButton = findViewById(R.id.buttonSave); // Example ID

        // Sets the click listener to trigger the save operation.
        saveButton.setOnClickListener(v -> saveActivityLog());
    }

    // Handles the validation and saving of the activity log data.
    private void saveActivityLog() {
        // Retrieve and trim input values.
        String type = activityTypeInput.getText().toString().trim();
        String valueStr = valueInput.getText().toString().trim();

        // Input validation: Check if required fields are empty.
        if (type.isEmpty() || valueStr.isEmpty()) {
            Toast.makeText(this, "Please enter both type and value.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // Attempt to convert the input value string to a float.
            float value = Float.parseFloat(valueStr);
            // Hardcoded to test user ID 1 for now (This will be replaced by SessionManager).
            int currentUserId = 1;

            // Create the new log entry object with the current timestamp.
            // IMPORTANT FIX: Removed the incorrect 'id' parameter.
            ActivityLog newLog = new ActivityLog(
                    currentUserId,
                    type,
                    value,
                    System.currentTimeMillis()
            );

            // Calls the repository to save the data in the background.
            activityRepository.insert(newLog);

            Toast.makeText(this, "Activity saved successfully!", Toast.LENGTH_SHORT).show();
            finish(); // Close the screen and return to the previous activity (DailyLogActivity).

        } catch (NumberFormatException e) {
            // Error handling for non-numeric input.
            Toast.makeText(this, "Value must be a number.", Toast.LENGTH_SHORT).show();
        }
    }
}