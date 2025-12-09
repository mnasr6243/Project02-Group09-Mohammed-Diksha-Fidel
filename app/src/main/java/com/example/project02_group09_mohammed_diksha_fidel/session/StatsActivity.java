package com.example.project02_group09_mohammed_diksha_fidel.session;

import android.os.Bundle;
import android.view.MenuItem; // Import for the back button handler
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull; // Import for checking non-null objects/parameters
import androidx.appcompat.app.AppCompatActivity;

import com.example.project02_group09_mohammed_diksha_fidel.R;
import com.example.project02_group09_mohammed_diksha_fidel.repository.ActivityRepository;

import java.util.Calendar;
import java.util.Locale;

// This activity calculates and displays user activity statistics (specifically weekly totals).
public class StatsActivity extends AppCompatActivity {

    private ActivityRepository activityRepository;
    private SessionManager sessionManager;
    private TextView textViewWeeklySteps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats); // Sets the layout for the stats screen

        // FIX 1: Enable the Up button (back arrow) in the Action Bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Enables the standard Action Bar back arrow
            getSupportActionBar().setTitle("Weekly Statistics"); // Sets the title of the Action Bar
        }
        // Finds the custom "Back to Main" button and sets its click listener.
        Button btnBackToMainStats = findViewById(R.id.btnBackToMainStats);
        btnBackToMainStats.setOnClickListener(v -> finish()); // Closes the current activity

        // Initialize helper classes
        sessionManager = new SessionManager(this);
        activityRepository = new ActivityRepository(getApplication());

        // Connects the UI element for displaying the result
        textViewWeeklySteps = findViewById(R.id.textViewWeeklySteps);

        // Initiates the data loading process when the screen is created
        loadWeeklyStats();
    }

    // Handles clicks on the Action Bar items (including the Up button)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Checks if the clicked item is the Up button (standard Android ID)
        if (item.getItemId() == android.R.id.home) {
            finish(); // Closes the current activity and returns to the previous one
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Logic to fetch the total steps for the last 7 days from the database.
    private void loadWeeklyStats() {
        int currentUserId = sessionManager.getUserId();

        // Calculate the start and end dates for the last 7 days (Weekly range)
        Calendar cal = Calendar.getInstance();
        long endDate = cal.getTimeInMillis(); // Gets the current timestamp (Today's end)

        cal.add(Calendar.DAY_OF_YEAR, -6); // Moves the calendar back 6 days to find the start of the 7-day period
        long startDate = cal.getTimeInMillis(); // Start of the 7-day period timestamp

        // --- Fetch Weekly Steps ---

        // Uses the Repository method to fetch the sum of activity values asynchronously
        activityRepository.getTotalValueForRange(
                currentUserId,
                "Steps", // The specific activity type to sum
                startDate,
                endDate,
                new ActivityRepository.OnTotalValueLoadedListener() {
                    @Override
                    public void onTotalValueLoaded(float total) {
                        // Updates the UI on the main thread after the database query completes
                        runOnUiThread(() -> {
                            textViewWeeklySteps.setText(String.format(Locale.getDefault(),
                                    "Weekly Steps: %.0f", total));
                        });
                    }
                }
        );
    }
}