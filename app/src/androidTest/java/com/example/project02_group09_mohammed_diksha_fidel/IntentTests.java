package com.example.project02_group09_mohammed_diksha_fidel;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.intent.rule.IntentsRule;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.project02_group09_mohammed_diksha_fidel.data.AppDatabase;
import com.example.project02_group09_mohammed_diksha_fidel.data.User;
import com.example.project02_group09_mohammed_diksha_fidel.data.UserDao;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class IntentTests {

    // Rule to handle synchronous execution of architecture components (best practice)
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    // Rule to automatically initialize and release Intents
    @Rule
    public IntentsRule intentsRule = new IntentsRule();

    // CRITICAL FIX: Launch MainActivity, which contains the buttons (the entry screen)
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setup() {
        // PRELOAD A USER for the successful login test (done in background executor)
        UserDao userDao = AppDatabase.get(ApplicationProvider.getApplicationContext()).userDao();

        AppDatabase.databaseWriteExecutor.execute(() -> {
            // Ensure a known test user exists for the login test
            userDao.insertUser(new User("testuser", "pass", false));
        });
    }

    // Test 1: Verify MainActivity launches correctly
    @Test
    public void verifyMainActivityLaunches() {
        // Assert that the 'Login' button is visible, confirming the correct activity is launched
        onView(withText("Login")).check(matches(isDisplayed()));
    }

    // Test 2: Verify transition from MainActivity to CreateAccountActivity (1st required transition)
    @Test
    public void verifyCreateAccountTransition() {
        // 1. Find the Create Account button and perform a click
        onView(withId(R.id.btnCreateAccount)).perform(click());

        // ADDED for Video Demo: PAUSE FOR 1 SECOND to show the click and transition
        TestUtils.sleep(2000);

        // 2. Assert that the launched intent targets the CreateAccountActivity
        intended(hasComponent(CreateAccountActivity.class.getName()));
    }

    // Test 3: Verify transition from MainActivity to LoginActivity
    @Test
    public void verifyLoginTransition() {
        // 1. Find the Login button (R.id.btnLogin) and perform a click
        onView(withId(R.id.btnLogin)).perform(click());

        // ADDED for Video Demo: PAUSE FOR 1 SECOND to show the click and transition
        TestUtils.sleep(2000);

        // 2. Assert that the launched intent targets the LoginActivity
        intended(hasComponent(LoginActivity.class.getName()));
    }
}