package com.example.login_register_db;

import android.os.Bundle;
import android.view.MenuItem;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.login_register_db.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationView = findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.body_container, new mapFragment()).commit();
        navigationView.setSelectedItemId(R.id.map);

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;

                switch (item.getItemId()) {
                    case R.id.map:
                        fragment = new mapFragment();
                        break;
                    case R.id.achievements:
                        fragment = new AchievmentFragment();
                        break;
                    case R.id.tasks:
                        fragment = new tasksFragment();
                        break;
                    case R.id.profile:
                        fragment = new profileFragment();
                        break;
                }

                if (fragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.body_container, fragment).commit();
                }

                return true;
            }
        });
    }
}
