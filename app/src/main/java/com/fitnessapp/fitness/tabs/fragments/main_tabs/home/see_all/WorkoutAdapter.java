package com.fitnessapp.fitness.tabs.fragments.main_tabs.home.see_all;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fitnessapp.fitness.Classes.Workout;
import com.fitnessapp.fitness.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.List;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder> {

    private List<Workout> workoutList;
    private FirebaseAnalytics analytics;

    // Constructor
    public WorkoutAdapter(List<Workout> workoutList, FirebaseAnalytics analytics) {
        this.workoutList = workoutList;
        this.analytics = analytics;
    }

    @NonNull
    @Override
    public WorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate your layout for each item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.see_all_workout_item_workout, parent, false);
        return new WorkoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutViewHolder holder, int position) {
        Workout workout = workoutList.get(position);
        holder.nameTextView.setText(workout.getName());
        holder.descriptionTextView.setText(workout.getDescription());

        // Handle Edit Button click
        holder.editButton.setOnClickListener(v -> {
            // Open the edit dialog for the selected workout
            showEditWorkoutDialog(v.getContext(), workout);
        });

        // Handle Delete Button click
        holder.deleteButton.setOnClickListener(v -> {
            // Delete the selected workout from Firebase
            deleteWorkout(workout, position);
        });
    }

    @Override
    public int getItemCount() {
        return workoutList.size();
    }

    public static class WorkoutViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, descriptionTextView;
        ImageView editButton, deleteButton;

        public WorkoutViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.workout_name);
            descriptionTextView = itemView.findViewById(R.id.workout_description);
            editButton = itemView.findViewById(R.id.edit_button);  // Find Edit Button
            deleteButton = itemView.findViewById(R.id.delete_button);  // Find Delete Button
        }
    }

    private void showEditWorkoutDialog(Context context, Workout workout) {
        // Create an AlertDialog.Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit Workout");

        // Inflate the custom layout for the dialog
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_edit_workout, null);
        builder.setView(dialogView);

        // Get references to the EditText fields in the dialog
        EditText workoutNameInput = dialogView.findViewById(R.id.workout_name);
        EditText workoutDescriptionInput = dialogView.findViewById(R.id.workout_description);

        // Set current values in the EditText fields
        workoutNameInput.setText(workout.getName());
        workoutDescriptionInput.setText(workout.getDescription());

        // Set the Save button to update the workout
        builder.setPositiveButton("Save", (dialog, which) -> {
            String workoutName = workoutNameInput.getText().toString().trim();
            String workoutDescription = workoutDescriptionInput.getText().toString().trim();

            // Update the workout only if the fields are not empty
            if (!workoutName.isEmpty() && !workoutDescription.isEmpty()) {
                workout.setName(workoutName);
                workout.setDescription(workoutDescription);
                updateWorkoutInFirebase(workout);
            }
        });

        // Set the Cancel button to dismiss the dialog
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        // Show the dialog
        builder.create().show();
    }

    private void updateWorkoutInFirebase(Workout workout) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String documentId = workout.getDocumentId();  // Get the document ID from the workout

        if (documentId == null) {
            // Log error or show message to user
            return;
        }

        db.collection("workouts").document(documentId).set(workout)
                .addOnSuccessListener(aVoid -> {
                    // Log event for workout update
                    Bundle bundle = new Bundle();
                    bundle.putString("workout_name", workout.getName());
                    analytics.logEvent("workout_updated", bundle);

                    // Workout updated successfully
                    notifyDataSetChanged();  // Refresh the RecyclerView
                })
                .addOnFailureListener(e -> {
                    // Handle failure (e.g., show a message to the user)
                    e.printStackTrace();
                });
    }

    private void deleteWorkout(Workout workout, int position) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String documentId = workout.getDocumentId();  // Get the document ID from the workout

        if (documentId == null) {
            // Log error or show message to user
            return;
        }

        db.collection("workouts").document(documentId).delete()
                .addOnSuccessListener(aVoid -> {
                    // Log event for workout deletion
                    Bundle bundle = new Bundle();
                    bundle.putString("workout_name", workout.getName());
                    analytics.logEvent("workout_deleted", bundle);

                    // Remove the workout from the list and notify the adapter
                    workoutList.remove(position);
                    notifyItemRemoved(position);
                })
                .addOnFailureListener(e -> {
                    // Handle failure (e.g., show a message to the user)
                    e.printStackTrace();
                });
    }

    public void updateWorkoutList(List<Workout> workouts) {
        // Update the list and notify adapter
        this.workoutList = workouts;
        notifyDataSetChanged();
    }
}
