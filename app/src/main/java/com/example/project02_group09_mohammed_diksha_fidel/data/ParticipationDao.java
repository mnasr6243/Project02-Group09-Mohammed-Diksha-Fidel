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

    @Query("SELECT challengeId FROM participations WHERE userId = :userId")
    List<Integer> getChallengeIdsForUser(int userId);

}