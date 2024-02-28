package com.example.login_register_db;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.google.android.material.tabs.TabLayoutMediator;


import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;

public class LocalTasksFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_local_tasks, container, false);

        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        ViewPager2 viewPagertasks = view.findViewById(R.id.viewPagertasks);

        TasksPagerAdapter pagerAdapter = new TasksPagerAdapter(this);
        viewPagertasks.setAdapter(pagerAdapter);

        new TabLayoutMediator(tabLayout, viewPagertasks, (tab, position) -> {
            // Customize tab text or other properties based on position if needed
            switch (position) {
                case 0:
                    tab.setText("My Tasks");
                    break;
                case 1:
                    tab.setText("Visitors");
                    break;
            }
        }).attach();

        FrameLayout frameLayout = view.findViewById(R.id.frametasks);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPagertasks.setVisibility(View.VISIBLE);
                frameLayout.setVisibility(View.VISIBLE);
                viewPagertasks.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewPagertasks.setVisibility(View.VISIBLE);
                frameLayout.setVisibility(View.VISIBLE);
            }
        });

        viewPagertasks.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch(position){
                    case 0:
                    case 1:
                    case 2:
                        tabLayout.getTabAt(position).select();
                }
                super.onPageSelected(position);
            }
        });

        return view;
    }
}
