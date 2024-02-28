package com.example.login_register_db;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import androidx.viewpager2.widget.ViewPager2;

public class ViewPager2Adaptor extends RecyclerView.Adapter<ViewPager2Adaptor.ViewHolder> {

    private Context context;
    private ViewPager2 mSlideViewPager;

    private int[] images = {
            R.drawable.icons,
            R.drawable.nfc,
            R.drawable.vanilia,
            R.drawable.close
    };

    private String[] descriptions = {
            "Κοίτα γύρω σου και ψάξε να βρεις ένα πράσινο κουτί. ",
            "Όταν το βρείς πάτα το κουμπί στο κινητό σου για να ανοίξει. ",
            "Πάρε μια βανίλια μέσα από το κουτί.",
            "Πριν φύγεις μην ξεχάσεις να κλείσεις το κουτί και να πατήσεις ολοκλήρωση."
    };

    public ViewPager2Adaptor(Context context, ViewPager2 viewPager) {
        this.context = context;
        this.mSlideViewPager = viewPager;
    }

    // Implement necessary methods for RecyclerView.Adapter

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_slider_page, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imageView.setImageResource(images[position]);
        holder.textDescription.setText(descriptions[position]);
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView5);
            textDescription = itemView.findViewById(R.id.textDescriptionpager);
        }
    }
}
