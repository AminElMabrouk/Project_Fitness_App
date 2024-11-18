package com.fitnessapp.fitness.tabs;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.fitnessapp.fitness.tabs.fragments.GuideFragment;
import com.fitnessapp.fitness.tabs.fragments.HomeFragment;
import com.fitnessapp.fitness.tabs.fragments.StatsFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: return new HomeFragment();
            case 1: return new GuideFragment();
            case 2: return new StatsFragment();
            default: return new HomeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3; // Number of tabs
    }
}