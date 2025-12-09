package com.example.project02_group09_mohammed_diksha_fidel;

import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.Toast;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AdminManagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Removed commented-out system bar code for cleanliness
        setContentView(R.layout.activity_admin_manager);

        Button viewChallenges = findViewById(R.id.btnViewChallenges);
        Button addChallenge   = findViewById(R.id.btnAddChallenge);
        Button backToMain     = findViewById(R.id.btnBackToMain);

        viewChallenges.setOnClickListener(v -> {
            // Go to Challenges screen
            Intent intent = new Intent(AdminManagerActivity.this, ChallengesActivity.class);
            startActivity(intent);
        });

        // addChallenge feature
        addChallenge.setOnClickListener(v -> {
            Intent intent = new Intent(AdminManagerActivity.this, AddChallengeActivity.class);
            startActivity(intent);
        });

        backToMain.setOnClickListener(v -> {
            // Go back to the main screen
            Intent intent = new Intent(AdminManagerActivity.this, MainActivity.class);
            startActivity(intent);
            finish();

        });
    }
}