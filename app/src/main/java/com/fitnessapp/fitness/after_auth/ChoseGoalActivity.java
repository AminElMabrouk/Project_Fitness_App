package com.fitnessapp.fitness.after_auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.fitnessapp.fitness.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class ChoseGoalActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.after_auth_chose_goal);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Assume buttons for "bulk" and "cut"
        findViewById(R.id.bulk).setOnClickListener(view -> saveObjective("bulk"));
        findViewById(R.id.cut).setOnClickListener(view -> saveObjective("cut"));
    }

    private void saveObjective(String objective) {
        String userId = auth.getCurrentUser().getUid();
        Log Log = null;
        db.collection("users").document(userId)
                .update("objective", objective)
                .addOnSuccessListener(aVoid -> goToGettingInformationsIndexPage())
                .addOnFailureListener(e -> Log.w("ChooseObjective", "Error updating objective", e));
    }

    private void goToGettingInformationsIndexPage() {
        startActivity(new Intent(ChoseGoalActivity.this, InformationIndexActivity.class));
    }
}
