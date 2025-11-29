package com.example.project02_group09_mohammed_diksha_fidel.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executors;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();

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
                            .allowMainThreadQueries()   // OK for small class project
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
            Executors.newSingleThreadExecutor().execute(() -> {

                // IMPORTANT: NOW safe — INSTANCE is fully built
                AppDatabase database = INSTANCE;
                UserDao dao = database.userDao();

                // Predefined users
                dao.insertUser(new User("testuser1", "testuser1", false));
                dao.insertUser(new User("admin2", "admin2", true));
            });
        }
    };
}