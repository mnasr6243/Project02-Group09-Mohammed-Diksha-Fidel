package com.example.project02_group09_mohammed_diksha_fidel.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ParticipationDao {

    @Insert
    long insert(Participation participation);

    @Query("SELECT COUNT(*) FROM participations WHERE userId = :userId AND challengeId = :challengeId")
    int countForUserAndChallenge(int userId, int challengeId);

    // NEW: Remove a user's participation in a challenge
    @Query("DELETE FROM participations WHERE userId = :userId AND challengeId = :challengeId")
    void deleteByUserAndChallenge(int userId, int challengeId);

    // NEW: Count total participants in a challenge
    @Query("SELECT COUNT(*) FROM participations WHERE challengeId = :challengeId")
    int countParticipantsForChallenge(int challengeId);

    // NEW: Get all challenge IDs a user has joined
    @Query("SELECT challengeId FROM participations WHERE userId = :userId")
    List<Integer> getChallengeIdsForUser(int userId);
}