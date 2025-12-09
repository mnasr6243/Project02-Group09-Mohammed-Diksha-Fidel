package com.example.project02_group09_mohammed_diksha_fidel.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Main Room database for ZenTrack.
// Now includes User, ActivityLog, and Challenge tables.
@Database(
        entities = {User.class, ActivityLog.class, Challenge.class, Participation.class},
        version = 6,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();
    public abstract ActivityLogDao activityLogDao();
    public abstract ChallengeDao challengeDao();
    public abstract ParticipationDao participationDao();

    private static volatile AppDatabase INSTANCE;

    private static final int NUMBER_OF_THREADS = 4;
    // Executor for background DB work
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    // Singleton getter
    public static AppDatabase get(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    "zentrack.db"
                            )
                            .allowMainThreadQueries()               // OK for this class project
                            .addCallback(preloadCallback)           // Seed initial data
                            .fallbackToDestructiveMigration()       // Reset DB on version change
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // Runs the first time the DB is created
    private static final Callback preloadCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                AppDatabase database = INSTANCE;

                // Seed Users
                UserDao userDao = database.userDao();
                userDao.insertUser(new User("testuser1", "testuser1", false)); // normal user
                userDao.insertUser(new User("admin2", "admin2", true));        // admin user

                // Seed one sample ActivityLog (for testuser1)
                ActivityLogDao logDao = database.activityLogDao();
                logDao.insert(new ActivityLog(
                        1,                        // userId (testuser1 will get ID 1 in this fresh DB)
                        "Steps",
                        5000f,
                        System.currentTimeMillis()
                ));

                // Seed sample Challenges
                ChallengeDao challengeDao = database.challengeDao();
                challengeDao.insert(new Challenge(
                        "7-Day Hydration",
                        "Drink 8 cups of water every day for a week.",
                        7
                ));
                challengeDao.insert(new Challenge(
                        "Daily Steps",
                        "Walk at least 5000 steps every day for 14 days.",
                        14
                ));
            });
        }
    };
}