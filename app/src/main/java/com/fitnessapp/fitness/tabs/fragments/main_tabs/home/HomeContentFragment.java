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

public class HomeContentFragment extends Fragment {

    public HomeContentFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_content, container, false);

        // Set up the See All button listener
        Button seeAllButton = view.findViewById(R.id.see_all_button);
        if (seeAllButton != null) {
            seeAllButton.setOnClickListener(v -> {
                if (getParentFragment() instanceof SeeAllButtonCallback) {
                    // Trigger the callback when the button is clicked
                    ((SeeAllButtonCallback) getParentFragment()).onSeeAllButtonClicked();
                } else {
                    Log.e("HomeContentFragment", "Parent fragment does not implement SeeAllButtonCallback");
                }
            });
        } else {
            Log.e("HomeContentFragment", "see_all_button not found in layout.");
        }

        return view;
    }
}
