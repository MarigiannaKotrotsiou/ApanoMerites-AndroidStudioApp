package com.example.login_register_db;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class LocalProfileFragment extends Fragment {

    TextView Fname, Username, Email;
    FirebaseAuth mAuth;
    FirebaseFirestore fstore;
    String userID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_local_profile, container, false);
        Username = view.findViewById(R.id.profileusernamelocal);
        Fname = view.findViewById(R.id.profilefnamelocal);
        Email = view.findViewById(R.id.profileemaillocal);
        fstore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        ImageView settingsIcon = view.findViewById(R.id.settings2);
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
                }
            }
        });

        settingsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getContext(), v);
                popupMenu.inflate(R.menu.settings_items);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.logout:
                                mAuth.signOut();
                                Intent intent = new Intent(getActivity(), Recycler.class);
                                startActivity(intent);
                                getActivity().finish();
                                return true;
                            case R.id.deleteAccount:
                                 return true;
                            default:
                                return false;
                        }
                    }
                });

                popupMenu.show();
            }
        });



        View editprofbtn = view.findViewById(R.id.editprofbtnlocal); // Υποθέτω ότι έχεις ένα κουμπί με αυτό το ID στο fragment_map.xml

        editprofbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), EditProfile.class); // Χρησιμοποιήστε getActivity() αν είστε μέσα σε ένα fragment
                startActivity(intent);
                getActivity().finish();
            }
        });


        return view;
    }
}