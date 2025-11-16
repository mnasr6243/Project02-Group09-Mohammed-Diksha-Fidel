package com.example.project02_group09_mohammed_diksha_fidel;

import android.util.Log;
import android.widget.Toast;
import android.widget.Button;
import android.content.Intent;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    // TODO: Implement Admin Manager + Challenges feature heres

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Button btnAdmin = findViewById(R.id.btnAdmin);
        Button btnChallenges = findViewById(R.id.btnChallenges);

        btnAdmin.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Admin clicked", Toast.LENGTH_SHORT).show();
            Log.d("MainActivity", "Admin button clicked");
            startActivity(new Intent(MainActivity.this, AdminManagerActivity.class));
        });

        btnChallenges.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Challenges clicked", Toast.LENGTH_SHORT).show();
            Log.d("MainActivity", "Challenges button clicked");
            startActivity(new Intent(MainActivity.this, ChallengesActivity.class));
        });
    }
}