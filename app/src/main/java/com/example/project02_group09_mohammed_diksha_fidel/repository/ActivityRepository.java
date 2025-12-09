package com.example.project02_group09_mohammed_diksha_fidel.repository;
import android.app.Application;

// Imports pointing to the correct data package
import com.example.project02_group09_mohammed_diksha_fidel.data.ActivityLog;
import com.example.project02_group09_mohammed_diksha_fidel.data.ActivityLogDao;
import com.example.project02_group09_mohammed_diksha_fidel.data.AppDatabase;

import java.util.List;

public class ActivityRepository {
    // FIX: Uses private final for encapsulation
    private final ActivityLogDao activityLogDao;

    // Constructor: Sets up the connection
    public ActivityRepository(Application application) {
        // Calls the static 'get' method in AppDatabase
        AppDatabase db = AppDatabase.get(application);
        // Gets the DAO object from the database instance
        this.activityLogDao = db.activityLogDao();
    }

    // Insert Method: Write Operations (Used by AddLogActivity)
    public void insert(ActivityLog log) {
        // Runs the insert on the dedicated background thread executor
        AppDatabase.databaseWriteExecutor.execute(() -> {
            activityLogDao.insert(log);
        });
    }

    // Query Method: Read Operations (Used by DailyLogActivity)
    public List<ActivityLog> getDailyLogs(int userId, long start, long end) {
        return activityLogDao.getDailyLogsForUser(userId, start, end);
    }
}