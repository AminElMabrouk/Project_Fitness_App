package com.fitnessapp.fitness.tabs.fragments.main_tabs.guide;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.fitnessapp.fitness.R;

public class ExerciseDetailActivity extends AppCompatActivity {
    public void goBack(View view) {
        finish(); // Termine cette activité et retourne à la précédente
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_detail);

        // Récupérer le nom de l'exercice
        String exerciseName = getIntent().getStringExtra("EXERCISE_NAME");

        // Afficher le nom de l'exercice
        TextView exerciseNameTextView = findViewById(R.id.exerciseName);
        exerciseNameTextView.setText(exerciseName);

        // Gérer le bouton retour
        findViewById(R.id.backButton).setOnClickListener(v -> finish());
    }
}
