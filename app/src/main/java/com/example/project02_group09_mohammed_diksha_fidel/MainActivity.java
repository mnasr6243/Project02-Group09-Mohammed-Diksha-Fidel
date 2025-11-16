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

        SessionManager session = new SessionManager(this);
        if (session.isLoggedIn()) {
            startActivity(new Intent(this, LandingPageActivity.class));
            finish();  // Optional: prevent back navigation
            return;
        }

        setContentView(R.layout.activity_main);

        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnAdmin = findViewById(R.id.btnAdmin);
        Button btnChallenges = findViewById(R.id.btnChallenges);

        if (btnLogin != null) {
            btnLogin.setOnClickListener(v ->
                    startActivity(new Intent(this, LoginActivity.class))
            );
        }

        if (btnAdmin != null) {
            btnAdmin.setOnClickListener(v ->
                    startActivity(new Intent(this, AdminManagerActivity.class))
            );
        }

        if (btnChallenges != null) {
            btnChallenges.setOnClickListener(v ->
                    startActivity(new Intent(this, ChallengesActivity.class))
            );
        }
    }
}