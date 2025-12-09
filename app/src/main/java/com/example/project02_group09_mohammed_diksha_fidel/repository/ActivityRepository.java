package com.example.project02_group09_mohammed_diksha_fidel.repository;

import android.app.Application;

import com.example.project02_group09_mohammed_diksha_fidel.data.ActivityLog;
import com.example.project02_group09_mohammed_diksha_fidel.data.ActivityLogDao;
import com.example.project02_group09_mohammed_diksha_fidel.data.AppDatabase;

import java.util.Calendar; // REQUIRED IMPORT
import java.util.List;

public class ActivityRepository {
    private final ActivityLogDao activityLogDao;

    // INTERFACE: Used to send the query results back to the Activity (Must be public inside the class)
    public interface OnLogsLoadedListener {
        void onLogsLoaded(List<ActivityLog> logs);
    }

    public ActivityRepository(Application application) {
        AppDatabase db = AppDatabase.get(application);
        this.activityLogDao = db.activityLogDao();
    }

    public void insert(ActivityLog log) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            activityLogDao.insert(log);
        });
    }

    // ASYNCHRONOUS Read Operations (Used by DailyLogActivity)
    public void getDailyLogsForUser(int userId, long date, OnLogsLoadedListener listener) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            long startOfDay = calculateStartOfDay(date);
            long endOfDay = calculateEndOfDay(date);

            List<ActivityLog> logs = activityLogDao.getLogsForDay(userId, startOfDay, endOfDay);
            listener.onLogsLoaded(logs);
        });
    }

    // Helper Method 1: Calculates the start time of the current day (00:00:00)
    private long calculateStartOfDay(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    // Helper Method 2: Calculates the end time of the current day (23:59:59)
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