package com.example.project02_group09_mohammed_diksha_fidel.repository;

import android.app.Application;
import androidx.lifecycle.LiveData; // NEW IMPORT: Required for LiveData return type

import com.example.project02_group09_mohammed_diksha_fidel.data.ActivityLog;
import com.example.project02_group09_mohammed_diksha_fidel.data.ActivityLogDao;
import com.example.project02_group09_mohammed_diksha_fidel.data.AppDatabase;

import java.util.Calendar; // Used for date calculations
import java.util.List;

// This class manages data operations for ActivityLog entries, abstracting the database access.
public class ActivityRepository {
    private final ActivityLogDao activityLogDao;

    // Interface removed, as LiveData handles results automatically.
    // public interface OnLogsLoadedListener { ... }

    // Existing interface for StatsActivity (still needed as Room aggregate queries don't return LiveData easily)
    public interface OnTotalValueLoadedListener {
        void onTotalValueLoaded(float total);
    }

    // Constructor: Initializes the database and the DAO for activity logs.
    public ActivityRepository(Application application) {
        AppDatabase db = AppDatabase.get(application);
        this.activityLogDao = db.activityLogDao();
    }

    // Inserts a new ActivityLog entry into the database.
    public void insert(ActivityLog log) {
        // Run the insert operation in the background using the database executor.
        AppDatabase.databaseWriteExecutor.execute(() -> {
            activityLogDao.insert(log);
        });
    }

    // This query is automatically run/updated by Room on a background thread.
    public LiveData<List<ActivityLog>> getDailyLogsForUser(int userId, long date) {
        // Calculate the start and end timestamps for the current day.
        long startOfDay = calculateStartOfDay(date);
        long endOfDay = calculateEndOfDay(date);

        // Fetch the LiveData from the DAO.
        return activityLogDao.getLogsForDay(userId, startOfDay, endOfDay);
    }

    // NEW METHOD: Get the total sum of activity values for a given date range (For Stats Screen)
    public void getTotalValueForRange(int userId, String activityType, long startDate, long endDate, OnTotalValueLoadedListener listener) {
        // Executes the aggregation query on a background thread.
        AppDatabase.databaseWriteExecutor.execute(() -> {
            long start = calculateStartOfDay(startDate);
            long end = calculateEndOfDay(endDate);

            float totalValue = activityLogDao.getTotalValueForDateRange(userId, activityType, start, end);
            listener.onTotalValueLoaded(totalValue);
        });
    }

    // Helper methods (calculateStartOfDay and calculateEndOfDay) remain unchanged.
    private long calculateStartOfDay(long time) {
        // ... (implementation remains the same)
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    private long calculateEndOfDay(long time) {
        // ... (implementation remains the same)
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTimeInMillis();
    }
}