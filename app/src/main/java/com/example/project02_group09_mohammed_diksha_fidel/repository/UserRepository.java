package com.example.project02_group09_mohammed_diksha_fidel.repository;

import android.app.Application;

import com.example.project02_group09_mohammed_diksha_fidel.data.AppDatabase;
import com.example.project02_group09_mohammed_diksha_fidel.data.User;
import com.example.project02_group09_mohammed_diksha_fidel.data.UserDao;

import java.util.List;

// Repository for admin user management (list + delete).
public class UserRepository {

    public interface OnUsersLoadedListener {
        void onUsersLoaded(List<User> users);
    }

    public interface OnUserDeletedListener {
        void onUserDeleted();
    }

    private final UserDao userDao;

    public UserRepository(Application application) {
        AppDatabase db = AppDatabase.get(application);
        userDao = db.userDao();
    }

    // Load all users (for admin list)
    public void getAllUsers(OnUsersLoadedListener listener) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            List<User> users = userDao.getAllUsers();
            listener.onUsersLoaded(users);
        });
    }

    // Delete a user
    public void deleteUser(User user, OnUserDeletedListener listener) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            userDao.deleteUser(user);
            listener.onUserDeleted();
        });
    }
}