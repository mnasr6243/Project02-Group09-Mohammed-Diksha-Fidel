package com.example.project02_group09_mohammed_diksha_fidel.repository;

import android.app.Application;

import com.example.project02_group09_mohammed_diksha_fidel.data.AppDatabase;
import com.example.project02_group09_mohammed_diksha_fidel.data.Participation;
import com.example.project02_group09_mohammed_diksha_fidel.data.ParticipationDao;

import java.util.List;

public class ParticipationRepository {

    public interface OnJoinResultListener {
        void onAlreadyJoined(int participantCount);
        void onJoined(int participantCount);
    }

    public interface OnLeaveResultListener {
        void onLeft(int participantCount);
    }

    public interface OnJoinedIdsLoadedListener {
        void onIdsLoaded(List<Integer> ids);
    }

    private final ParticipationDao participationDao;

    public ParticipationRepository(Application application) {
        AppDatabase db = AppDatabase.get(application);
        participationDao = db.participationDao();
    }

    // Join a challenge and return the updated participant count
    public void joinChallenge(int userId, int challengeId, OnJoinResultListener listener) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            int countForUser = participationDao.countForUserAndChallenge(userId, challengeId);
            if (countForUser > 0) {
                int total = participationDao.countParticipantsForChallenge(challengeId);
                listener.onAlreadyJoined(total);
            } else {
                Participation p = new Participation(userId, challengeId, System.currentTimeMillis());
                participationDao.insert(p);
                int total = participationDao.countParticipantsForChallenge(challengeId);
                listener.onJoined(total);
            }
        });
    }

    // Leave a challenge and return updated participant count
    public void leaveChallenge(int userId, int challengeId, OnLeaveResultListener listener) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            participationDao.deleteByUserAndChallenge(userId, challengeId);
            int total = participationDao.countParticipantsForChallenge(challengeId);
            listener.onLeft(total);
        });
    }

    public void getJoinedChallengeIds(int userId, OnJoinedIdsLoadedListener listener) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            List<Integer> ids = participationDao.getChallengeIdsForUser(userId);
            listener.onIdsLoaded(ids);
        });
    }
}