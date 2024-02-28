package com.example.login_register_db;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class Nfc extends AppCompatActivity {

    private static final long DELAY_BEFORE_SECOND_LAYOUT = 7000; // 7 seconds delay

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide the action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Make the activity full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_nfc);

        // Delay the transition to the second layout
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Switch to the second layout after the delay
                setContentView(R.layout.activity_closenfc);

                // Delay the return to the MapsTaskActivity
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Start the MapsTaskActivity
                        startActivity(new Intent(Nfc.this, MapsTaskActivity.class));

                        // Finish the current activity
                        finish();
                    }
                }, DELAY_BEFORE_SECOND_LAYOUT);
            }
        }, DELAY_BEFORE_SECOND_LAYOUT);
    }
}
