package com.example.project02_group09_mohammed_diksha_fidel;

import android.content.Context;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import com.example.project02_group09_mohammed_diksha_fidel.data.ActivityLog;
import com.example.project02_group09_mohammed_diksha_fidel.data.ActivityLogDao;
import com.example.project02_group09_mohammed_diksha_fidel.data.AppDatabase;

@RunWith(AndroidJUnit4.class)
public class ActivityLogDaoTest {
    private ActivityLogDao activityLogDao;
    private AppDatabase db;

    private final long DAY1 = 10000000;
    private static final int USER_A = 10;
    private static final int USER_B = 20;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class)
                .allowMainThreadQueries()
                .build();
        activityLogDao = db.activityLogDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    // Test 1: Insert and Retrieve Logs for a specific day.
    @Test
    public void insertAndRetrieveLog() {
        ActivityLog log = new ActivityLog(0, USER_A, "Steps", 5000, DAY1);
        activityLogDao.insert(log);

        // Assuming date range from 0 to DAY1 + 1 (using a large number for the end time)
        List<ActivityLog> logs = activityLogDao.getLogsForDay(USER_A, 0, DAY1 + 1000);
        assertEquals(1, logs.size());
        assertEquals(5000, logs.get(0).getValue(), 0.01);
    }

    // Test 2: Aggregate (SUM) activity values over a time range. (For StatsActivity logic)
    @Test
    public void aggregateTotalValueForRange() {
        long endDate = System.currentTimeMillis();
        long startDate = endDate - (7 * 24 * 60 * 60 * 1000L); // 7 days earlier

        // Log 1 (in range): Steps 3000
        activityLogDao.insert(new ActivityLog(0, USER_A, "Steps", 3000, endDate));
        // Log 2 (in range): Steps 500
        activityLogDao.insert(new ActivityLog(0, USER_A, "Steps", 500, endDate - 86400000));
        // Log 3 (outside range): Steps 100
        activityLogDao.insert(new ActivityLog(0, USER_A, "Steps", 100, startDate - 86400000));

        // Get total steps for the date range (sum should be 3000 + 500 = 3500)
        float totalSteps = activityLogDao.getTotalValueForDateRange(USER_A, "Steps", startDate, endDate);

        assertEquals("Total sum should be 3500", 3500, totalSteps, 0.1);
    }

    // Test 3: Verify logs are filtered correctly by user ID.
    @Test
    public void filterLogsByUserId() {
        // Log for User A
        activityLogDao.insert(new ActivityLog(0, USER_A, "Steps", 100, DAY1));
        // Log for User B (should be ignored when querying for USER_A)
        activityLogDao.insert(new ActivityLog(0, USER_B, "Steps", 500, DAY1));

        // Retrieve logs for User A only (should be 1 log)
        List<ActivityLog> logsA = activityLogDao.getLogsForDay(USER_A, 0, DAY1 + 1000);
        assertEquals(1, logsA.size());
        assertEquals(100, logsA.get(0).getValue(), 0.01);
    }

    // Test 4: Delete a specific log entry by ID.
    @Test
    public void deleteLogEntryById() {
        long timestamp = System.currentTimeMillis();
        ActivityLog log = new ActivityLog(0, USER_A, "Running", 60, timestamp);

        // 1. Insert the log and get the auto-generated ID
        long logId = activityLogDao.insert(log);

        // 2. Delete the log using its ID
        activityLogDao.deleteLogById((int) logId);

        // 3. Verify log was deleted (list should be empty)
        List<ActivityLog> finalLogs = activityLogDao.getLogsForDay(USER_A, timestamp - 1000, timestamp + 1000);
        assertTrue("Log list should be empty after deletion", finalLogs.isEmpty());
    }
}