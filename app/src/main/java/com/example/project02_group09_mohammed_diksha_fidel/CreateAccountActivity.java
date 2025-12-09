// Complete CreateAccountActivity.java code
package com.example.project02_group09_mohammed_diksha_fidel;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project02_group09_mohammed_diksha_fidel.data.AppDatabase;
import com.example.project02_group09_mohammed_diksha_fidel.data.User;
import com.example.project02_group09_mohammed_diksha_fidel.data.UserDao;

// This activity manages the user interface and logic for registering a new user account.
public class CreateAccountActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private CheckBox cbIsAdmin;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        // Initializes the DAO to interact with the User table in the database
        userDao = AppDatabase.get(this).userDao();

        // Connects all UI input elements to Java variables
        etUsername = findViewById(R.id.etNewUsername);
        etPassword = findViewById(R.id.etNewPassword);
        cbIsAdmin = findViewById(R.id.cbIsAdmin);
        Button btnSubmit = findViewById(R.id.btnSubmitCreate);
        Button btnBack = findViewById(R.id.btnBackFromCreate);

        // Sets click listeners for the form buttons
        btnSubmit.setOnClickListener(v -> createAccount());
        btnBack.setOnClickListener(v -> finish()); // Closes the screen
    }

    // Handles the validation and execution of the account creation process.
    private void createAccount() {
        // Retrieves and cleans input from text fields
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        boolean isAdmin = cbIsAdmin.isChecked();

        // Validation: Check if input fields are empty
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Username and password cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        // Executes database operation (checking existence and insertion) on a background thread
        AppDatabase.databaseWriteExecutor.execute(() -> {
            // Checks if a user with the provided username already exists
            User existingUser = userDao.getUserByUsername(username);

            // Switches back to the main thread to update the UI (display Toast message)
            runOnUiThread(() -> {
                if (existingUser != null) {
                    // Username already in use
                    Toast.makeText(this, "Username already exists!", Toast.LENGTH_SHORT).show();
                } else {
                    // Creates and inserts the new User object into the database
                    User newUser = new User(username, password, isAdmin);
                    userDao.insertUser(newUser);

                    Toast.makeText(this, "Account created successfully!", Toast.LENGTH_SHORT).show();
                    finish(); // Closes the screen to return to the previous activity (MainActivity)
                }
            });
        });
    }
}