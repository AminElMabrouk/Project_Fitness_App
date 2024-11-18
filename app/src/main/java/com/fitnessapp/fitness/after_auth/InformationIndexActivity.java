package com.fitnessapp.fitness.after_auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.fitnessapp.fitness.tabs.AllTabsActivity;
import com.fitnessapp.fitness.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class InformationIndexActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.after_auth_information_index);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        findViewById(R.id.calculateButton).setOnClickListener(view -> saveUserInfo());
    }

    private void saveUserInfo() {

        // Get user input from UI elements
        Spinner ageSpinner = findViewById(R.id.ageInput);
        int age = Integer.parseInt(ageSpinner.getSelectedItem().toString());
        int weight = Integer.parseInt(((EditText) findViewById(R.id.weightInput)).getText().toString());
        int height = Integer.parseInt(((EditText) findViewById(R.id.heightInput)).getText().toString());
        int targetWeight = Integer.parseInt(((EditText) findViewById(R.id.targetWeightInput)).getText().toString());

        // Prepare data to save
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("age", age);
        userInfo.put("weight", weight);
        userInfo.put("height", height);
        userInfo.put("targetWeight", targetWeight);
        userInfo.put("hasCompletedSetup", true);

        String userId = auth.getCurrentUser().getUid();
        db.collection("users").document(userId)
                .set(userInfo, SetOptions.merge())
                .addOnSuccessListener(aVoid -> goToHomePage())
                .addOnFailureListener(e -> Log.w("GettingInfo", "Error saving user info", e));
    }

    private void goToHomePage() {
        startActivity(new Intent(InformationIndexActivity.this, AllTabsActivity.class));
        finish();
    }
}
