package com.fitnessapp.fitness.tabs;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.fitnessapp.fitness.R;
import com.fitnessapp.fitness.authentification.LoginActivity;
import com.fitnessapp.fitness.authentification.StartActivity;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.allt_tabs_profile);

        Button logoutButton = findViewById(R.id.logoutButton);

        logoutButton.setOnClickListener(v -> {
            // Logout the user from Firebase
            FirebaseAuth.getInstance().signOut();

            // Redirect to login activity
            Intent intent = new Intent(ProfileActivity.this, StartActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish(); // End the current activity
        });
    }
}
