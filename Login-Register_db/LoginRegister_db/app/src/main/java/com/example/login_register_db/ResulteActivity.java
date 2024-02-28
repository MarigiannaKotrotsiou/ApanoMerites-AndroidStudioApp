package com.example.login_register_db;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ResulteActivity extends AppCompatActivity {

    TextView textView;
    TextView pointsTextView;

    FirebaseAuth mmAuth;
    FirebaseFirestore ffstore;
    String userID;

    // Add fields for quiz points
    private int quiz1Points = 0; // Change this name accordingly
    private int quiz2Points = 0; // Change this name accordingly

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resulte);

        textView = findViewById(R.id.textView);
        pointsTextView = findViewById(R.id.pointsTextView);

        mmAuth = FirebaseAuth.getInstance();
        ffstore = FirebaseFirestore.getInstance();
        userID = Objects.requireNonNull(mmAuth.getCurrentUser()).getUid();

        // Fetch quiz1Points and quiz2Points from Firestore
        fetchQuizPoints();

        Button restartButton = findViewById(R.id.btn_restart);
        restartButton.setOnClickListener(restart -> {
            Intent intent = new Intent(ResulteActivity.this, MapsTaskActivity.class);
            intent.putExtra("TotalPoints", quiz1Points + quiz2Points); // Sum of quiz1 and quiz2 points
            startActivity(intent);
            finish();
        });
    }

    private void fetchQuizPoints() {
        DocumentReference userAchievementsRef = ffstore.collection("users").document(userID);

        // Fetch quiz1Points and quiz2Points from Firestore
        userAchievementsRef.get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        quiz1Points = documentSnapshot.getLong("quiz1Points") != null ?
                                documentSnapshot.getLong("quiz1Points").intValue() : 0;

                        quiz2Points = documentSnapshot.getLong("quiz2Points") != null ?
                                documentSnapshot.getLong("quiz2Points").intValue() : 0;

                        updateUI();
                    } else {
                        // Document does not exist
                        // Handle accordingly
                    }
                })
                .addOnFailureListener(e -> {
                    // Handle failure to fetch quiz points
                    // Log.e("FetchQuizPoints", "Failed to fetch quiz points: " + e.getMessage());
                });
    }

    private void updateUI() {
        int totalPoints = quiz1Points + quiz2Points;

        // Update the total points in Firestore
        updateTotalPoints(totalPoints);

        textView.setText("Score: " + totalPoints);
        pointsTextView.setText("Κερδίσατε: " + totalPoints + " Πόντους");
    }

    private void updateTotalPoints(int totalPoints) {
        DocumentReference userAchievementsRef = ffstore.collection("users").document(userID);

        // Update the total points field in Firestore
        userAchievementsRef.update("points", totalPoints)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Total points updated successfully
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle failure to update total points
                        // Log.e("UpdateTotalPoints", "Failed to update total points: " + e.getMessage());
                    }
                });
    }}
