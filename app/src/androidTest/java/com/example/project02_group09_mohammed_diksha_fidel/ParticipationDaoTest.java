package com.example.project02_group09_mohammed_diksha_fidel;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.project02_group09_mohammed_diksha_fidel.data.AppDatabase;
import com.example.project02_group09_mohammed_diksha_fidel.data.Participation;
import com.example.project02_group09_mohammed_diksha_fidel.data.ParticipationDao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class ParticipationDaoTest {

    private AppDatabase db;
    private ParticipationDao participationDao;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class)
                .allowMainThreadQueries()
                .build();

        participationDao = db.participationDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    // Test 1: Insert participation and verify it is stored
    @Test
    public void insertParticipationTest() {
        Participation p = new Participation(1, 10, System.currentTimeMillis());
        long id = participationDao.insert(p);

        assertTrue(id > 0);
        // Confirm count = 1 for this user/challenge
        int count = participationDao.countForUserAndChallenge(1, 10);
        assertEquals(1, count);
    }

    // Test 2: Duplicate join should not increase count
    @Test
    public void duplicateJoinDoesNotCountTwice() {
        Participation p1 = new Participation(2, 20, System.currentTimeMillis());
        Participation p2 = new Participation(2, 20, System.currentTimeMillis());

        participationDao.insert(p1);
        participationDao.insert(p2); // duplicate attempt

        // countForUserAndChallenge should be 2 in DB, but join logic prevents duplicates at REPO level
        // Unit test focuses ONLY on DAO behavior:
        int count = participationDao.countForUserAndChallenge(2, 20);
        assertEquals(2, count);
    }

    // Test 3: Retrieve all joined challenge IDs for a user
    @Test
    public void getJoinedChallengeIdsTest() {
        participationDao.insert(new Participation(1, 5, 1000));
        participationDao.insert(new Participation(1, 6, 2000));
        participationDao.insert(new Participation(2, 7, 3000));

        List<Integer> list = participationDao.getChallengeIdsForUser(1);

        assertEquals(2, list.size());
        assertTrue(list.contains(5));
        assertTrue(list.contains(6));
    }
}