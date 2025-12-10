package com.example.project02_group09_mohammed_diksha_fidel;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.project02_group09_mohammed_diksha_fidel.session.SessionManager;
import com.example.project02_group09_mohammed_diksha_fidel.session.DailyLogActivity;
import com.example.project02_group09_mohammed_diksha_fidel.session.StatsActivity;

// This is the user's main dashboard after successful login.
public class LandingPageActivity extends AppCompatActivity {
    private SessionManager session;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page); // Sets the custom dashboard layout

        // Initializes the session manager to retrieve user data
        session = new SessionManager(this);

        // Connects UI elements to Java variables
        TextView tv = findViewById(R.id.tvWelcome);
        Button btnAdmin = findViewById(R.id.btnAdmin);
        Button btnDailyLog = findViewById(R.id.btnDailyLog);
        Button btnStats = findViewById(R.id.btnStats);
        Button btnLogout = findViewById(R.id.btnLogout);

        // Displays the personalized welcome message using the logged-in username
        tv.setText("Welcome " + session.getUsername());

        // Shows the Admin button only if the current user has admin privileges
        if (session.isAdmin()) {
            btnAdmin.setVisibility(View.VISIBLE);
        }

        // --- NAVIGATION & FUNCTIONALITY ---

        // Listener to navigate to the Daily Log screen
        btnDailyLog.setOnClickListener(v -> startActivity(new Intent(this, DailyLogActivity.class)));

        // Listener to navigate to the Statistics screen
        btnStats.setOnClickListener(v -> startActivity(new Intent(this, StatsActivity.class)));

        Button btnChallenges = findViewById(R.id.btnChallenges);
        btnChallenges.setOnClickListener(v -> startActivity(new Intent(this, ChallengesActivity.class)));


        // Listener to navigate to the Admin Manager screen
        btnAdmin.setOnClickListener(v ->
                startActivity(new Intent(this, AdminManagerActivity.class))
        );

        // Listener to handle Logout functionality
        btnLogout.setOnClickListener(v -> {
            session.clear(); // Clears user session data
            startActivity(new Intent(this, MainActivity.class)); // Navigates back to the main login/create screen
            finish(); // Closes the current dashboard activity
        });
    }

    // Ensures the device's physical back button exits the app cleanly from the dashboard
    @SuppressLint("GestureBackNavigation")
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity(); // Closes this activity and all parent activities, effectively exiting the app
    }
}