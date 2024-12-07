package com.fitnessapp.fitness.after_auth;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.fitnessapp.fitness.tabs.AllTabsActivity;
import com.fitnessapp.fitness.R;
import com.fitnessapp.fitness.authentification.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ChooseGoal_HomePage extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.after_auth_choose_goal_or_homepage); // Update with the correct layout resource

        // Initialize Firebase Authentication and Firestore
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Check if user is logged in
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            checkUserSetup(currentUser.getUid());
        } else {
            // Redirect to LoginActivity if the user is not logged in
            startActivity(new Intent(ChooseGoal_HomePage.this, LoginActivity.class));
            finish();
        }
    }

    // Method to check if the user has completed the setup process
    private void checkUserSetup(String userId) {
        DocumentReference userRef = db.collection("users").document(userId);
        userRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists() && Boolean.TRUE.equals(document.getBoolean("hasCompletedSetup"))) {
                    goToHomePage();
                } else {
                    goToChooseObjectivePage();
                }
            } else {
                goToChooseObjectivePage(); // Redirect to the objective selection if document retrieval fails
            }
        });
    }

    // Redirect to the ChooseObjectiveActivity
    private void goToChooseObjectivePage() {
        startActivity(new Intent(ChooseGoal_HomePage.this, ChoseGoalActivity.class));
        finish();
    }

    // Redirect to the HomePageActivity
    private void goToHomePage() {
        startActivity(new Intent(ChooseGoal_HomePage.this, AllTabsActivity.class));
        finish();
    }

}
