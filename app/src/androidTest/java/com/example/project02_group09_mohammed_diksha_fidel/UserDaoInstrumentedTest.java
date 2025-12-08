package com.example.project02_group09_mohammed_diksha_fidel;

import static org.junit.Assert.*;

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

@RunWith(AndroidJUnit4.class)
public class UserDaoInstrumentedTest {

    private AppDatabase db;
    private UserDao userDao;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class)
                .allowMainThreadQueries()   // OK in tests
                .build();
        userDao = db.userDao();
    }

    @After
    public void closeDb() {
        db.close();
    }

    @Test
    public void insertAndReadUserByUsername() {
        User user = new User("testuser_dao", "secret", false);
        userDao.insertUser(user);

        User loaded = userDao.getUserByUsername("testuser_dao");

        assertNotNull(loaded);
        assertEquals("testuser_dao", loaded.getUsername());
        assertEquals("secret", loaded.getPassword());
        assertFalse(loaded.isAdmin());
    }
}

