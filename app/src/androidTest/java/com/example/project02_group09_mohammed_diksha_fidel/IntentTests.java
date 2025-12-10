package com.example.project02_group09_mohammed_diksha_fidel;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsRule;

import com.example.project02_group09_mohammed_diksha_fidel.LandingPageActivity; // CORRECTED IMPORT: No .session
import com.example.project02_group09_mohammed_diksha_fidel.LoginActivity; // CORRECTED IMPORT: No .session

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;

@RunWith(AndroidJUnit4.class)
public class IntentTests {

    // Use this rule to automatically initialize and release Intents
    @Rule
    public IntentsRule intentsRule = new IntentsRule();

    // Rule to launch the starting activity for testing
    @Rule
    public ActivityScenarioRule<LoginActivity> activityRule = new ActivityScenarioRule<>(LoginActivity.class);

    // Test 1: Verify LoginActivity launches correctly (the initial entry point)
    @Test
    public void verifyLoginActivityLaunches() {
        // Since the activityRule launches LoginActivity, we simply ensure the scenario is running.
        ActivityScenario.launch(LoginActivity.class);
    }
}