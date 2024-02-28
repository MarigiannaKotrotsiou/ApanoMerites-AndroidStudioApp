package com.example.login_register_db;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mAuth = FirebaseAuth.getInstance();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Check if the user is already logged in
                FirebaseUser currentUser = mAuth.getCurrentUser();

                if (currentUser != null) {
                    // User is logged in, get user type from SharedPreferences
                    String userType = getUserTypeFromSharedPreferences();

                    if (!TextUtils.isEmpty(userType)) {
                        // User type is not empty, navigate to the correct screen
                        if ("visitor".equals(userType)) {
                            startActivity(new Intent(SplashScreen.this, MainActivity.class));
                        } else if ("local".equals(userType)) {
                            startActivity(new Intent(SplashScreen.this, LocalMainPage.class));
                        }
                    } else {
                        // User type is empty, navigate to the onboarding screen
                        startActivity(new Intent(SplashScreen.this, OnBoarding.class));
                    }
                } else {
                    // User is not logged in, navigate to the onboarding screen
                    startActivity(new Intent(SplashScreen.this, OnBoarding.class));
                }

                finish();
            }
        }, 3000);
    }

    // Function to get user type from SharedPreferences
    private String getUserTypeFromSharedPreferences() {
        SharedPreferences preferences = getSharedPreferences("userType", MODE_PRIVATE);
        return preferences.getString("userType", "");
    }
}
