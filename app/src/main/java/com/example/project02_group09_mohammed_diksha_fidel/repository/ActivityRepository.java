package com.example.project02_group09_mohammed_diksha_fidel.repository;

import android.app.Application;

import com.example.project02_group09_mohammed_diksha_fidel.data.ActivityLog;
import com.example.project02_group09_mohammed_diksha_fidel.data.ActivityLogDao;
import com.example.project02_group09_mohammed_diksha_fidel.data.AppDatabase;

import java.util.Calendar;
import java.util.List;

// This class manages data operations for ActivityLog entries, abstracting the database access.
public class ActivityRepository {
    private final ActivityLogDao activityLogDao;

    // Constructor: Initializes the database and the DAO for activity logs.
    public ActivityRepository(Application application) {
        AppDatabase db = AppDatabase.get(application);
        this.activityLogDao = db.activityLogDao();
    }

    // Inserts a new ActivityLog entry into the database.
    public void insert(ActivityLog log) {
        AppDatabase.databaseWriteExecutor.execute(() -> activityLogDao.insert(log));
    }

    // NEW: Fetch a single log by ID (for editing)
    public void getLogById(int id, OnLogLoadedListener listener) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            ActivityLog log = activityLogDao.getLogById(id);
            listener.onLogLoaded(log);
        });
    }

    // NEW: Update an existing log
    public void updateLog(ActivityLog log) {
        AppDatabase.databaseWriteExecutor.execute(() -> activityLogDao.update(log));
    }

    // Retrieves all logs for a specific user within the current day.
    public void getDailyLogsForUser(int userId, long date, OnLogsLoadedListener listener) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            long startOfDay = calculateStartOfDay(date);
            long endOfDay = calculateEndOfDay(date);
            List<ActivityLog> logs = activityLogDao.getLogsForDay(userId, startOfDay, endOfDay);
            listener.onLogsLoaded(logs);
        });
    }

    // Get the total sum of activity values for a given date range (For Stats Screen)
    public void getTotalValueForRange(int userId, String activityType, long startDate, long endDate, OnTotalValueLoadedListener listener) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            long start = calculateStartOfDay(startDate);
            long end = calculateEndOfDay(endDate);
            float totalValue = activityLogDao.getTotalValueForDateRange(userId, activityType, start, end);
            listener.onTotalValueLoaded(totalValue);
        });
    }

    private long calculateStartOfDay(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    private long calculateEndOfDay(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTimeInMillis();
    }

    // Interface to send database results back to the activity (For Daily Log)
    public interface OnLogsLoadedListener {
        void onLogsLoaded(List<ActivityLog> logs);
    }

    // Used to send a single log back (for editing)
    public interface OnLogLoadedListener {
        void onLogLoaded(ActivityLog log);
    }

    // NEW INTERFACE: Used to send the calculated total back to the StatsActivity
    public interface OnTotalValueLoadedListener {
        void onTotalValueLoaded(float total);
    }
}