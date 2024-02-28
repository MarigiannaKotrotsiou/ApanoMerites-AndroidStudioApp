package com.example.login_register_db;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.text.Html;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SliderActivity extends AppCompatActivity {

    ViewPager mSlideViewPager;
    LinearLayout mDotLayout;

    private ViewPagerAdaptor viewPagerAdaptor;
    private TextView[] dots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Hide the action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        setContentView(R.layout.activity_slider);

        setContentView(R.layout.activity_slider);



        mSlideViewPager = findViewById(R.id.slideViewPager);
        mDotLayout = findViewById(R.id.indicator_layout_two);

        viewPagerAdaptor = new ViewPagerAdaptor(this, mSlideViewPager);
        mSlideViewPager.setAdapter(viewPagerAdaptor);
        mSlideViewPager.setAdapter(viewPagerAdaptor);

        addDotsIndicator(0); // Προσθήκη αρχικών dots

        mSlideViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                addDotsIndicator(position); // Καλείται όταν αλλάζει το slide
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    // Προσθήκη dots δυναμικά
    public void addDotsIndicator(int position) {
        dots = new TextView[viewPagerAdaptor.getCount()];
        mDotLayout.removeAllViews();

        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(40);
            dots[i].setTextColor(getResources().getColor(R.color.BCCDBC, getTheme())); // Χρώμα ανενεργών dots
            mDotLayout.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[position].setTextColor(getResources().getColor(R.color.A3B18A, getTheme())); // Χρώμα ενεργού dot
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Check if ActionBar is not null before invoking methods on it
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.show();
        }
    }
}
