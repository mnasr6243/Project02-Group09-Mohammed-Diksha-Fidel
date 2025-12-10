package com.example.project02_group09_mohammed_diksha_fidel;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project02_group09_mohammed_diksha_fidel.session.SessionManager;

// Imports required for the Notifications/Broadcast feature and Permissions
import android.app.AlarmManager;
import android.app.PendingIntent;
import java.util.Calendar;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1001; // Unique ID for the permission request

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. Request Notification Permission (Must happen before scheduling)
        requestNotificationPermission();

        // 2. SCHEDULE NOTIFICATION
        scheduleDailyNotification();

        // CRITICAL FIX: Combine session check with Admin redirection logic (Teammate's logic)
        SessionManager session = new SessionManager(this);
        if (session.isLoggedIn()) {
            if (session.isAdmin()) {
                // If logged in as Admin, go to Admin Manager
                startActivity(new Intent(MainActivity.this, AdminManagerActivity.class));
            } else {
                // If logged in as regular User, go to Landing Page
                startActivity(new Intent(MainActivity.this, LandingPageActivity.class));
            }
            finish(); // block back button
            return;
        }

        // Load the main layout if not logged in
        setContentView(R.layout.activity_main);

        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnCreateAccount = findViewById(R.id.btnCreateAccount);

        // Login button sends to the login screen
        btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        // CRITICAL FIX: Create Account button sends to the dedicated creation screen (Your logic)
        btnCreateAccount.setOnClickListener(v -> {
            Intent intent = new Intent(this, CreateAccountActivity.class);
            startActivity(intent);
        });
    }

    /**
     * Checks and requests the POST_NOTIFICATIONS permission required for Android 13+.
     */
    private void requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // API 33+
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {

                // Permission is not granted, request it from the user
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.POST_NOTIFICATIONS},
                        PERMISSION_REQUEST_CODE);
            }
        }
    }


    /**
     * Schedules a repeating daily notification at 7:10 PM using AlarmManager
     * that is handled by NotificationReceiver.
     */
    private void scheduleDailyNotification() {
        Intent intent = new Intent(this, NotificationReceiver.class);

        // Use FLAG_IMMUTABLE as required for API 31+
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        // Set time for 7:10 PM using the 12-hour clock
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        // Using Calendar.HOUR for 12-hour clock (1 to 12)
        calendar.set(Calendar.HOUR, 7);
        calendar.set(Calendar.MINUTE, 12);
        calendar.set(Calendar.SECOND, 0);

        // Specify PM using Calendar.AM_PM
        calendar.set(Calendar.AM_PM, Calendar.PM);

        // If the scheduled time has already passed today, set it for the next day
        if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
            calendar.add(Calendar.DATE, 1);
        }

        if (alarmManager != null) {
            // Set the repeating alarm (fires daily)
            alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP, // Wakes up the device to fire the intent
                    calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, // Repeat every 24 hours
                    pendingIntent
            );
        }
    }
}