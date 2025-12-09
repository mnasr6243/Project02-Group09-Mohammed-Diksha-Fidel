package com.example.project02_group09_mohammed_diksha_fidel.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "challenges")
public class Challenge {

    @PrimaryKey(autoGenerate = true)
    public int challengeId;

    @NonNull
    public String title;

    public String description;

    public int goalType;
    public int duration;

    // Optional field from the original structure
    public Integer createdByUserId;

    // Default no-argument constructor (required by Room)
    public Challenge() {
        // Empty constructor
    }

    public Challenge(String title, String description, int goalType, int duration) {
        this.title = title;
        this.description = description;
        this.goalType = goalType;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}