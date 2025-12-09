package com.example.project02_group09_mohammed_diksha_fidel;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project02_group09_mohammed_diksha_fidel.adapters.ChallengeAdapter;
import com.example.project02_group09_mohammed_diksha_fidel.data.Challenge;
import com.example.project02_group09_mohammed_diksha_fidel.repository.ChallengeRepository;

import java.util.List;

public class ChallengesActivity extends AppCompatActivity {

    private ChallengeAdapter adapter;
    private ChallengeRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenges);

        adapter = new ChallengeAdapter();
        repository = new ChallengeRepository(getApplication());

        RecyclerView recyclerView = findViewById(R.id.recyclerViewChallenges);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        Button btnBack = findViewById(R.id.btnBackChallenges);
        btnBack.setOnClickListener(v -> finish());

        loadChallenges();
    }

    private void loadChallenges() {
        repository.getAllChallenges(new ChallengeRepository.OnChallengesLoadedListener() {
            @Override
            public void onChallengesLoaded(List<Challenge> challenges) {
                runOnUiThread(() -> adapter.setChallenges(challenges));
            }
        });
    }
}