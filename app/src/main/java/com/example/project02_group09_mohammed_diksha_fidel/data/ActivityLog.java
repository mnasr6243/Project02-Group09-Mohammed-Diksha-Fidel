package com.example.project02_group09_mohammed_diksha_fidel.data;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "activity_log",
        foreignKeys = @ForeignKey(
                entity = User.class,
                parentColumns = "userId",
                childColumns = "userId",
                onDelete = ForeignKey.CASCADE
        )
)
public class ActivityLog {

    @PrimaryKey(autoGenerate = true)
    public int id;

    // Link to the User who owns this log
    public int userId;

    // Activity being tracked
    public String activityType;

    // Value recorded (float)
    public float value;

    // Timestamp
    public long timestamp;

    @Ignore
    public ActivityLog() {}

    //  constructor
    public ActivityLog(int id, int userId, String activityType, float value, long timestamp) {
        this.id = id;
        this.userId = userId;
        this.activityType = activityType;
        this.value = value;
        this.timestamp = timestamp;
    }

    // Getters / Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getActivityType() { return activityType; }
    public void setActivityType(String activityType) { this.activityType = activityType; }

    public float getValue() { return value; }
    public void setValue(float value) { this.value = value; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}
