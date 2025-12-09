package com.example.project02_group09_mohammed_diksha_fidel;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project02_group09_mohammed_diksha_fidel.session.SessionManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // CRITICAL FIX: Combine session check with Admin redirection logic (Teammate's logic)
        SessionManager session = new SessionManager(this);
        if (session.isLoggedIn()) {
            if (session.isAdmin()) {
                startActivity(new Intent(MainActivity.this, AdminManagerActivity.class));
            } else {
                startActivity(new Intent(MainActivity.this, LandingPageActivity.class));
            }
            finish(); // block back button
            return;
        }

        // Load the main layout
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
}