package com.example.project02_group09_mohammed_diksha_fidel.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ParticipationDao {

    @Query("SELECT * FROM participations WHERE userId = :userId")
    List<Participation> getParticipationForUser(int userId);

    @Insert
    long insertParticipation(Participation participation);
}
