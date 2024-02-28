package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class playActivity extends AppCompatActivity {
    String[] question_list = {"Ποιο ρόλο έχει η βασίλισσα μέλισσα στην κυψέλη της","Τι είναι ο χορός της μέλισσας;", "Ποιο ρόλο παίζουν οι μέλισσες στη γεωργία;",
            "Πώς παράγεται το κερί από τις μέλισσες;", "Ποια μέλισσα ζει περισσότερο από τις άλλες;"   };
    String[] choose_list = {"Συλλέγει το νέκταρ από τα άνθη","Γεννά τα αυγά","Κατασκευάζει τα κελιά του κεριού","Πετάνε ψηλά",
            "Μια χορευτική παράσταση για ψυχαγωγία","Ένας τρόπος επικοινωνίας για τον εντοπισμό τροφής","Ένα είδος χορού που κάνουν οι μέλισσες στα πάρτι τους","Κοινωνική ζωή",
            "Είναι γεωργοί που καλλιεργούν φρούτα και λαχανικά","Βοηθούν στην επικοινωνία των ανθέων","Συνεισφέρουν στην καθολική οικονομία","Καλλιεργούν φυτά",
            "Συλλέγουν το κερί από τα δέντρα","Παράγουν το κερί από τα κερινά αδένες τους","Αγοράζουν το κερί από το κατάστημα","ο Ερρίκος",
            "Εργάτρια","Βασίλισσα","Επίσκοπος","QuennB"

    };
    String[] correct_list = {"Γεννά τα αυγά","Ένας τρόπος επικοινωνίας για τον εντοπισμό τροφής"," Είναι γεωργοί που καλλιεργούν φρούτα και λαχανικά","Παράγουν το κερί από τα κερινά αδένες τους","Βασίλισσα"};


    TextView cpt_question , text_question;
    Button btn_choose1 , btn_choose2 , btn_choose3 , btn_choose4 , btn_next;


    int currentQuestion =  0  ;
    int scorePlayer =  0  ;
    boolean isclickBtn = false;
    String valueChoose = "";
    Button btn_click;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        cpt_question = findViewById(R.id.cpt_question);
        text_question = findViewById(R.id.text_question);

        btn_choose1 = findViewById(R.id.btn_choose1);
        btn_choose2 = findViewById(R.id.btn_choose2);
        btn_choose3 = findViewById(R.id.btn_choose3);
        btn_choose4 = findViewById(R.id.btn_choose4);
        btn_next = findViewById(R.id.btn_next);

        findViewById(R.id.image_back).setOnClickListener(
                a-> finish()
        );
        remplirData();
        btn_next.setOnClickListener(
                view -> {
                        if (isclickBtn){
                            isclickBtn = false;

                            if(!valueChoose.equals(correct_list[currentQuestion])){
                                Toast.makeText(playActivity.this , "erreur",Toast.LENGTH_LONG).show();
                                btn_click.setBackgroundResource(R.drawable.background_btn_erreur);

                            }else {
                                Toast.makeText(playActivity.this , "correct",Toast.LENGTH_LONG).show();
                                btn_click.setBackgroundResource(R.drawable.background_btn_correct);

                                scorePlayer++;
                            }
                            new Handler().postDelayed(() -> {
                                if(currentQuestion!=question_list.length-1){
                                    currentQuestion = currentQuestion + 1;
                                    remplirData();
                                    valueChoose = "";
                                    btn_choose1.setBackgroundResource(R.drawable.background_btn_choose);
                                    btn_choose2.setBackgroundResource(R.drawable.background_btn_choose);
                                    btn_choose3.setBackgroundResource(R.drawable.background_btn_choose);
                                    btn_choose4.setBackgroundResource(R.drawable.background_btn_choose);

                                }else {
                                    Intent intent  = new Intent(playActivity.this , ResulteActivity.class);
                                    intent.putExtra("Resute" , scorePlayer);
                                    startActivity(intent);
                                    finish();
                                }

                            },2000);

                        }else {
                            Toast.makeText(playActivity.this ,  "Vous devez en choisir un",Toast.LENGTH_LONG).show();
                        }
                }
        );


    }

    void remplirData(){
        cpt_question.setText((currentQuestion+1) + "/" + question_list.length);
        text_question.setText(question_list[currentQuestion]);

        btn_choose1.setText(choose_list[4 * currentQuestion]);
        btn_choose2.setText(choose_list[4 * currentQuestion+1]);
        btn_choose3.setText(choose_list[4 * currentQuestion+2]);
        btn_choose4.setText(choose_list[4 * currentQuestion+3]);

    }

    public void ClickChoose(View view) {
        btn_click = (Button)view;

        if (isclickBtn) {
            btn_choose1.setBackgroundResource(R.drawable.background_btn_choose);
            btn_choose2.setBackgroundResource(R.drawable.background_btn_choose);
            btn_choose3.setBackgroundResource(R.drawable.background_btn_choose);
            btn_choose4.setBackgroundResource(R.drawable.background_btn_choose);
        }
        chooseBtn();


    }
    void chooseBtn(){

        btn_click.setBackgroundResource(R.drawable.background_btn_choose_color);
        isclickBtn = true;
        valueChoose = btn_click.getText().toString();
    }
}