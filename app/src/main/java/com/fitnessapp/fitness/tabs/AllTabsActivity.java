package com.fitnessapp.fitness.tabs;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.fitnessapp.fitness.R;
import com.fitnessapp.fitness.tabs.fragments.main_tabs.guide.ExerciseDetailActivity;
import com.fitnessapp.fitness.tabs.fragments.main_tabs.home.check_workout.check_workout;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class AllTabsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_tabs0);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager2 viewPager = findViewById(R.id.viewPager);

        // Set up the adapter
        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(adapter);

        // Attach TabLayout to ViewPager2
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Home");
                    break;
                case 1:
                    tab.setText("Guide");
                    break;
                case 2:
                    tab.setText("Stats");
                    break;
            }
        }).attach();
        // Profile picture click listener
        findViewById(R.id.avatarIcon).setOnClickListener(v -> {
            Intent intent = new Intent(AllTabsActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

    }

    public void openWorkout(View view) {
        navigateToWorkout();
    }


    // Handles clicks for the fragment's buttons/cards
    public void openChest(View view) {
        navigateToExercise("Chest");
    }

    public void openShoulders(View view) {
        navigateToExercise("Shoulders");
    }

    public void openBack(View view) {
        navigateToExercise("Back");
    }

    public void openArms(View view) {
        navigateToExercise("Arms");
    }

    public void openLegs(View view) {
        navigateToExercise("Legs");
    }

    private void navigateToExercise(String exerciseName) {
        Intent intent = new Intent(this, ExerciseDetailActivity.class);
        intent.putExtra("EXERCISE_NAME", exerciseName);
        startActivity(intent);
        Log.d("ExerciseGuide", "Navigating to " + exerciseName + " page");
    }

    private void navigateToWorkout() {
        Intent intent = new Intent(this, check_workout.class);

        startActivity(intent);

    }
}
