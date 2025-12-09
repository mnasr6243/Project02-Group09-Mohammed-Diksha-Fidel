package com.example.project02_group09_mohammed_diksha_fidel.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

@Database(entities = {User.class, ActivityLog.class}, version = 4, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();
    public abstract ActivityLogDao activityLogDao();

    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDatabase get(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    "zentrack.db"
                            )
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .addCallback(preloadCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final Callback preloadCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {

                AppDatabase database = INSTANCE;
                UserDao dao = database.userDao();
                ActivityLogDao logDao = database.activityLogDao();

                // Preload required users for assignment
                dao.insertUser(new User("testuser1", "testuser1", false));
                dao.insertUser(new User("admin2", "admin2", true));

                // Insert sample ActivityLog (constructor WITHOUT id)
                logDao.insert(new ActivityLog(
                        1,                       // userId
                        "Steps",                 // type
                        5000,                    // value
                        System.currentTimeMillis()
                ));
            });
        }
    };
}