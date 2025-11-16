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
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_manager);

        //View root = findViewById(R.id.main);
        //ViewCompat.setOnApplyWindowInsetsListener(root, (v, insets) -> {
            //Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            //v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            //return insets;
        //});

        Button viewChallenges = findViewById(R.id.btnViewChallenges);
        Button addChallenge   = findViewById(R.id.btnAddChallenge);
        Button backToMain     = findViewById(R.id.btnBackToMain);

        viewChallenges.setOnClickListener(v -> {
            Intent intent = new Intent(AdminManagerActivity.this, ChallengesActivity.class);
            startActivity(intent);
        });

        addChallenge.setOnClickListener(v ->
                Toast.makeText(this, "Add Challenge feature coming soon", Toast.LENGTH_SHORT).show()
        );

        backToMain.setOnClickListener(v -> {
            Intent intent = new Intent(AdminManagerActivity.this, MainActivity.class);
            startActivity(intent);
            finish();

        });
    }
}