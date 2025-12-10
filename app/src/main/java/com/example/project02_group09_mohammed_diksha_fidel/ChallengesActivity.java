package com.example.project02_group09_mohammed_diksha_fidel;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project02_group09_mohammed_diksha_fidel.adapters.ChallengeAdapter;
import com.example.project02_group09_mohammed_diksha_fidel.data.Challenge;
import com.example.project02_group09_mohammed_diksha_fidel.repository.ChallengeRepository;
import com.example.project02_group09_mohammed_diksha_fidel.repository.ParticipationRepository;
import com.example.project02_group09_mohammed_diksha_fidel.session.SessionManager;

import java.util.List;

public class ChallengesActivity extends AppCompatActivity {

    private ChallengeAdapter adapter;
    private ChallengeRepository challengeRepository;
    private ParticipationRepository participationRepository;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenges);

        adapter = new ChallengeAdapter();
        challengeRepository = new ChallengeRepository(getApplication());
        participationRepository = new ParticipationRepository(getApplication());
        sessionManager = new SessionManager(this);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewChallenges);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        Button btnBack = findViewById(R.id.btnBackChallenges);
        btnBack.setOnClickListener(v -> finish());

        adapter.setOnJoinClickListener(challenge -> {
            int userId = sessionManager.getCurrentUserId();
            participationRepository.joinChallenge(userId, challenge.getChallengeId(),
                    new ParticipationRepository.OnJoinResultListener() {
                        @Override
                        public void onAlreadyJoined() {
                            runOnUiThread(() ->
                                    Toast.makeText(ChallengesActivity.this,
                                            "You already joined this challenge.",
                                            Toast.LENGTH_SHORT).show()
                            );
                        }

                        @Override
                        public void onJoined() {
                            runOnUiThread(() -> {
                                Toast.makeText(ChallengesActivity.this,
                                        "Challenge joined!",
                                        Toast.LENGTH_SHORT).show();
                                loadJoinedChallenges();
                            });
                        }
                    });
        });

        loadChallenges();
        loadJoinedChallenges();
    }

    private void loadChallenges() {
        challengeRepository.getAllChallenges(new ChallengeRepository.OnChallengesLoadedListener() {
            @Override
            public void onChallengesLoaded(List<Challenge> challenges) {
                runOnUiThread(() -> adapter.setChallenges(challenges));
            }
        });
    }

    private void loadJoinedChallenges() {
        int userId = sessionManager.getCurrentUserId();
        participationRepository.getJoinedChallengeIds(userId, ids ->
                runOnUiThread(() -> adapter.setJoinedChallengeIds(ids))
        );
    }
}