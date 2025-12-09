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

import static org.junit.Assert.*;

import com.example.project02_group09_mohammed_diksha_fidel.data.AppDatabase;
import com.example.project02_group09_mohammed_diksha_fidel.data.User;
import com.example.project02_group09_mohammed_diksha_fidel.data.UserDao;

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

    // Test 2: Check if a username exists and retrieve admin status.
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

    // Test 3: Verify user retrieval using both username and password (for Login validation).
    @Test
    public void verifyLoginCredentials() {
        User loginUser = new User("login_test", "correct_pass", false);
        userDao.insertUser(loginUser);

        // Scenario 1: Correct credentials should return the user
        User foundUser = userDao.getUserByUsernameAndPassword("login_test", "correct_pass");
        assertNotNull("User should be found with correct credentials", foundUser);
        assertEquals("Username must match the inserted user", "login_test", foundUser.getUsername());

        // Scenario 2: Incorrect password should return null
        User failedUser = userDao.getUserByUsernameAndPassword("login_test", "wrong_pass");
        assertNull("User should NOT be found with incorrect password", failedUser);
    }

    // Test 4: Verify the isAdmin status is retrieved correctly for both Admin and Regular users.
    @Test
    public void verifyAdminStatusRetrieval() {
        // Insert a user marked as Admin
        User adminUser = new User("admin_check_1", "securepass", true);
        userDao.insertUser(adminUser);

        // Retrieve the user by username and assert isAdmin is true
        User foundAdmin = userDao.getUserByUsername("admin_check_1");
        assertTrue("User should be an administrator", foundAdmin.isAdmin());

        // Insert a non-admin user
        User regularUser = new User("user_check_1", "pass", false);
        userDao.insertUser(regularUser);

        // Retrieve the user and assert isAdmin is false
        User foundRegular = userDao.getUserByUsername("user_check_1");
        assertFalse("User should NOT be an administrator", foundRegular.isAdmin());
    }
}