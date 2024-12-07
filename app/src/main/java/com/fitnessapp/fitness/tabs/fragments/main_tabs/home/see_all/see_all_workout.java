package com.fitnessapp.fitness.tabs.fragments.main_tabs.home.see_all;

import android.app.AlertDialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.fitnessapp.fitness.Classes.Workout;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.fitnessapp.fitness.R;

import java.util.ArrayList;
import java.util.List;

public class see_all_workout extends Fragment {

    private RecyclerView recyclerView;
    private WorkoutAdapter adapter;
    private List<Workout> workoutList = new ArrayList<>();
    private FirebaseAnalytics analytics;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_see_all_workout, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_workouts);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        analytics = FirebaseAnalytics.getInstance(requireContext());
        // In your see_all_workout Fragment
        FirebaseAnalytics analytics = FirebaseAnalytics.getInstance(requireContext());
        adapter = new WorkoutAdapter(workoutList, analytics);

        recyclerView.setAdapter(adapter);

        fetchWorkoutsFromFirebase();

        // Find the add button
        ImageView addWorkoutButton = view.findViewById(R.id.add_workout_button);

        // Set click listener to show a dialog
        addWorkoutButton.setOnClickListener(v -> showAddWorkoutDialog());
        //back button
        ImageView backButton = view.findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> {
            requireParentFragment().getChildFragmentManager().popBackStack();
        });

        return view;
    }

    private void showAddWorkoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Add Workout");

        // Inflate a custom dialog layout
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_workout, null);
        builder.setView(dialogView);

        EditText workoutNameInput = dialogView.findViewById(R.id.workout_name);
        EditText workoutDescriptionInput = dialogView.findViewById(R.id.workout_description);

        // Set up dialog buttons
        builder.setPositiveButton("Add", (dialog, which) -> {
            String workoutName = workoutNameInput.getText().toString().trim();
            String workoutDescription = workoutDescriptionInput.getText().toString().trim();

            if (!workoutName.isEmpty() && !workoutDescription.isEmpty()) {
                // Add workout to database or list
                addWorkoutToFirebase(workoutName, workoutDescription);
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        builder.show();
    }

    private void addWorkoutToFirebase(String workoutName, String workoutDescription) {
        Workout workout = new Workout(workoutName, workoutDescription);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("workouts").add(workout)
                .addOnSuccessListener(documentReference -> {
                    // Add the document ID to the workout object
                    workout.setDocumentId(documentReference.getId());
                    workoutList.add(workout);
                    adapter.notifyDataSetChanged();

                    // Log the event to Firebase Analytics

                    Bundle bundle = new Bundle();
                    bundle.putString("workout_name", workoutName);
                    analytics.logEvent("workout_added", bundle);

                    // Optional: Show a confirmation log
                    System.out.println(workoutName + " has been added.");
                })
                .addOnFailureListener(e -> {
                    // Error adding workout
                    e.printStackTrace();
                });
    }


    private void fetchWorkoutsFromFirebase() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("workouts").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot snapshots = task.getResult();
                        if (snapshots != null) {
                            workoutList.clear();
                            for (DocumentSnapshot document : snapshots) {
                                Workout workout = document.toObject(Workout.class);
                                workout.setDocumentId(document.getId()); // Set the document ID
                                workoutList.add(workout);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }
}
