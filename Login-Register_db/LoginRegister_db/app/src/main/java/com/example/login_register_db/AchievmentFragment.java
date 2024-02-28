package com.example.login_register_db;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class AchievmentFragment extends Fragment {

    ImageView rankImageView;
    TextView rank, points, pointsLeft;
    RecyclerView recyclerViewAch;

    FirebaseAuth mmAuth;
    FirebaseFirestore ffstore;
    String userID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_achievment, container, false);

        rankImageView = view.findViewById(R.id.imgrank); // Replace 'imgrank' with the actual ID of your ImageView

        rank = view.findViewById(R.id.txtRankNum);
        points = view.findViewById(R.id.txttotalpointsnum);
        pointsLeft = view.findViewById(R.id.txtpointsfornextrank);

        ffstore = FirebaseFirestore.getInstance();
        mmAuth = FirebaseAuth.getInstance();

        userID = Objects.requireNonNull(mmAuth.getCurrentUser()).getUid();
        DocumentReference documentReference = ffstore.collection("users").document(userID);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                try {
                    if (documentSnapshot != null) {
                        // Fetch points from quizz1 and quizz2
                        int quiz1Points = documentSnapshot.getLong("quiz1Points") != null ?
                                documentSnapshot.getLong("quiz1Points").intValue() : 0;

                        int quiz2Points = documentSnapshot.getLong("quiz2Points") != null ?
                                documentSnapshot.getLong("quiz2Points").intValue() : 0;

                        // Calculate total points including quizz1 and quizz2
                        int userPoints = documentSnapshot.getLong("points") != null ?
                                documentSnapshot.getLong("points").intValue() : 0;

                        int totalPoints = userPoints + quiz1Points + quiz2Points;

                        // Update achievements in Firestore
                        updateAchievements(totalPoints);

                        // Update UI
                        String userRank = calculateRank(totalPoints);
                        rank.setText(userRank);

                        points.setText(String.valueOf(totalPoints));

                        int pointsForNextRank = calculatePointsForNextRank(totalPoints);
                        pointsLeft.setText(String.valueOf(pointsForNextRank));

                        updateImageView(totalPoints);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return view;
    }

    private void updateAchievements(int points) {
        DocumentReference userAchievementsRef = ffstore.collection("users").document(userID);

        // Update the points field in Firestore
        userAchievementsRef.update("points", points)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Points updated successfully
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle failure to update points
                        Log.e("AchievementUpdate", "Failed to update points: " + e.getMessage());
                    }
                });
    }

    private String calculateRank(int points) {
        int pointsPerRank = 200;

        int rankNumber = (points / pointsPerRank) + 1;

        String[] ranks = {"F", "E", "D", "C", "B", "A", "S"};

        int index = Math.min(rankNumber - 1, ranks.length - 1);

        return ranks[index];
    }

    private int calculatePointsForNextRank(int currentPoints) {
        int pointsPerRank = 200;

        return pointsPerRank - (currentPoints % pointsPerRank);
    }

    private void updateImageView(int userTotalPoints) {
        if (userTotalPoints >= 1600) {  // Assuming S rank starts at 1600 points
            rankImageView.setImageResource(R.drawable.ranks);
        } else if (userTotalPoints >= 1200) {  // Assuming A rank starts at 1200 points
            rankImageView.setImageResource(R.drawable.ranka);
        } else if (userTotalPoints >= 800) {   // Assuming B rank starts at 800 points
            rankImageView.setImageResource(R.drawable.rankb);
        } else if (userTotalPoints >= 300) {
            rankImageView.setImageResource(R.drawable.rankc);
        }
    }
}
