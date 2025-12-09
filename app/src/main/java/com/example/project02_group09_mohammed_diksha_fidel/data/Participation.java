package com.example.project02_group09_mohammed_diksha_fidel.data;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "participations",
        foreignKeys = @ForeignKey(entity = Challenge.class,
                parentColumns = "challengeId",
                childColumns = "challengeId",
                onDelete = ForeignKey.CASCADE),
        indices = {@Index("challengeId")})
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
