package com.example.project02_group09_mohammed_diksha_fidel.repository;

import android.app.Application;

import com.example.project02_group09_mohammed_diksha_fidel.data.ActivityLog;
import com.example.project02_group09_mohammed_diksha_fidel.data.ActivityLogDao;
import com.example.project02_group09_mohammed_diksha_fidel.data.AppDatabase;

import java.util.Calendar; // Used for date calculations
import java.util.List;

public class ActivityRepository {
    private final ActivityLogDao activityLogDao;

    // Interface to send database results back to the activity
    public interface OnLogsLoadedListener {
        void onLogsLoaded(List<ActivityLog> logs);
    }

    public ActivityRepository(Application application) {
        AppDatabase db = AppDatabase.get(application);
        this.activityLogDao = db.activityLogDao();
    }

    public void insert(ActivityLog log) {
        // Run the insert operation in the background
        AppDatabase.databaseWriteExecutor.execute(() -> {
            activityLogDao.insert(log);
        });
    }

    // Get all logs for a specific user and day in the background
    public void getDailyLogsForUser(int userId, long date, OnLogsLoadedListener listener) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            long startOfDay = calculateStartOfDay(date);
            long endOfDay = calculateEndOfDay(date);

            List<ActivityLog> logs = activityLogDao.getLogsForDay(userId, startOfDay, endOfDay);
            listener.onLogsLoaded(logs);
        });
    }

    // Helper: Calculate the start time of the day (00:00:00)
    private long calculateStartOfDay(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    // Helper: Calculate the end time of the day (23:59:59)
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