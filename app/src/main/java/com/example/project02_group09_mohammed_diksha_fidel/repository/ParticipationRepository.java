package com.example.project02_group09_mohammed_diksha_fidel.repository;

import android.app.Application;

import com.example.project02_group09_mohammed_diksha_fidel.data.AppDatabase;
import com.example.project02_group09_mohammed_diksha_fidel.data.Participation;
import com.example.project02_group09_mohammed_diksha_fidel.data.ParticipationDao;

import java.util.List;

public class ParticipationRepository {

    public interface OnJoinResultListener {
        void onAlreadyJoined();
        void onJoined();
    }

    public interface OnJoinedIdsLoadedListener {
        void onIdsLoaded(List<Integer> ids);
    }

    private final ParticipationDao participationDao;

    public ParticipationRepository(Application application) {
        AppDatabase db = AppDatabase.get(application);
        participationDao = db.participationDao();
    }

    public void joinChallenge(int userId, int challengeId, OnJoinResultListener listener) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            int count = participationDao.countForUserAndChallenge(userId, challengeId);
            if (count > 0) {
                listener.onAlreadyJoined();
            } else {
                Participation p = new Participation(userId, challengeId, System.currentTimeMillis());
                participationDao.insert(p);
                listener.onJoined();
            }
        });
    }

    public void getJoinedChallengeIds(int userId, OnJoinedIdsLoadedListener listener) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            List<Integer> ids = participationDao.getChallengeIdsForUser(userId);
            listener.onIdsLoaded(ids);
        });
    }
}