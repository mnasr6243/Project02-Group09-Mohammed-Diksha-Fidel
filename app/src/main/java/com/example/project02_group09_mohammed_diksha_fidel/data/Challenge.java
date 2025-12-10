package com.example.project02_group09_mohammed_diksha_fidel.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "challenges")
public class Challenge {

    @PrimaryKey(autoGenerate = true)
    private int challengeId;

    private final String title;
    private final String description;
    private final int durationDays; // e.g. 7, 14, 30

    public Challenge(String title, String description, int durationDays) {
        this.title = title;
        this.description = description;
        this.durationDays = durationDays;
    }

    public int getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(int challengeId) {
        this.challengeId = challengeId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getDurationDays() {
        return durationDays;
    }
}