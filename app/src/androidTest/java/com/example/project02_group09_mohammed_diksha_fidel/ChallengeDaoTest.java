package com.example.project02_group09_mohammed_diksha_fidel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

import java.io.IOException;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class ChallengeDaoTest {

    private AppDatabase db;
    private ChallengeDao challengeDao;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class)
                .allowMainThreadQueries()
                .build();
        challengeDao = db.challengeDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    // Test 1: Insert and fetch all challenges
    @Test
    public void insertAndGetAllChallenges() {
        Challenge c1 = new Challenge("Hydration", "Drink 8 cups of water", 7);
        Challenge c2 = new Challenge("Steps", "Walk 5000 steps daily", 14);

        challengeDao.insert(c1);
        challengeDao.insert(c2);

        List<Challenge> all = challengeDao.getAllChallenges();
        assertEquals(2, all.size());
        assertEquals("Steps", all.get(0).getTitle()); // because ordered DESC by id
    }

    // Test 2: Delete a challenge
    @Test
    public void deleteChallenge() {
        Challenge c1 = new Challenge("Sleep", "Sleep 8 hours", 7);
        long id = challengeDao.insert(c1);

        List<Challenge> before = challengeDao.getAllChallenges();
        assertEquals(1, before.size());

        // Need to set the generated ID back before delete
        c1.setChallengeId((int) id);
        challengeDao.delete(c1);

        List<Challenge> after = challengeDao.getAllChallenges();
        assertTrue(after.isEmpty());
    }
}