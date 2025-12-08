package com.example.project02_group09_mohammed_diksha_fidel;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project02_group09_mohammed_diksha_fidel.data.AppDatabase;
import com.example.project02_group09_mohammed_diksha_fidel.data.User;
import com.example.project02_group09_mohammed_diksha_fidel.data.UserDao;
import com.example.project02_group09_mohammed_diksha_fidel.session.SessionManager;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private UserDao userDao;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userDao = AppDatabase.get(this).userDao();
        session = new SessionManager(this);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        Button btnLoginSubmit = findViewById(R.id.btnLoginSubmit);
        Button btnBack = findViewById(R.id.btnBack);

        btnLoginSubmit.setOnClickListener(v -> handleLogin());
        btnBack.setOnClickListener(v -> finish());
    }

    private void handleLogin() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = userDao.getUserByUsername(username);

        if (user == null || !user.getPassword().equals(password)) {
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
            return;
        }

        session.login(user.getUserId(), user.getUsername(), user.isAdmin());

        Intent intent;
        if (user.isAdmin()) {
            intent = new Intent(LoginActivity.this, AdminManagerActivity.class);
        } else {
            intent = new Intent(LoginActivity.this, LandingPageActivity.class);
        }
        startActivity(intent);
        finish();
    }
}
