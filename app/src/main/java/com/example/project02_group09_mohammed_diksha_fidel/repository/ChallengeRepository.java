package com.example.project02_group09_mohammed_diksha_fidel.repository;

import android.app.Application;

import com.example.project02_group09_mohammed_diksha_fidel.data.AppDatabase;
import com.example.project02_group09_mohammed_diksha_fidel.data.Challenge;
import com.example.project02_group09_mohammed_diksha_fidel.data.ChallengeDao;

import java.util.List;

public class ChallengeRepository {

    private final ChallengeDao challengeDao;

    public ChallengeRepository(Application application) {
        AppDatabase db = AppDatabase.get(application);
        challengeDao = db.challengeDao();
    }

    public void getAllChallenges(OnChallengesLoadedListener listener) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            List<Challenge> list = challengeDao.getAllChallenges();
            listener.onChallengesLoaded(list);
        });
    }

    public interface OnChallengesLoadedListener {
        void onChallengesLoaded(List<Challenge> challenges);
    }
}