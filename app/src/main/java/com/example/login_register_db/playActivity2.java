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
public class playActivity2 extends AppCompatActivity {

    String[] question_list = {
            "Πως ονομάζεται η παραλία στο τέλος του μονοπατιού",
            "Η διαδρομή είναι διάσημη για:",
            "Κατα την διάρκεια \nτης διαδρομής μπορεί να συναντήσατε κάποιους\n μη-ανθρώπινους φίλους! \nΓνωρίζετε τι είδος ζώου είναι;",
            "Κατά την διάρκεια της διαδρομης \nπιθανών να παρατηρίσατε διάφορα βότανα.\n Ποια πιστεύεται πως είναι;"
    };
    String[] choose_list = {
            "Αμερικάνος","Γράμματα","Λία","Βαρβαρούσα",
            "Την παραλία","Τον γεωλογικό\n χαρακτήρα","Την Θέα","Την σπηλιά",
            "Αίγαγρος της\nΕρημομήλου","Κρι-Κρι","Γίδια","Καλαρρύτικο αρνί",
            "Φασκόμηλο","θυμάρι","Λεβάντα","Όλα απο τα παραπάνω"
    };
    String[] correct_list = {
            "Λία",
            "Τον γεωλογικό\n χαρακτήρα",
            "Αίγαγρος της\nΕρημομήλου",
            "Όλα απο τα παραπάνω"
    };

    String[] explanation_list = {
            "Η παραλία στο τέλος του μονοπατιού ονομάζεται “Λία”. Είναι προσβάσιμη είτε μέσω του μονοπατιού, είτε με καΐκι από την παραλία Κίνι. Στην παραλία μπορείτε να δείτε πολλά αρμυρίκια, όπως και τον βράχο των Γραμμάτων στην απέναντι πλευρά.",
            "Ο αερόλιθος είναι ένα από τα πιο σημαντικά σημεία γεωλογικού ενδιαφέροντος στην Απάνω Μεριά. Ο μύθος γύρω από τον αερόλιθο ισχυρίζεται πως ο βράχος έχει πέσει από το διάστημα, απ’ όπου πήρε και το όνομα του. Ειδικοί αναφέρουν πως ο βράχος έχει αναδυθεί από την γη ενω το συγκεκριμένο είδος πετρώματος ονομάζεται “εκλογίτης”.",
            "Ενδημικό υποείδος, που υπάρχει μόνο στην Ερημόμηλο και συγγενεύει με το Αγρίμι ή Αίγαγρο της Κρήτης. Και τα 2 ελληνικά υποείδη μοιάζουν μεταξύ τους, αλλά το υποείδος των Κυκλάδων χαρακτηρίζεται από καφεκόκκινο χρώμα, ιδίως το θηλυκό, και έντονα τα μαύρα σημεία του σώματος. Σήμερα οι Αίγαγροι της Ερημομήλου προστατεύονται, και δεν φαίνεται τουλάχιστο στα άμεσο μέλλον να απειλούνται με εξαφάνιση.",
            "Και τα τρία φυτά υπάρχουν σε αυτή την περιοχή και ανήκουν στην ταξινομία “φρυγάνων”. Τα φρύγανα είναι χαμηλοί θάμνοι που συνήθως αλλάζουν τα φύλλα τους την άνοιξη και το φθινόπωρο, έχουν πολύ τρίχωμα, αγκάθια και μυρίζουν έντονα λόγω των αιθέριων ελαίων τους. Συναντιούνται συχνά σε μεσογειακά οικοσυστήματα καθώς αναπτύσσονται σε βραχώδη εδάφη και έχουν πολύ μεγάλη αντοχή στις υψηλές θερμοκρασίες."
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
                    Toast.makeText(playActivity2.this, "Λάθος", Toast.LENGTH_LONG).show();
                    btnClick.setBackgroundResource(R.drawable.background_btn_erreur);
                } else {
                    Toast.makeText(playActivity2.this, "Σωστό", Toast.LENGTH_LONG).show();
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
                        Intent intent = new Intent(playActivity2.this, ResulteActivity.class);
                        intent.putExtra("Result", scorePlayer);
                        startActivity(intent);
                        finish();
                    }
                }, 10000);
            } else {
                Toast.makeText(playActivity2.this, "Πρέπει να απαντήσετε", Toast.LENGTH_LONG).show();
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