package com.example.login_register_db;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.Objects;

public class beforeTaskBottomSheet extends AppCompatActivity {
    ImageView imgbck;
    Toolbar toolbar;
    Button starttask;
    BottomSheetBehavior bottomSheetBehavior;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_task_bottom_sheet);

        toolbar = findViewById(R.id.toolbartask1);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);


        imgbck = findViewById(R.id.imgback);
        imgbck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(beforeTaskBottomSheet.this, insideMapActivity.class);
                startActivity(intent);
            }
        });

        starttask = findViewById(R.id.btnstarttask);

        starttask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(beforeTaskBottomSheet.this, MapsTaskActivity.class);
                startActivity(intent);
            }
        });
        showBottomSheetFragment();
    }

    private void showBottomSheetFragment() {
        CustomBottomSheetFragment customBottomSheetFragment = new CustomBottomSheetFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(android.R.id.content, customBottomSheetFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
