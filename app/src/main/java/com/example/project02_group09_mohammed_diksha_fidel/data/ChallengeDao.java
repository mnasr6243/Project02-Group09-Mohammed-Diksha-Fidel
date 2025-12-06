package com.example.project02_group09_mohammed_diksha_fidel.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ChallengeDao {

    @Query("SELECT * FROM challenges")
    List<Challenge> getAllChallenges();

    @Insert
    long insertChallenge(Challenge challenge);

    @Update
    int updateChallenge(Challenge challenge);

    @Delete
    int deleteChallenge(Challenge challenge);
}
