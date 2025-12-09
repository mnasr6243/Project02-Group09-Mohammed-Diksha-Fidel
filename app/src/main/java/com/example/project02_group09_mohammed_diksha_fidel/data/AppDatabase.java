package com.example.project02_group09_mohammed_diksha_fidel.data;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

// This is the main database class for the whole app.
// It lists all the tables (entities) and the current version.
@Database(entities = {User.class, ActivityLog.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    // This is how we get the User table functions.
    public abstract UserDao userDao();

    // Abstract method to get the new ActivityLog table functions.
    public abstract ActivityLogDao activityLogDao();

    // This variable holds the single instance of the database (Singleton pattern).
    private static volatile AppDatabase INSTANCE;

    // Executor for database operations (e.g., inserts) on a background thread.
    private static final int NUMBER_OF_THREADS = 4;
    // This runs database tasks so the app doesn't freeze.
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    // This method gets the database instance, creating it if it doesn't exist.
    public static AppDatabase get(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    // This creates the database file named "zentrack.db".
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    "zentrack.db"
                            )
                            // Note: .allowMainThreadQueries() is often discouraged,
                            // but retained for simplicity as per your original code.
                            .allowMainThreadQueries()
                            .addCallback(preloadCallback)

                            // 4. IMPORTANT: Required when upgrading the database version (from 1 to 2)
                            // If the version changes, this wipes the old data and recreates the tables.
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // This is a special function that runs when the database is first created.
    private static final Callback preloadCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            // Use the dedicated executor for initial data insertion
            databaseWriteExecutor.execute(() -> {

                // IMPORTANT: NOW safe — INSTANCE is fully built
                AppDatabase database = INSTANCE;
                UserDao dao = database.userDao();

                // Predefined users are added here.
                dao.insertUser(new User("testuser1", "testuser1", false));
                dao.insertUser(new User("admin2", "admin2", true));

                // Add an initial activity log example for testing the new table
                // This line adds a sample activity log for user ID 1.
                ActivityLogDao logDao = database.activityLogDao();
                logDao.insert(new ActivityLog(0,1,"Steps",5000,System.currentTimeMillis()
                ));

            });
        }
    };
}