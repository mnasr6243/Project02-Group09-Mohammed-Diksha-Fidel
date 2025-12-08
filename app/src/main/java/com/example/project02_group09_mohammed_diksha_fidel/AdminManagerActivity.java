package com.example.project02_group09_mohammed_diksha_fidel;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project02_group09_mohammed_diksha_fidel.data.AppDatabase;
import com.example.project02_group09_mohammed_diksha_fidel.data.Challenge;
import com.example.project02_group09_mohammed_diksha_fidel.data.ChallengeDao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AdminManagerActivity extends AppCompatActivity {

    private AppDatabase db;
    private ChallengeDao challengeDao;
    private Executor executor = Executors.newSingleThreadExecutor();

    private EditText editTitle;
    private EditText editDescription;
    private ListView listViewChallenges;
    private Button buttonAdd;
    private Button buttonUpdate;
    private Button buttonDelete;
    private Button buttonBack;

    private List<Challenge> challengeList = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private int selectedIndex = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manager);

        db = AppDatabase.get(getApplicationContext());
        challengeDao = db.challengeDao();

        initViews();
        loadChallenges();
        setupListeners();
    }

    private void initViews() {
        editTitle = findViewById(R.id.edit_challenge_title);
        editDescription = findViewById(R.id.edit_challenge_description);
        listViewChallenges = findViewById(R.id.list_admin_challenges);
        buttonAdd = findViewById(R.id.button_add_challenge);
        buttonUpdate = findViewById(R.id.button_update_challenge);
        buttonDelete = findViewById(R.id.button_delete_challenge);
        buttonBack = findViewById(R.id.button_back);
    }

    private void loadChallenges() {
        executor.execute(() -> {
            challengeList = challengeDao.getAllChallenges();
            List<String> titles = new ArrayList<>();
            for (Challenge c : challengeList) {
                titles.add(c.title);
            }

            runOnUiThread(() -> {
                adapter = new ArrayAdapter<>(
                        AdminManagerActivity.this,
                        android.R.layout.simple_list_item_activated_1,
                        titles
                );
                listViewChallenges.setAdapter(adapter);
                listViewChallenges.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                clearInputs();
            });
        });
    }

    private void setupListeners() {
        listViewChallenges.setOnItemClickListener((parent, view, position, id) -> {
            if (position >= 0 && position < challengeList.size()) {
                selectedIndex = position;
                Challenge c = challengeList.get(position);
                editTitle.setText(c.title);
                editDescription.setText(c.description);
            }
        });

        buttonAdd.setOnClickListener(v -> addChallenge());
        buttonUpdate.setOnClickListener(v -> updateChallenge());
        buttonDelete.setOnClickListener(v -> deleteChallenge());
        buttonBack.setOnClickListener(v -> finish());
    }

    private void addChallenge() {
        String title = editTitle.getText().toString().trim();
        String description = editDescription.getText().toString().trim();

        if (title.isEmpty()) {
            Toast.makeText(this, "Title is required", Toast.LENGTH_SHORT).show();
            return;
        }

        executor.execute(() -> {
            Challenge c = new Challenge();
            c.title = title;
            c.description = description;
            challengeDao.insertChallenge(c);
            runOnUiThread(this::loadChallenges);
        });
    }

    private void updateChallenge() {
        if (selectedIndex < 0 || selectedIndex >= challengeList.size()) {
            Toast.makeText(this, "Select a challenge to update", Toast.LENGTH_SHORT).show();
            return;
        }

        String title = editTitle.getText().toString().trim();
        String description = editDescription.getText().toString().trim();

        if (title.isEmpty()) {
            Toast.makeText(this, "Title is required", Toast.LENGTH_SHORT).show();
            return;
        }

        executor.execute(() -> {
            Challenge c = challengeList.get(selectedIndex);
            c.title = title;
            c.description = description;
            challengeDao.updateChallenge(c);
            runOnUiThread(this::loadChallenges);
        });
    }

    private void deleteChallenge() {
        if (selectedIndex < 0 || selectedIndex >= challengeList.size()) {
            Toast.makeText(this, "Select a challenge to delete", Toast.LENGTH_SHORT).show();
            return;
        }

        new AlertDialog.Builder(this)
                .setTitle("Delete Challenge")
                .setMessage("Are you sure you want to delete this challenge?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    executor.execute(() -> {
                        Challenge c = challengeList.get(selectedIndex);
                        challengeDao.deleteChallenge(c);
                        runOnUiThread(this::loadChallenges);
                    });
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void clearInputs() {
        editTitle.setText("");
        editDescription.setText("");
        listViewChallenges.clearChoices();
        selectedIndex = -1;
    }
}
