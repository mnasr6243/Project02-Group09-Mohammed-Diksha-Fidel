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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.example.project02_group09_mohammed_diksha_fidel.data.ActivityLog;
import com.example.project02_group09_mohammed_diksha_fidel.data.ActivityLogDao;
import com.example.project02_group09_mohammed_diksha_fidel.data.AppDatabase;

@RunWith(AndroidJUnit4.class)
public class ActivityLogDaoTest {
    private ActivityLogDao activityLogDao;
    private AppDatabase db;

    private final long DAY1 = 1000;
    private final long DAY2 = 2000;

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

    // Database Test 1: Insert and Retrieve
    @Test
    public void insertAndRetrieveLog() {
        ActivityLog log = new ActivityLog(0, 1, "Steps", 5000, DAY1);
        activityLogDao.insert(log);

        // Correct assertion: Only 1 log inserted in this method.
        assertEquals(1, activityLogDao.getLogsForDay(1, 0, 3000).size());
        assertEquals(5000, activityLogDao.getLogsForDay(1, 0, 3000).get(0).getValue(), 0.01);
    }

    // Database Test 2 & 3: Delete and Aggregate Query
    @Test
    public void getTotalValueAndDeleteLog() {
        // Test data
        ActivityLog log1 = new ActivityLog(0, 1, "Steps", 1000, DAY1);
        ActivityLog log2 = new ActivityLog(0, 1, "Steps", 2000, DAY1);
        ActivityLog log3 = new ActivityLog(0, 2, "Steps", 500, DAY2);

        // ACTION: Insert logs and capture the generated ID for the one we intend to delete.
        long log1Id = activityLogDao.insert(log1);
        activityLogDao.insert(log2);
        activityLogDao.insert(log3);

        // 1. Initial Aggregate Test (Total is 3000)
        float totalBeforeDelete = activityLogDao.getTotalValueForDateRange(1, "Steps", 0, 3000);
        assertEquals(3000, totalBeforeDelete, 0.01);

        // 2. Delete test: We set the generated primary key (ID) back into the object before deleting.
        log1.setId((int) log1Id);
        activityLogDao.delete(log1);

        // 3. Final Aggregate Test (Should be 2000 after deleting log1)
        float totalAfterDelete = activityLogDao.getTotalValueForDateRange(1, "Steps", 0, 3000);
        assertEquals(2000, totalAfterDelete, 0.01);
    }
}