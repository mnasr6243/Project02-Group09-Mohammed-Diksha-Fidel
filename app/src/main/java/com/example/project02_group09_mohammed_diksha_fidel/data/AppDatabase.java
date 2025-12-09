package com.example.project02_group09_mohammed_diksha_fidel.data;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

// CRITICAL FIX: Combines ALL entities (User, ActivityLog, Challenge, Participation)
@Database(
        entities = {
                User.class,
                ActivityLog.class,
                Challenge.class,
                Participation.class
        },
        version = 4, // Higher version to ensure no migration issues with combined tables
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    // CRITICAL FIX: Includes all abstract DAO methods
    public abstract UserDao userDao();
    public abstract ActivityLogDao activityLogDao();
    public abstract ChallengeDao challengeDao();
    public abstract ParticipationDao participationDao();

    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    // Runs database tasks in the background
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    // Gets the database instance
    public static AppDatabase get(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    // Builds the database file
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    "zentrack.db"
                            )
                            // Keeps the most permissive settings for testing/development
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .addCallback(preloadCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // Runs when the database is first created
    private static final Callback preloadCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                AppDatabase database = INSTANCE;
                UserDao dao = database.userDao();

                // Predefined users added here for testing
                dao.insertUser(new User("testuser1", "testuser1", false)); // Non-admin user
                dao.insertUser(new User("admin2", "admin2", true));       // Admin user

                // Add sample log for testing the new table
                ActivityLogDao logDao = database.activityLogDao();
                logDao.insert(new ActivityLog(0,1,"Steps",5000,System.currentTimeMillis()
                ));
            });
        }
    };
}