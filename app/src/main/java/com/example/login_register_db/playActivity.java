package com.example.login_register_db;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class playActivity extends AppCompatActivity {
    String[] question_list = {
            "Σε ποια ομοταξία ζωντανών οργανισμών ανήκει η μέλισσα;",
            "Πως ονομάζονται οι αρσενικές μέλισσες;",
            "Τι από τα παρακάτω ΔΕΝ παράγουν οι μέλισσες;",
            "Πόσο καιρό ζουν οι μέλισσες στον πλανήτη;"
    };
    String[] choose_list = {
            "Αμφίβια", "Έντομα", "Ερπετά", "Πτηνά",
            "Κυνηγοί", "Ικέτες", "Κηφήνες", "Πρίγκιπες",
            "Κολλαγόνο", "Πρόπολη", "Κερί", "Βασιλικό πολτό",
            "150 χιλιάδες\nχρόνια", "5 εκατομμύρια\n χρόνια", "150 εκατομμύρια\nχρόνια", "15 εκατομμύρια\nχρόνια"
    };

    String[] correct_list = {
            "Έντομα",
            "Κηφήνες",
            "Κολλαγόνο",
            "15 εκατομμύρια\nχρόνια"
    };
    String[] explanation_list = {
            "Τα έντομα χαρακτηρίζονται από εξωτερικό σκελετό, έξι πόδια, και δύο ή περισσότερα ζευγάρια φτερών. Η μέλισσα πληροί αυτά τα χαρακτηριστικά,  καθιστώντας την ένα έντομο.",
            "Οι αρσενικές μέλισσες, γνωστές και ως κηφήνες, έχουν κύριο ρόλο στη γονιμοποίηση των κηρυκωνιών, δηλαδή των θηλυκών μελισσών.",
            "Οι μέλισσες παράγουν κυρίως μέλι, κερί, πρόπολη και βασιλικό πολτό. Το κολλαγόνο είναι μία πρωτεΐνη που συνήθως παράγεται από άλλα ζώα, όχι από μέλισσες.",
            "Η μέλισσα ζει στη Γη το λιγότερο 15 εκατομμύρια χρόνια και θεωρείται από τους πιο παλαιούς κατοίκους της Γης, που εξακολουθεί να υπάρχει ακόμη και σήμερα."
    };


    TextView cpt_question , text_question;
    Button btn_choose1 , btn_choose2 , btn_choose3 , btn_choose4 , btn_next;
    int totalPoints = 299;
    // Define a score value for each correct answer
    int[] questionScores = {1, 1, 1, 1};

    int currentQuestion = 0;
    int scorePlayer = 0;
    boolean isClickBtn = false;
    String valueChoose = "";
    Button btnClick;

    FirebaseAuth mmAuth;
    FirebaseFirestore ffstore;
    String userID;
    int quiz1Points = 0;
    ViewFlipper viewFlipper;
    TextView txtcorrectanswe, txtanswer, txtanswedeccription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_flipper);

        viewFlipper  = findViewById(R.id.viewFlipper); // Initialize viewFlipper first
        View explanationView = getLayoutInflater().inflate(R.layout.explanation_quizz, viewFlipper, false);
        viewFlipper.addView(explanationView);

        txtcorrectanswe = findViewById(R.id.txtcorrectanwser);
        txtanswer = findViewById(R.id.txtanswer);
        txtanswedeccription = findViewById(R.id.txtanswerdescription);


        cpt_question = findViewById(R.id.cpt_question);
        text_question = findViewById(R.id.text_question);

        btn_choose1 = findViewById(R.id.btn_choose1);
        btn_choose2 = findViewById(R.id.btn_choose2);
        btn_choose3 = findViewById(R.id.btn_choose3);
        btn_choose4 = findViewById(R.id.btn_choose4);
        btn_next = findViewById(R.id.btn_next);
        // Initialize ProgressBar
        ProgressBar progressBar = findViewById(R.id.progressBar2);
        progressBar.setProgress(0);
        findViewById(R.id.image_back).setOnClickListener(
                a -> finish()
        );
        remplirData();
        btn_next.setOnClickListener(view -> {
            if (isClickBtn) {
                isClickBtn = false;

                viewFlipper.showNext();

                if (!valueChoose.equals(correct_list[currentQuestion])) {
                    Toast.makeText(playActivity.this, "Λάθος", Toast.LENGTH_LONG).show();
                    btnClick.setBackgroundResource(R.drawable.background_btn_erreur);
                } else {
                    Toast.makeText(playActivity.this, "Σωστό", Toast.LENGTH_LONG).show();
                    btnClick.setBackgroundResource(R.drawable.background_btn_correct);

                    scorePlayer += questionScores[currentQuestion];
                    quiz1Points += totalPoints / question_list.length;

                    updateQuizPointsInFirestore();
                }

                txtanswer.setText(correct_list[currentQuestion]);
                txtanswedeccription.setText(explanation_list[currentQuestion]);

                new Handler().postDelayed(() -> {
                    // Move to the next question or finish the quiz
                    if (currentQuestion != question_list.length - 1) {
                        currentQuestion = currentQuestion + 1;
                        remplirData();
                        valueChoose = "";
                        btn_choose1.setBackgroundResource(R.drawable.quizzbtn);
                        btn_choose2.setBackgroundResource(R.drawable.quizzbtn);
                        btn_choose3.setBackgroundResource(R.drawable.quizzbtn);
                        btn_choose4.setBackgroundResource(R.drawable.quizzbtn);

                        viewFlipper.showPrevious(); // Switch back to quizz layout
                    } else {
                        Intent intent = new Intent(playActivity.this, ResulteActivity.class);
                        intent.putExtra("Result", scorePlayer);
                        startActivity(intent);
                        finish();
                    }
                }, 10000);
            } else {
                Toast.makeText(playActivity.this, "Πρέπει να απαντήσετε", Toast.LENGTH_LONG).show();
            }
        });
        mmAuth = FirebaseAuth.getInstance();
        ffstore = FirebaseFirestore.getInstance();
        userID = Objects.requireNonNull(mmAuth.getCurrentUser()).getUid();
    }



    void remplirData() {
        cpt_question.setText((currentQuestion + 1) + "/" + question_list.length);
        text_question.setText(question_list[currentQuestion]);

        btn_choose1.setText(choose_list[4 * currentQuestion]);
        btn_choose2.setText(choose_list[4 * currentQuestion + 1]);
        btn_choose3.setText(choose_list[4 * currentQuestion + 2]);
        btn_choose4.setText(choose_list[4 * currentQuestion + 3]);

        // Reset button backgrounds
        btn_choose1.setBackgroundResource(R.drawable.quizzbtn);
        btn_choose2.setBackgroundResource(R.drawable.quizzbtn);
        btn_choose3.setBackgroundResource(R.drawable.quizzbtn);
        btn_choose4.setBackgroundResource(R.drawable.quizzbtn);

        // Update ProgressBar
        ProgressBar progressBar = findViewById(R.id.progressBar2);
        int progress = (int) (((float) (currentQuestion + 1) / question_list.length) * 100);
        progressBar.setProgress(progress);
    }

    public void ClickChoose(View view) {
        btnClick = (Button)view;

        if (isClickBtn) {
            btn_choose1.setBackgroundResource(R.drawable.quizzbtn);
            btn_choose2.setBackgroundResource(R.drawable.quizzbtn);
            btn_choose3.setBackgroundResource(R.drawable.quizzbtn);
            btn_choose4.setBackgroundResource(R.drawable.quizzbtn);
        }
        chooseBtn();
    }
    void chooseBtn(){
        btnClick.setBackgroundResource(R.drawable.background_btn_choose_color);
        isClickBtn = true;
        valueChoose = btnClick.getText().toString();
    }


    private void updateQuizPointsInFirestore() {
        DocumentReference userAchievementsRef = ffstore.collection("users").document(userID);

        // Update quiz-specific points in Firestore
        userAchievementsRef.update("quiz1Points", quiz1Points)
                .addOnSuccessListener(aVoid -> {
                    // Quiz points updated successfully
                })
                .addOnFailureListener(e -> {
                    // Handle failure to update quiz points
                    // Log.e("AchievementUpdate", "Failed to update quiz points: " + e.getMessage());
                });
    }
}