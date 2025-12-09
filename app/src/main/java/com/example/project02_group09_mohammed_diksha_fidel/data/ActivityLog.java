package com.example.project02_group09_mohammed_diksha_fidel.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// This makes the class a database table named 'activitylog'
@Entity(tableName = "activitylog")
public class ActivityLog {

    // Primary Key (Room generates the ID automatically)
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "userId")
    public int userId; // Links to the User table

    @ColumnInfo(name = "type")
    public String type; // Like "Steps" or "Water"

    @ColumnInfo(name = "value")
    public float value; // The amount, like 5000 steps

    @ColumnInfo(name = "timestamp")
    public long timestamp; // Time the activity was recorded

    // Constructor
    public ActivityLog(int userId, String type, float value, long timestamp) {
        this.userId = userId;
        this.type = type;
        this.value = value;
        this.timestamp = timestamp;
    }

    // Getters for accessing the data
    public String getType() { return type; }
    public float getValue() { return value; }
    public long getTimestamp() { return timestamp; }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}