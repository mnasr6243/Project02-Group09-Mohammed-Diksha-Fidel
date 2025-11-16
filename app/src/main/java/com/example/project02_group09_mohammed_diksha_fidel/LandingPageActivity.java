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

        TextView tv = findViewById(R.id.tvWelcome);
        Button btnAdmin = findViewById(R.id.btnAdmin);
        Button btnLogout = findViewById(R.id.btnLogout);

        tv.setText("Welcome " + session.getUsername());

        if (session.isAdmin()) {
            btnAdmin.setVisibility(View.VISIBLE);
        }

        btnAdmin.setOnClickListener(v ->
                startActivity(new Intent(this, AdminManagerActivity.class))
        );

        btnLogout.setOnClickListener(v -> {
            session.clear();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }
}