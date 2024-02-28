package com.example.login_register_db;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Recycler extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        ImageView visitorImage = findViewById(R.id.visitorImage);
        ImageView localImage = findViewById(R.id.localImage);

        visitorImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Pass information that user clicked visitor
                Intent intent = new Intent(Recycler.this, Register.class);
                intent.putExtra("userType", "visitor");
                startActivity(intent);
            }
        });

        localImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Recycler.this, Login.class);
                intent.putExtra("userType", "local");
                startActivity(intent);
            }
        });
    }
}

