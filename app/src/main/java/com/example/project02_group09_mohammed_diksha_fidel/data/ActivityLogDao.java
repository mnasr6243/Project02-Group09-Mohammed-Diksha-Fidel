package com.example.project02_group09_mohammed_diksha_fidel.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ActivityLogDao {

    @Insert
    void insert(ActivityLog log);

    // 1. Query for Daily Log Activity
    @Query("SELECT * FROM activitylog WHERE userId = :userId AND timestamp BETWEEN :startOfDay AND :endOfDay ORDER BY timestamp DESC")
    List<ActivityLog> getLogsForDay(int userId, long startOfDay, long endOfDay); // Must be correct

    // 2. Query for Stats Activity
    @Query("SELECT SUM(value) FROM activitylog WHERE userId = :userId AND type = :activityType AND timestamp BETWEEN :startDate AND :endDate")
    float getTotalValueForDateRange(int userId, String activityType, long startDate, long endDate);
}