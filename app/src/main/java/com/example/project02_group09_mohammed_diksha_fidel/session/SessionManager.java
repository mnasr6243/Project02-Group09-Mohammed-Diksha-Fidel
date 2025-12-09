package com.example.project02_group09_mohammed_diksha_fidel.session;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String PREF_NAME = "user_session";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_IS_ADMIN = "is_admin";
    private static final String KEY_USER_ID = "userId";

    private final SharedPreferences prefs;
    private final SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    // Added user ID to parameters
    public void saveSession(int userId, String username, boolean isAdmin) {
        editor.putInt(KEY_USER_ID, userId);
        editor.putString(KEY_USERNAME, username);
        editor.putBoolean(KEY_IS_ADMIN, isAdmin);
        editor.apply();
    }

    public String getUsername() {
        return prefs.getString(KEY_USERNAME, null);
    }

    public boolean isAdmin() {
        return prefs.getBoolean(KEY_IS_ADMIN, false);
    }

    public boolean isLoggedIn() {
        return prefs.contains(KEY_USERNAME);
    }

    // Get the ID of the logged-in user
    public int getCurrentUserId() {
        // Default to 1
        return prefs.getInt(KEY_USER_ID, 1);
    }

    public void clear() {
        editor.clear();
        editor.apply();
    }

    public void logout() {
        editor.clear();
        editor.apply();
    }
}