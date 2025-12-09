package com.example.project02_group09_mohammed_diksha_fidel.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ParticipationDao {

    @Query("SELECT * FROM participations WHERE userId = :userId AND challengeId = :challengeId LIMIT 1")
    Participation findByUserIdAndChallengeId(int userId, int challengeId);

    @Insert
    long insertParticipation(Participation participation);

    // optional: other queries if you want
}
