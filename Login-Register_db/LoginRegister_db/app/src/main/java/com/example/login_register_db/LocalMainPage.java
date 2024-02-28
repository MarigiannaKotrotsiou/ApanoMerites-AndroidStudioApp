package com.example.login_register_db;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class LocalMainPage extends AppCompatActivity {

    BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_main_page);

        navigationView = findViewById(R.id.bottom_navigation2);
        getSupportFragmentManager().beginTransaction().replace(R.id.body_containerlocal, new LocalMapFragment()).commit();
        navigationView.setSelectedItemId(R.id.maplocal);

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;

                switch (item.getItemId()) {
                    case R.id.maplocal:
                        fragment = new LocalMapFragment();
                        break;

                    case R.id.taskslocal:
                        fragment = new LocalTasksFragment();
                        break;
                    case R.id.profilelocal:
                        fragment = new LocalProfileFragment();
                        break;
                }

                if (fragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.body_containerlocal, fragment).commit();
                }

                return true;
            }
        });
    }
}