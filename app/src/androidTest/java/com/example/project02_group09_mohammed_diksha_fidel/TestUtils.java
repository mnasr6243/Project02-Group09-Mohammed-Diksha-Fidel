package com.example.project02_group09_mohammed_diksha_fidel;

// Utility for introducing delays in tests
public class TestUtils {

    // Pauses the test execution for the given duration in milliseconds
    public static void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}