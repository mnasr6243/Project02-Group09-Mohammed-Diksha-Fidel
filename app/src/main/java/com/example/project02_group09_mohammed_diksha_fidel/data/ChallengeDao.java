package com.example.project02_group09_mohammed_diksha_fidel.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ChallengeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Challenge challenge);

    @Query("SELECT * FROM challenges ORDER BY challengeId DESC")
    List<Challenge> getAllChallenges();

    @Delete
    void delete(Challenge challenge);
}