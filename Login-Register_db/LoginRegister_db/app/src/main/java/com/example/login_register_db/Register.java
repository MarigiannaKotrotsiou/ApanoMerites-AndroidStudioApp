// RegisterActivity
package com.example.login_register_db;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    TextInputEditText editTextUsername, editTextFname, editTextEmail, editTextPassword;
    Button buttonReg;
    FirebaseAuth mAuth;
    FirebaseFirestore fstore;
    String userID;
    ProgressBar progressBar;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Intent intent = getIntent();
        String userType = intent.getStringExtra("userType");

        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        editTextUsername = findViewById(R.id.profileusername);
        editTextFname = findViewById(R.id.profilefname);
        editTextEmail = findViewById(R.id.profileemail);
        editTextPassword = findViewById(R.id.password);
        buttonReg = findViewById(R.id.btn_register);
        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.loginNow);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                intent.putExtra("userType", userType);
                startActivity(intent);
                finish();
            }
        });

        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String username, fname, email, password;
                username = String.valueOf(editTextUsername.getText());
                fname = String.valueOf(editTextFname.getText());
                email = String.valueOf(editTextEmail.getText());
                password = String.valueOf(editTextPassword.getText());

                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(Register.this, "Enter Username", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(fname)) {
                    Toast.makeText(Register.this, "Enter Fullname", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(Register.this, "Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(Register.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);

                                if (task.isSuccessful()) {
                                    userID = mAuth.getCurrentUser().getUid();

                                    DocumentReference documentReference = fstore.collection("users").document(userID);
                                    Map<String, Object> user = new HashMap<>();
                                    user.put("username", username);
                                    user.put("fname", fname);
                                    user.put("email", email);

                                    documentReference.set(user).addOnSuccessListener(aVoid -> {
                                        Toast.makeText(Register.this, "User Profile created", Toast.LENGTH_SHORT).show();
                                    });

                                    Toast.makeText(Register.this, "Account Created.", Toast.LENGTH_SHORT).show();

                                    // Save user type to SharedPreferences
                                    saveUserTypeToSharedPreferences(userType);

                                    if ("visitor".equals(userType)) {
                                        Intent intent = new Intent(getApplicationContext(), InstructionsActivity.class);
                                        startActivity(intent);
                                    } else if ("local".equals(userType)) {
                                        Intent intent = new Intent(getApplicationContext(), LocalMainPage.class);
                                        startActivity(intent);
                                    }

                                    finish();
                                } else {
                                    Toast.makeText(Register.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
    private void saveUserTypeToSharedPreferences(String userType) {
        SharedPreferences.Editor editor = getSharedPreferences("userType", MODE_PRIVATE).edit();
        editor.putString("userType", userType);
        editor.apply();
    }

}

