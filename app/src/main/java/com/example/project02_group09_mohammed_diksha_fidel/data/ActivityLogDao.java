package com.example.project02_group09_mohammed_diksha_fidel.data;

import androidx.lifecycle.LiveData;
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

    // 1. Get logs for the Daily Log screen (CHANGED to return LiveData)
    @Query("SELECT * FROM activitylog WHERE userId = :userId AND timestamp BETWEEN :startOfDay AND :endOfDay ORDER BY timestamp DESC")
    LiveData<List<ActivityLog>> getLogsForDay(int userId, long startOfDay, long endOfDay); // CRITICAL CHANGE: LiveData

    // 2. Get the total sum of values for the Stats screen
    @Query("SELECT SUM(value) FROM activitylog WHERE userId = :userId AND type = :activityType AND timestamp BETWEEN :startDate AND :endDate")
    float getTotalValueForDateRange(int userId, String activityType, long startDate, long endDate);

    @Delete
    void delete(ActivityLog log);

    @Query("DELETE FROM activitylog WHERE id = :logId")
    void deleteLogById(int logId);
}