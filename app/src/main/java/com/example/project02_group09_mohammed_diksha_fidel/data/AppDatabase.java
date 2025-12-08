package com.example.project02_group09_mohammed_diksha_fidel.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executors;

@Database(
        entities = {
                User.class,
                Challenge.class,
                Participation.class
        },
        version = 2,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();
    public abstract ChallengeDao challengeDao();
    public abstract ParticipationDao participationDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase get(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    "zentrack.db"
                            )
                            // For this small class project, let Room allow main-thread queries
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .addCallback(preloadCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback preloadCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Executors.newSingleThreadExecutor().execute(() -> {
                AppDatabase database = INSTANCE;
                UserDao dao = database.userDao();

                // Predefined users
                dao.insertUser(new User("testuser1", "testuser1", false));
                dao.insertUser(new User("admin2", "admin2", true));
            });
        }
    };
}

