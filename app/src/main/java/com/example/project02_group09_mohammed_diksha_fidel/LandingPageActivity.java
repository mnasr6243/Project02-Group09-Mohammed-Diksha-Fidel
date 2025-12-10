package com.example.project02_group09_mohammed_diksha_fidel;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page); // Sets the custom dashboard layout

        // Initializes the session manager to retrieve user data
        session = new SessionManager(this);

        // Connects UI elements to Java variables
        TextView tvWelcome = findViewById(R.id.tvWelcome); // Using Teammate's variable name
        Button btnAdmin = findViewById(R.id.btnAdmin);
        Button btnDailyLog = findViewById(R.id.btnDailyLog);
        Button btnStats = findViewById(R.id.btnStats);
        Button btnLogout = findViewById(R.id.btnLogout);
        Button buttonOpenChallenges = findViewById(R.id.button_open_challenges);

        // Displays the personalized welcome message
        tvWelcome.setText("Welcome " + session.getUsername());

        // Shows the Admin button only if the current user has admin privileges
        if (session.isAdmin()) {
            btnAdmin.setVisibility(View.VISIBLE);
        } else {
            btnAdmin.setVisibility(View.GONE);
        }

        // --- NAVIGATION & FUNCTIONALITY ---

        // Listener to navigate to the Daily Log screen
        btnDailyLog.setOnClickListener(v -> {
            startActivity(new Intent(this, DailyLogActivity.class));
        });

        // Listener to navigate to the Statistics screen
        btnStats.setOnClickListener(v -> {
            startActivity(new Intent(this, StatsActivity.class));
        });


        // Listener to navigate to the Admin Manager screen
        btnAdmin.setOnClickListener(v ->
                startActivity(new Intent(LandingPageActivity.this, AdminManagerActivity.class))
        );

        buttonOpenChallenges.setOnClickListener(v ->
                startActivity(new Intent(LandingPageActivity.this, ChallengesActivity.class))
        );

        // Listener to handle Logout functionality
        btnLogout.setOnClickListener(v -> {
            session.logout(); // Use the standardized logout method

            // CRITICAL FIX: Use the robust logout navigation (Teammate's logic)
            Intent intent = new Intent(LandingPageActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }

    // CRITICAL FIX: Keeps the clean exit logic for the back button (Your logic)
    @Override
    public void onBackPressed() {
        finishAffinity(); // Closes this activity and all parent activities, effectively exiting the app
    }

}