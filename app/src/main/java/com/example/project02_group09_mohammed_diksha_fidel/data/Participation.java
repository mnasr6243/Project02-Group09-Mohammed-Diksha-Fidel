package com.example.project02_group09_mohammed_diksha_fidel.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "participations")
public class Participation {

    @PrimaryKey(autoGenerate = true)
    public int participationId;

    public int userId;
    public int challengeId;

    // e.g. "joined", "completed"
    public String status;

    // e.g. System.currentTimeMillis()
    public long dateUpdated;
}
