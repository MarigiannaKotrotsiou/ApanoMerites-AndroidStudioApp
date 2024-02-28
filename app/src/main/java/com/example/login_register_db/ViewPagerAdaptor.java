package com.example.login_register_db;
import android.content.Intent;
import android.view.LayoutInflater;
import androidx.annotation.NonNull;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class ViewPagerAdaptor extends PagerAdapter {

    Context context;
    private ViewPager mSlideViewPager;


    int headings[] = {
            R.string.slide_heading_1,
            R.string.slide_heading_1,
            R.string.slide_heading_1,
            R.string.slide_heading_1
    };

    int descriptions [] = {
            R.string.slide_description_1,
            R.string.slide_description_2,
            R.string.slide_description_3,
            R.string.slide_description_4
    };

    public ViewPagerAdaptor(Context context, ViewPager viewPager){

        this.context = context;
        this.mSlideViewPager = viewPager;
    }


    @Override
    public int getCount() {
        return headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object; // No need for casting
    }

    // Add the following code to handle region information
    RegionInfo[] regionInfos = {
            new RegionInfo(R.id.img_kampos, "Μέτρια", "Βραχώδης", "0/7", "0/2", 1),
            new RegionInfo(R.id.img_xalandriani, "Εύκολη", "Βραχώδες", "0/7", "0/2", 2),
            new RegionInfo(R.id.img_papouri, "Δύσκολη", "Βραχώδες", "0/7", "0/2", 3)
    };

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view;

        if (position == 2) {
            // Load slider3_layout.xml for the third position
            view = layoutInflater.inflate(R.layout.slider3_layout, container, false);
            ImageButton xbtn = view.findViewById(R.id.xbtn);
            xbtn.setOnClickListener(v -> {
                // Handle the xbtn click event, for example, navigate back to the main activity
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            });
            // ... (rest of the code for the third position)
        } else if (position == 3) {
            // Load slider4_layout.xml for the last position
            view = layoutInflater.inflate(R.layout.slider4_layout, container, false);
            ImageButton xbtn = view.findViewById(R.id.xbtn);
            xbtn.setOnClickListener(v -> {
                // Handle the xbtn click event, for example, navigate back to the main activity
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            });
            // Find Button element from slider4_layout.xml
            Button button3 = view.findViewById(R.id.btns4);
            button3.setOnClickListener(v -> {
                // Handle the button3 click event, navigate to InsideMapActivity
                Intent intent = new Intent(context, insideMapActivity.class);
                context.startActivity(intent);
            });
            // ... (rest of the code for the last position)
        } else {
            // Load the default slider_layout.xml for other positions
            view = layoutInflater.inflate(R.layout.slider_layout, container, false);

            // Find TextView elements from slider_layout.xml
            TextView skipInfo = view.findViewById(R.id.skipInfo);
            TextView sliderHeading = view.findViewById(R.id.texttitle);
            TextView sliderDescription = view.findViewById(R.id.textdeccription);

            ImageButton xbtn = view.findViewById(R.id.xbtn);
            xbtn.setOnClickListener(v -> {
                // Handle the xbtn click event, for example, navigate back to the main activity
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            });

            skipInfo.setOnClickListener(v -> {
                int lastPagePosition = getCount() - 1;

                if (lastPagePosition == position) {
                    // If it's already the last page, you may perform some other action if needed
                } else {
                    // If it's not the last page, navigate to the last page
                    mSlideViewPager.setCurrentItem(lastPagePosition, true);
                }
            });

            sliderHeading.setText(headings[position]);
            sliderDescription.setText(descriptions[position]);
        }

        container.addView(view);
        return view;
    }



    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((LinearLayout)object);
    }
}