package com.example.login_register_db;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;

public class DialogInformationMap extends Dialog {
    public DialogInformationMap(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate your custom layout for dialog_information_map.xml
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_information_map, null);
        setContentView(view);
    }
}
