package com.example.project02_group09_mohammed_diksha_fidel.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// This makes the class a database table named 'activitylog'
@Entity(tableName = "activitylog")
public class ActivityLog {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "userId")
    private int userId; // Links to the User table

    @ColumnInfo(name = "type")
    private String type; // Like "Steps" or "Water"

    @ColumnInfo(name = "value")
    private float value; // The amount, like 5000 steps

    @ColumnInfo(name = "timestamp")
    private long timestamp; // Time the activity was recorded

    // Existing constructor used in tests (kept for compatibility)
    public ActivityLog(int id, int userId, String type, float value, long timestamp) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.value = value;
        this.timestamp = timestamp;
    }

    // New convenience constructor (for inserts where ID is auto-generated)
    public ActivityLog(int userId, String type, float value, long timestamp) {
        this(0, userId, type, value, timestamp);
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public float getValue() { return value; }
    public void setValue(float value) { this.value = value; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}