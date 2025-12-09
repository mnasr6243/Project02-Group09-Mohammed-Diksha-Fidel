package com.example.project02_group09_mohammed_diksha_fidel.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface ActivityLogDao {
    @Insert
    void insert(ActivityLog log);

    @Query("SELECT * FROM activity_log WHERE userId = :userId AND timestamp BETWEEN :startOfDay AND :endOfDay ORDER BY timestamp DESC")
    List<ActivityLog> getDailyLogsForUser(int userId, long startOfDay, long endOfDay);

    // For Stats Screen: Get total values for a type across a date range
    @Query("SELECT SUM(value) FROM activity_log WHERE userId = :userId AND activityType = :type AND timestamp BETWEEN :startDate AND :endDate")
    float getTotalValueForDateRange(int userId, String type, long startDate, long endDate);

    // Add other methods as needed (e.g., getting all unique activity types)
}