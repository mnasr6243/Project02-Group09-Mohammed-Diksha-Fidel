package com.example.project02_group09_mohammed_diksha_fidel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.project02_group09_mohammed_diksha_fidel.data.AppDatabase;
import com.example.project02_group09_mohammed_diksha_fidel.data.User;
import com.example.project02_group09_mohammed_diksha_fidel.data.UserDao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

@RunWith(AndroidJUnit4.class)
public class UserDaoTest {
    private UserDao userDao;
    private AppDatabase db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        // Uses an in-memory database for testing
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class)
                .allowMainThreadQueries()
                .build();
        userDao = db.userDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    // Test 1: Insert and retrieve user by ID.
    @Test
    public void insertAndGetUserById() {
        User user = new User("testuser", "password123", false);
        userDao.insertUser(user);

        // Assumes the first inserted user gets auto-generated ID 1.
        User found = userDao.getUserById(1);
        assertNotNull(found);
        assertEquals("testuser", found.getUsername());
    }

    // Test 2: Check if a username exists (for login/create account).
    @Test
    public void getUserByUsernameTest() {
        User user = new User("admin_test", "secure", true);
        userDao.insertUser(user);

        // Verify user is found
        User found = userDao.getUserByUsername("admin_test");
        assertNotNull(found);
        assertTrue(found.isAdmin());

        // Verify nonexistent user returns null
        User notFound = userDao.getUserByUsername("nonexistent");
        assertNull(notFound);
    }
}