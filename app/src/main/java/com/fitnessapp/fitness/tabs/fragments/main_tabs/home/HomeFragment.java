package com.fitnessapp.fitness.tabs.fragments.main_tabs.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.fitnessapp.fitness.R;
import com.fitnessapp.fitness.tabs.fragments.main_tabs.home.see_all.see_all_workout;

public class HomeFragment extends Fragment implements SeeAllButtonCallback {

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("HomeFragment", "onCreate called");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("HomeFragment", "onCreateView called");
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("HomeFragment", "onViewCreated called");

        // Initialize HomeContentFragment
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.home_content_container, new HomeContentFragment())
                .commit();
    }

    @Override
    public void onSeeAllButtonClicked() {
        // Handle the See All button click
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.home_content_container, new see_all_workout())
                .addToBackStack(null)
                .commit();
    }
}
