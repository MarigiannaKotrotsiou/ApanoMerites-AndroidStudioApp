package com.example.login_register_db;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TaskComplete extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_complete);
        Button btnTask = findViewById(R.id.btn_task);
        Button btnTask2 = findViewById(R.id.btn_task2);

        btnTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click for btnTask
                Intent intent = new Intent(TaskComplete.this, insideMapActivity.class);
                startActivity(intent);
            }
        });

        btnTask2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click for btnTask2
                Intent intent = new Intent(TaskComplete.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}