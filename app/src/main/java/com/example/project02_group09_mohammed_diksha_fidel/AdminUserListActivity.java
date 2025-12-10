package com.example.project02_group09_mohammed_diksha_fidel;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project02_group09_mohammed_diksha_fidel.adapters.AdminUserAdapter;
import com.example.project02_group09_mohammed_diksha_fidel.repository.UserRepository;

// Admin-only screen: shows all users with a Delete button.
public class AdminUserListActivity extends AppCompatActivity {

    private UserRepository userRepository;
    private AdminUserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_users);

        userRepository = new UserRepository(getApplication());
        adapter = new AdminUserAdapter();

        RecyclerView rv = findViewById(R.id.recyclerAdminUsers);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        Button btnBack = findViewById(R.id.btnBackFromUsers);
        btnBack.setOnClickListener(v -> finish());

        adapter.setOnDeleteClickListener(user -> {
            userRepository.deleteUser(user, () -> runOnUiThread(() -> {
                Toast.makeText(this, "User deleted", Toast.LENGTH_SHORT).show();
                loadUsers();
            }));
        });

        loadUsers();
    }

    private void loadUsers() {
        userRepository.getAllUsers(users ->
                runOnUiThread(() -> adapter.setUsers(users))
        );
    }
}