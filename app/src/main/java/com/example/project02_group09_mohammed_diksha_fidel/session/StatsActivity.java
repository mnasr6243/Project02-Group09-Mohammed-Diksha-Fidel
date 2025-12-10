package com.example.project02_group09_mohammed_diksha_fidel.session;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project02_group09_mohammed_diksha_fidel.R;

// This Activity now serves only as a host for the StatsFragment.
public class StatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // This layout must contain an empty FrameLayout named 'fragment_container'.
        setContentView(R.layout.activity_stats);

        // Set up the back button (Up button)
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Weekly Statistics");
        }

        // Load the fragment dynamically
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new StatsFragment())
                    .commit();
        }

        // NOTE: The custom R.id.btnBackToMainStats button is now handled within the Fragment or removed entirely.
        // All data loading logic has been moved to StatsFragment.java.
    }

    // Still handles clicks on the Action Bar Up button
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}