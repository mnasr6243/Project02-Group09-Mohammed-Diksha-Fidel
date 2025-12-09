package com.example.project02_group09_mohammed_diksha_fidel;

import static org.junit.Assert.*;

import android.content.Context;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.example.project02_group09_mohammed_diksha_fidel.data.AppDatabase;
import com.example.project02_group09_mohammed_diksha_fidel.data.Challenge;
import com.example.project02_group09_mohammed_diksha_fidel.data.ChallengeDao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class ChallengeDaoTest {

    private AppDatabase db;
    private ChallengeDao challengeDao;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        // Use an in-memory database for testing
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class)
                .allowMainThreadQueries()
                .build();
        challengeDao = db.challengeDao();
    }

    @After
    public void closeDb() {
        db.close();
    }

    // Test: Insert a new challenge and verify it can be read back.
    @Test
    public void insertAndReadChallenge() {
        // Use the new constructor that matches your AppDatabase preload logic
        Challenge newChallenge = new Challenge("Test Run Challenge", "Test challenge description.", 0, 5);

        // Insert the challenge
        challengeDao.insertChallenge(newChallenge);

        // Read all challenges back
        List<Challenge> challenges = challengeDao.getAllChallenges();

        // Assertions
        assertFalse("Challenge list should not be empty", challenges.isEmpty());
        assertEquals("The challenge title should match", "Test Run Challenge", challenges.get(0).title);
    }
}