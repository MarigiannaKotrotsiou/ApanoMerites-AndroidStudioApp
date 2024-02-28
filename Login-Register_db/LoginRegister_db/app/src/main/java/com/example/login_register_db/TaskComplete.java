package com.example.login_register_db;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class TaskComplete extends AppCompatActivity {

    private TextView textView16;
    private FirebaseFirestore ffstore;
    private FirebaseAuth mmAuth;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_complete);

        textView16 = findViewById(R.id.textView16);
        mmAuth = FirebaseAuth.getInstance();
        ffstore = FirebaseFirestore.getInstance();
        userID = mmAuth.getCurrentUser().getUid();

        // Fetch total points from Firestore
        fetchTotalPoints();

        findViewById(R.id.btn_task).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToInsideMapActivity();
            }
        });

        findViewById(R.id.btn_task2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToMainActivity();
            }
        });
    }

    private void fetchTotalPoints() {
        DocumentReference userAchievementsRef = ffstore.collection("users").document(userID);

        userAchievementsRef.get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        long totalPoints = documentSnapshot.getLong("points");
                        updateTotalPointsInView(totalPoints);
                    } else {
                        // Document does not exist
                        // Handle accordingly
                    }
                })
                .addOnFailureListener(e -> {
                    // Handle failure to fetch total points
                    // Log.e("FetchTotalPoints", "Failed to fetch total points: " + e.getMessage());
                });
    }

    private void updateTotalPointsInView(long totalPoints) {
        if (textView16 != null) {
            textView16.setText("Total Points: " + totalPoints);
        }
    }

    private void navigateToInsideMapActivity() {
        Intent intent = new Intent(TaskComplete.this, insideMapActivity.class);
        startActivity(intent);
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(TaskComplete.this, MainActivity.class);
        startActivity(intent);
    }
}
