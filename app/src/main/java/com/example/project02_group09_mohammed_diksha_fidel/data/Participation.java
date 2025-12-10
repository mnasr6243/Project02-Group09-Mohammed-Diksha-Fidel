package com.example.project02_group09_mohammed_diksha_fidel.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "participations")
public class Participation {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private final int userId;
    private final int challengeId;
    private final long joinedAt;

    public Participation(int userId, int challengeId, long joinedAt) {
        this.userId = userId;
        this.challengeId = challengeId;
        this.joinedAt = joinedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public int getChallengeId() {
        return challengeId;
    }

    public long getJoinedAt() {
        return joinedAt;
    }
}