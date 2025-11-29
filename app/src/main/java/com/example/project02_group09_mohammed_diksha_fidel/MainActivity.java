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

        // 1. Check if user already logged in using SharedPreferences
        SessionManager session = new SessionManager(this);
        if (session.isLoggedIn()) {
            startActivity(new Intent(MainActivity.this, LandingPageActivity.class));
            finish(); // block back button to avoid returning to Main
            return;
        }

        // 2. Load the correct layout for MainActivity
        setContentView(R.layout.activity_main);

        // Buttons required by assignment:
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnCreateAccount = findViewById(R.id.btnCreateAccount);

        // 3. Login button → LoginActivity
        btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        // 4. Create Account button (can be placeholder for now)
        btnCreateAccount.setOnClickListener(v -> {
            // OPTIONAL for Part 02, can be implemented later
            // For now, show login page or toast
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        });
    }
}