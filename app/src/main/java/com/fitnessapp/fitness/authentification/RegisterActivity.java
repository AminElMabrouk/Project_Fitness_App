package com.fitnessapp.fitness.authentification;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.fitnessapp.fitness.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private EditText name;
    private Button register;

    private FirebaseAuth auth;
    private FirebaseFirestore db;

    private static final String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Setup UI and Layout
        EdgeToEdge.enable(this);
        setContentView(R.layout.auth_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize UI Elements
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        name = findViewById(R.id.name);
        register = findViewById(R.id.register);

        // Initialize Firebase
        try {
            FirebaseApp.initializeApp(this);
            auth = FirebaseAuth.getInstance();
            db = FirebaseFirestore.getInstance();
        } catch (Exception e) {
            Log.e(TAG, "Error initializing Firebase: ", e);
            Toast.makeText(this, "Failed to initialize Firebase", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        // Register button click listener
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text_email = email.getText().toString();
                String text_password = password.getText().toString();
                String text_name = name.getText().toString();

                // Validate Inputs
                if (TextUtils.isEmpty(text_email) || TextUtils.isEmpty(text_password) || TextUtils.isEmpty(text_name)) {
                    Toast.makeText(RegisterActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                } else if (text_password.length() < 6) {
                    Toast.makeText(RegisterActivity.this, "Password is too short", Toast.LENGTH_SHORT).show();
                } else {
                    registerUser(text_email, text_password, text_name);
                }
            }
        });
    }

    /**
     * Registers the user with Firebase Authentication and adds user info to Firestore using the UID as the document ID.
     */
    private void registerUser(String email, String password, String name) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();

                            if (user != null) {
                                createUserDocumentInFirestore(user.getUid(), name, email);
                            } else {
                                Log.e(TAG, "FirebaseUser is null after registration.");
                            }
                        } else {
                            Log.e(TAG, "Registration failed: ", task.getException());
                            Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * Creates a Firestore document for the user, using their Firebase UID as the document ID.
     */
    private void createUserDocumentInFirestore(String uid, String name, String email) {
        // Data to store in Firestore
        Map<String, Object> userData = new HashMap<>();
        userData.put("username", name);              // Store the username as a field
        userData.put("email", email);                // Store user's email
        userData.put("uid", uid);                    // Firebase UID for reference
        userData.put("hasCompletedSetup", false);    // Default setup completion status

        db.collection("users").document(uid)  // Using UID as the document ID
                .set(userData)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "Register success", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        finish();
                    } else {
                        Log.e(TAG, "Failed to create user document in Firestore: ", task.getException());
                        Toast.makeText(RegisterActivity.this, "Failed to create user document", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
