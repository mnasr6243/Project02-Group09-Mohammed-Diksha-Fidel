package com.example.project02_group09_mohammed_diksha_fidel;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project02_group09_mohammed_diksha_fidel.data.AppDatabase;
import com.example.project02_group09_mohammed_diksha_fidel.data.Challenge;
import com.example.project02_group09_mohammed_diksha_fidel.data.ChallengeDao;

public class AddChallengeActivity extends AppCompatActivity {

    private EditText etTitle;
    private EditText etDescription;
    private EditText etDuration;
    private ChallengeDao challengeDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_challenge);

        challengeDao = AppDatabase.get(this).challengeDao();

        etTitle = findViewById(R.id.etChallengeTitle);
        etDescription = findViewById(R.id.etChallengeDescription);
        etDuration = findViewById(R.id.etChallengeDuration);
        Button btnSave = findViewById(R.id.btnSaveChallenge);
        Button btnCancel = findViewById(R.id.btnCancelChallenge);

        btnSave.setOnClickListener(v -> saveChallenge());
        btnCancel.setOnClickListener(v -> finish());
    }

    private void saveChallenge() {
        String title = etTitle.getText().toString().trim();
        String desc = etDescription.getText().toString().trim();
        String durationStr = etDuration.getText().toString().trim();

        if (title.isEmpty() || desc.isEmpty() || durationStr.isEmpty()) {
            Toast.makeText(this, "All fields are required.", Toast.LENGTH_SHORT).show();
            return;
        }

        int days;
        try {
            days = Integer.parseInt(durationStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Duration must be a number.", Toast.LENGTH_SHORT).show();
            return;
        }

        Challenge challenge = new Challenge(title, desc, days);

        AppDatabase.databaseWriteExecutor.execute(() -> {
            challengeDao.insert(challenge);
            runOnUiThread(() -> {
                Toast.makeText(this, "Challenge added!", Toast.LENGTH_SHORT).show();
                finish();
            });
        });
    }
}