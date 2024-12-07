package com.fitnessapp.fitness;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.program_add_day);

        // Récupération des vues
        CalendarView calendarView = findViewById(R.id.calendarView);
        ImageView headerImage = findViewById(R.id.image);

        // Affichage d'une image dans le Header
        headerImage.setImageResource(R.drawable.pilate); // Assurez-vous que l'image existe dans res/drawable

        // Listener pour gérer la sélection des dates dans le calendrier
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
            Toast.makeText(MainActivity.this, "Date sélectionnée : " + selectedDate, Toast.LENGTH_SHORT).show();

            // Appeler une méthode pour mettre à jour les routines en fonction de la date sélectionnée
            updateWorkoutCards(selectedDate);
        });
    }

    /**
     * Méthode pour mettre à jour les routines d'exercices en fonction de la date sélectionnée.
     *
     * @param date La date sélectionnée par l'utilisateur
     */
    private void updateWorkoutCards(String date) {
        // Exemple simple de mise à jour dynamique (remplacer par une vraie logique)
        if (date.equals("10/12/2024")) {
            Toast.makeText(this, "Routine spéciale pour le 10 décembre !", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Routines standards affichées pour " + date, Toast.LENGTH_SHORT).show();
        }

        // Vous pouvez ajouter des changements dynamiques dans l'affichage des cartes ici
    }
}
