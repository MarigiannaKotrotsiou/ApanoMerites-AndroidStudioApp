package com.example.login_register_db;

import static android.app.PendingIntent.getActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditProfile extends AppCompatActivity {

    public static final String TAG = "TAG";
    EditText profileusername, profilefname, profileemail;
    Button uploadbtn;
    Button savebtn;
    ImageView arrowback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        profileusername = findViewById(R.id.profileusername);
        profilefname = findViewById(R.id.profilefname);
        profileemail = findViewById(R.id.profileemail);
        savebtn=findViewById(R.id.savebtn);
        uploadbtn=findViewById(R.id.uploadbtn);
        arrowback=findViewById(R.id.arrowback);


        // Λάβετε τα τρέχοντα δεδομένα από τη βάση δεδομένων
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore fstore = FirebaseFirestore.getInstance();
        String userID = mAuth.getCurrentUser().getUid();
        DocumentReference documentReference = fstore.collection("users").document(userID);

        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    String currentUsername = documentSnapshot.getString("username");
                    String currentFname = documentSnapshot.getString("fname");
                    String currentEmail = documentSnapshot.getString("email");

                    // Εμφανίστε τα τρέχοντα δεδομένα στα πεδία
                    profileusername.setText(currentUsername);
                    profilefname.setText(currentFname);
                    profileemail.setText(currentEmail);
                } else {
                    Log.d(TAG, "Document does not exist");
                }
            }
        });

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Αποθηκεύστε τις αλλαγές στη βάση δεδομένων
                String newUsername = profileusername.getText().toString();
                String newFname = profilefname.getText().toString();
                String newEmail = profileemail.getText().toString();

                // Ενημερώστε τα δεδομένα στη βάση δεδομένων
                DocumentReference userRef = fstore.collection("users").document(userID);
                userRef.update("username", newUsername, "fname", newFname, "email", newEmail)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(EditProfile.this, "Οι αλλαγές αποθηκεύτηκαν επιτυχώς", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(EditProfile.this, "Σφάλμα κατά την αποθήκευση αλλαγών", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });


// Προσθήκη του arrow back
        arrowback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Πραγματοποίηση μετάβασης πίσω στο MainActivity
                Intent intent = new Intent(EditProfile.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });




        Log.d(TAG, "onCreate");
    }
}