package com.example.project02_group09_mohammed_diksha_fidel.repository;

import android.app.Application;

import com.example.project02_group09_mohammed_diksha_fidel.data.ActivityLog;
import com.example.project02_group09_mohammed_diksha_fidel.data.ActivityLogDao;
import com.example.project02_group09_mohammed_diksha_fidel.data.AppDatabase;

import java.util.Calendar; // Used for date calculations
import java.util.List;

// This class manages data operations for ActivityLog entries, abstracting the database access.
public class ActivityRepository {
    private final ActivityLogDao activityLogDao;

    // Interface to send database results back to the activity (For Daily Log)
    public interface OnLogsLoadedListener {
        void onLogsLoaded(List<ActivityLog> logs);
    }

    // NEW INTERFACE: Used to send the calculated total back to the StatsActivity
    public interface OnTotalValueLoadedListener {
        void onTotalValueLoaded(float total);
    }

    // Constructor: Initializes the database and the DAO for activity logs.
    public ActivityRepository(Application application) {
        // Gets the singleton instance of the AppDatabase.
        AppDatabase db = AppDatabase.get(application);
        // Retrieves the Data Access Object (DAO) for ActivityLog operations.
        this.activityLogDao = db.activityLogDao();
    }

    // Inserts a new ActivityLog entry into the database.
    public void insert(ActivityLog log) {
        // Run the insert operation in the background using the database executor.
        AppDatabase.databaseWriteExecutor.execute(() -> {
            activityLogDao.insert(log);
        });
    }

    // Retrieves all logs for a specific user within the current day.
    public void getDailyLogsForUser(int userId, long date, OnLogsLoadedListener listener) {
        // Executes the database query on a background thread.
        AppDatabase.databaseWriteExecutor.execute(() -> {
            // Calculate the start and end timestamps for the current day.
            long startOfDay = calculateStartOfDay(date);
            long endOfDay = calculateEndOfDay(date);

            // Fetch the logs from the DAO.
            List<ActivityLog> logs = activityLogDao.getLogsForDay(userId, startOfDay, endOfDay);
            // Calls the listener method to return the results to the calling activity.
            listener.onLogsLoaded(logs);
        });
    }

    // NEW METHOD: Get the total sum of activity values for a given date range (For Stats Screen)
    public void getTotalValueForRange(int userId, String activityType, long startDate, long endDate, OnTotalValueLoadedListener listener) {
        // Executes the aggregation query on a background thread.
        AppDatabase.databaseWriteExecutor.execute(() -> {
            // Ensure we use the full range of the start and end days for accurate counting.
            long start = calculateStartOfDay(startDate);
            long end = calculateEndOfDay(endDate);

            // Fetch the summed value from the DAO.
            float totalValue = activityLogDao.getTotalValueForDateRange(userId, activityType, start, end);
            // Returns the total value via the listener interface.
            listener.onTotalValueLoaded(totalValue);
        });
    }

    // Helper: Calculates the start time of the day (00:00:00) based on a given timestamp.
    private long calculateStartOfDay(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    // Helper: Calculates the end time of the day (23:59:59) based on a given timestamp.
    private long calculateEndOfDay(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTimeInMillis();
    }
}