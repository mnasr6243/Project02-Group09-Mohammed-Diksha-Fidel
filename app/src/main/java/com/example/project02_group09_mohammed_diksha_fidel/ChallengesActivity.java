package com.example.project02_group09_mohammed_diksha_fidel;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project02_group09_mohammed_diksha_fidel.data.AppDatabase;
import com.example.project02_group09_mohammed_diksha_fidel.data.Challenge;
import com.example.project02_group09_mohammed_diksha_fidel.data.ChallengeDao;
import com.example.project02_group09_mohammed_diksha_fidel.data.Participation;
import com.example.project02_group09_mohammed_diksha_fidel.data.ParticipationDao;
import com.example.project02_group09_mohammed_diksha_fidel.session.SessionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ChallengesActivity extends AppCompatActivity {

    private ChallengeDao challengeDao;
    private ParticipationDao participationDao;
    private Executor executor = Executors.newSingleThreadExecutor();

    private ListView listViewChallenges;
    private Button buttonJoin;
    private Button buttonBack;

    private List<Challenge> challengeList = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    private int currentUserId = -1;
    private Challenge selectedChallenge = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenges);

        AppDatabase db = AppDatabase.get(getApplicationContext());
        challengeDao = db.challengeDao();
        participationDao = db.participationDao();

        SessionManager sessionManager = new SessionManager(this);
        currentUserId = sessionManager.getUserId();

        initViews();
        setupListeners();
        loadChallenges();
    }

    private void initViews() {
        listViewChallenges = findViewById(R.id.list_challenges);
        buttonJoin = findViewById(R.id.button_join_challenge);
        buttonBack = findViewById(R.id.button_back);
    }

    private void setupListeners() {
        listViewChallenges.setOnItemClickListener((parent, view, position, id) -> {
            if (position >= 0 && position < challengeList.size()) {
                selectedChallenge = challengeList.get(position);
                updateJoinButtonState();
            }
        });

        buttonJoin.setOnClickListener(v -> joinChallenge());
        buttonBack.setOnClickListener(v -> finish());
    }

    private void loadChallenges() {
        executor.execute(() -> {
            challengeList = challengeDao.getAllChallenges();
            List<String> titles = new ArrayList<>();
            for (Challenge c : challengeList) {
                titles.add(c.title);
            }

            runOnUiThread(() -> {
                adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, titles);
                listViewChallenges.setAdapter(adapter);
                listViewChallenges.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                buttonJoin.setEnabled(false); // Disabled until a challenge is selected
            });
        });
    }

    private void updateJoinButtonState() {
        if (selectedChallenge == null) {
            buttonJoin.setEnabled(false);
            return;
        }
        executor.execute(() -> {
            Participation existing = participationDao.findByUserIdAndChallengeId(
                    currentUserId,
                    selectedChallenge.challengeId
            );
            runOnUiThread(() -> buttonJoin.setEnabled(existing == null));
        });
    }

    private void joinChallenge() {
        if (selectedChallenge == null) {
            Toast.makeText(this, "Please select a challenge first", Toast.LENGTH_SHORT).show();
            return;
        }

        executor.execute(() -> {
            Participation existing = participationDao.findByUserIdAndChallengeId(
                    currentUserId,
                    selectedChallenge.challengeId
            );

            if (existing != null) {
                runOnUiThread(() -> Toast.makeText(this, "You have already joined this challenge", Toast.LENGTH_SHORT).show());
            } else {
                Participation p = new Participation();
                p.userId = currentUserId;
                p.challengeId = selectedChallenge.challengeId;
                p.status = "joined";
                p.dateUpdated = System.currentTimeMillis();
                participationDao.insertParticipation(p);

                runOnUiThread(() -> {
                    Toast.makeText(this, "Joined challenge: " + selectedChallenge.title, Toast.LENGTH_SHORT).show();
                    buttonJoin.setEnabled(false);
                });
            }
        });
    }
}
