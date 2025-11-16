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
                                    "app.db")
                            .addCallback(preload)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final Callback preload = new Callback() {
        @Override public void onCreate(@NonNull SupportSQLiteDatabase db) {
            Executors.newSingleThreadExecutor().execute(() -> {
                UserDao dao = AppDatabase.INSTANCE.userDao();
                // Demo users
                dao.insertUser(new User("testuser1", "testuser1", false));
                dao.insertUser(new User("admin2", "admin2", true));
            });
        }
    };
}
