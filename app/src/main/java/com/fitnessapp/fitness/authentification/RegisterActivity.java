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

import com.fitnessapp.fitness.MainActivity;
import com.fitnessapp.fitness.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button register ;
    private static final String TAG = "RegisterActivity";
    private FirebaseAuth auth ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        register=findViewById(R.id.register);

        // Initialize Firebase
        try {
            FirebaseApp.initializeApp(this);
            auth = FirebaseAuth.getInstance();
        } catch (Exception e) {
            Log.e(TAG, "Error initializing Firebase: ", e);
            Toast.makeText(this, "Failed to initialize Firebase", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text_email=email.getText().toString();
                String text_password=password.getText().toString();

                if(TextUtils.isEmpty(text_email)||TextUtils.isEmpty(text_password)){
                    Toast.makeText(RegisterActivity.this,"Empty fieldss",Toast.LENGTH_SHORT).show();
                } else if (text_password.length()<6) {
                    Toast.makeText(RegisterActivity.this ,"Password is too short",Toast.LENGTH_SHORT).show();
                }else{
                    registerUser(text_email,text_password);
                }
            }
        });

    }

    private void registerUser(String email, String password) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "Register success", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    finish();
                }else {
                    Toast.makeText(RegisterActivity.this, "Registration Failed ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}