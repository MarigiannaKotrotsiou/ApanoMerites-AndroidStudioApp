package com.example.login_register_db;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class TasksPagerAdapter extends FragmentStateAdapter {

    public TasksPagerAdapter(LocalTasksFragment fm) {
        super(fm);
    }

    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new MyTasksFragment();
            case 1:
                return new VisitorTasksFragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        // Number of tabs
        return 2;
    }
}
