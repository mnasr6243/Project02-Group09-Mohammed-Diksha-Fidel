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


    public Integer createdByUserId;
}
