package com.example.login_register_db;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomListAdapter extends ArrayAdapter {

    private Context context;
    private ArrayList<MyTask> mytasks;
    ArrayList<Integer> shortlistedIDs;
    private SharedPreferences myPreferences;

    private SharedPreferences.Editor myEditor;

    public CustomListAdapter(Context context, int resource, ArrayList<MyTask> events) {
        super(context, resource, events);

        this.context = context;
        this.mytasks = events;

        initializePreferences();
        shortlistedIDs = new ArrayList<>();
    }


    private void initializePreferences() {
        myPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        myEditor = myPreferences.edit();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.listview_mytaskrow, parent, false);
            Log.d("CustomListAdapter", "getView called for position: " + position);

        }

        TextView txtTitle = convertView.findViewById(R.id.txttitlerowmytask);
        TextView txtDescr = convertView.findViewById(R.id.txtdecrrowmytask);
        ImageView imgEvent = convertView.findViewById(R.id.mytaskimg);

        txtTitle.setText(mytasks.get(position).getTitle());
        txtDescr.setText(mytasks.get(position).getDescr());
        imgEvent.setImageResource(mytasks.get(position).getImage());

        return convertView;
    }
}


