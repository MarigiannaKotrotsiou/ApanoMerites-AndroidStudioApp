package com.example.login_register_db;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.gms.tasks.OnSuccessListener;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.concurrent.Executor;

import android.view.MenuItem;
import android.widget.PopupMenu;
public class profileFragment extends Fragment {


    TextView Fname, Username, Email;
    FirebaseAuth mAuth;
    FirebaseFirestore fstore;
    String userID;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        Username = view.findViewById(R.id.profileusername);
        Fname = view.findViewById(R.id.profilefname);
        Email = view.findViewById(R.id.profileemail);
        fstore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();  // Αρχικοποίηση του αντικειμένου mAuth
        ImageView settingsIcon = view.findViewById(R.id.settings);

        userID = mAuth.getCurrentUser().getUid();
        DocumentReference documentReference = fstore.collection("users").document(userID);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot != null) {
                    Username.setText(documentSnapshot.getString("username"));
                    Fname.setText(documentSnapshot.getString("fname"));
                    Email.setText(documentSnapshot.getString("email"));
                } else {
                    // Handle the case where documentSnapshot is null or an error occurred
                }
            }
        });

        settingsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getContext(), v);
                popupMenu.inflate(R.menu.settings_items); // Δηλώστε ένα αρχείο menu στον φάκελο res/menu

                // Ορίστε τη συμπεριφορά για κάθε επιλογή στο μενού
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.logout:
                                // Κώδικας για αποσύνδεση χρήστη
                                mAuth.signOut();
                                Intent intent = new Intent(getActivity(), Recycler.class);
                                startActivity(intent);
                                getActivity().finish();
                                return true;
                            case R.id.deleteAccount:
                                // Κώδικας για διαγραφή λογαριασμού χρήστη
                                // Προσέξτε τη διαγραφή λογαριασμού, είναι μια σοβαρή ενέργεια
                                // Εξασφαλίστε ότι έχετε τις κατάλληλες επιβεβαιώσεις
                                // Και πιθανόν να χρειαστείτε επιπλέον επιβεβαιώσεις από το χρήστη
                                return true;
                            default:
                                return false;
                        }
                    }
                });

                // Εμφανίστε το PopupMenu
                popupMenu.show();
            }
        });



        View editprofbtn = view.findViewById(R.id.editprofbtn); // Υποθέτω ότι έχεις ένα κουμπί με αυτό το ID στο fragment_map.xml

        editprofbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               
                // Προωθήστε στο Recycler
                Intent intent = new Intent(getActivity(), EditProfile.class); // Χρησιμοποιήστε getActivity() αν είστε μέσα σε ένα fragment
                startActivity(intent);
                getActivity().finish();
            }
        });
        

        return view;
    }}