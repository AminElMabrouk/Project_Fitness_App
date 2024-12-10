package com.fitnessapp.fitness.tabs.fragments.main_tabs.stat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.fitnessapp.fitness.R;

public class StatsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Lie le layout XML au fragment
        View view = inflater.inflate(R.layout.fragment_stats, container, false);

        // Récupère les éléments de l'interface
        ProgressBar progressBar = view.findViewById(R.id.progressCircle);
        TextView progressPercentage = view.findViewById(R.id.tvProgressPercentage);

        // Définir la progression (Exemple : 65%)
        int progress = 65;
        progressBar.setProgress(progress);
        progressPercentage.setText(progress + "%");

        return view;
    }
}
