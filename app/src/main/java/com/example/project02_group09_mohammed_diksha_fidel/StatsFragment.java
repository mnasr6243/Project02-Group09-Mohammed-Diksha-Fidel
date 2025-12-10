package com.example.project02_group09_mohammed_diksha_fidel.session;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.project02_group09_mohammed_diksha_fidel.R;
import com.example.project02_group09_mohammed_diksha_fidel.repository.ActivityRepository;

import java.util.Calendar;
import java.util.Locale;

public class StatsFragment extends Fragment {

    private ActivityRepository activityRepository;
    private SessionManager sessionManager;

    // UI variables for all stats
    private TextView textViewWeeklySteps;
    private TextView textViewWeeklyWater;
    private TextView textViewWeeklySleep;
    private TextView textViewWeeklyCaloriesBurnt;
    private TextView textViewWeeklyCaloriesIntake; // Added new variable

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stats, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Find the custom "Back to Main" button and set its click listener
        Button btnBackToMainStats = view.findViewById(R.id.btnBackToMainStats);
        btnBackToMainStats.setOnClickListener(v -> requireActivity().finish());

        // Initialize helper classes
        sessionManager = new SessionManager(requireContext());
        activityRepository = new ActivityRepository(requireActivity().getApplication());

        // Connects the UI elements for displaying the result
        textViewWeeklySteps = view.findViewById(R.id.textViewWeeklySteps);

        // Connect new TextViews (IDs must be added to fragment_stats.xml)
        textViewWeeklyWater = view.findViewById(R.id.textViewWeeklyWater);
        textViewWeeklySleep = view.findViewById(R.id.textViewWeeklySleep);
        textViewWeeklyCaloriesBurnt = view.findViewById(R.id.textViewWeeklyCaloriesBurnt);
        textViewWeeklyCaloriesIntake = view.findViewById(R.id.textViewWeeklyCaloriesIntake);

        // Initiates the data loading process
        loadWeeklyStats();
    }

    // Logic to fetch all totals for the last 7 days from the database.
    private void loadWeeklyStats() {
        int currentUserId = sessionManager.getUserId();

        // 1. Calculate the END DATE (Last millisecond of today)
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        long endDate = cal.getTimeInMillis();

        // 2. Calculate the START DATE (Midnight 7 days ago)
        cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.DAY_OF_YEAR, -6); // Go back 6 full days (for 7 days total)
        long startDate = cal.getTimeInMillis();

        // --- FETCH ALL WEEKLY STATS ---

        // 1. Steps (Fixing the previous issue)
        fetchAndDisplayTotal(currentUserId, "Steps", startDate, endDate, textViewWeeklySteps, "Weekly Steps: %.0f");

        // 2. Water Intake
        fetchAndDisplayTotal(currentUserId, "Water", startDate, endDate, textViewWeeklyWater, "Weekly Water: %.1f Liters");

        // 3. Sleep
        fetchAndDisplayTotal(currentUserId, "Sleep", startDate, endDate, textViewWeeklySleep, "Weekly Sleep: %.1f Hours");

        // 4. Calories Burnt
        fetchAndDisplayTotal(currentUserId, "Calories Burnt", startDate, endDate, textViewWeeklyCaloriesBurnt, "Weekly Calories Burnt: %.0f");

        // 5. Calories Intake
        fetchAndDisplayTotal(currentUserId, "Calories Intake", startDate, endDate, textViewWeeklyCaloriesIntake, "Weekly Calories Intake: %.0f");
    }

    /**
     * Helper method to fetch and display a single total to avoid code duplication.
     */
    private void fetchAndDisplayTotal(int userId, String activityType, long startDate, long endDate, TextView textView, String formatString) {
        activityRepository.getTotalValueForRange(
                userId,
                activityType,
                startDate,
                endDate,
                new ActivityRepository.OnTotalValueLoadedListener() {
                    @Override
                    public void onTotalValueLoaded(float total) {
                        requireActivity().runOnUiThread(() -> {
                            textView.setText(String.format(Locale.getDefault(), formatString, total));
                        });
                    }
                }
        );
    }
}