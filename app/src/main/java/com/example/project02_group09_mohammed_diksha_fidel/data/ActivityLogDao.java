package com.example.project02_group09_mohammed_diksha_fidel.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ActivityLogDao {

    @Insert
    long insert(ActivityLog log);

    // Get logs for the Daily Log screen
    @Query("SELECT * FROM activitylog WHERE userId = :userId AND timestamp BETWEEN :startOfDay AND :endOfDay ORDER BY timestamp DESC")
    List<ActivityLog> getLogsForDay(int userId, long startOfDay, long endOfDay);

    // Get the total sum of values for the Stats screen
    @Query("SELECT SUM(value) FROM activitylog WHERE userId = :userId AND type = :activityType AND timestamp BETWEEN :startDate AND :endDate")
    float getTotalValueForDateRange(int userId, String activityType, long startDate, long endDate);

    // NEW: Fetch a single log by ID (for editing)
    @Query("SELECT * FROM activitylog WHERE id = :id LIMIT 1")
    ActivityLog getLogById(int id);

    // NEW: Update an existing log
    @Update
    void update(ActivityLog log);

    @Delete
    void delete(ActivityLog log);
}