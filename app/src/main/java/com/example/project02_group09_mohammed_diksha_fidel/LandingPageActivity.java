package com.example.project02_group09_mohammed_diksha_fidel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project02_group09_mohammed_diksha_fidel.session.SessionManager;

public class LandingPageActivity extends AppCompatActivity {

    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        session = new SessionManager(this);

        TextView tvWelcome = findViewById(R.id.tvWelcome);
        Button btnAdmin = findViewById(R.id.btnAdmin);
        Button btnLogout = findViewById(R.id.btnLogout);
        Button buttonOpenChallenges = findViewById(R.id.button_open_challenges);

        tvWelcome.setText("Welcome, " + session.getUsername());

        if (session.isAdmin()) {
            btnAdmin.setVisibility(View.VISIBLE);
        } else {
            btnAdmin.setVisibility(View.GONE);
        }

        btnAdmin.setOnClickListener(v ->
                startActivity(new Intent(LandingPageActivity.this, AdminManagerActivity.class))
        );

        buttonOpenChallenges.setOnClickListener(v ->
                startActivity(new Intent(LandingPageActivity.this, ChallengesActivity.class))
        );

        btnLogout.setOnClickListener(v -> {
            session.logout();
            Intent intent = new Intent(LandingPageActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}
