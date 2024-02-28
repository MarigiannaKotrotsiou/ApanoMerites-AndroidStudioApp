package com.example.login_register_db;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

public class SliderActivity2 extends AppCompatActivity {

    ViewPager2 mmSlideViewPager;
    LinearLayout mmDotLayout;

    TextView nextbtn;

    private ViewPager2Adaptor viewPagerAdaptor;
    private TextView[] dots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide the action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        setContentView(R.layout.item_slider);

        nextbtn = findViewById(R.id.btnnetnfc);
        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getitem(0) < 3) {
                    mmSlideViewPager.setCurrentItem(getitem(1), true);
                } else {
                    Intent i = new Intent(SliderActivity2.this, Nfc.class);
                    startActivity(i);
                    finish();
                }
            }
        });

        mmSlideViewPager = findViewById(R.id.viewPagertwo);
        mmDotLayout = findViewById(R.id.indicator_layout_two);

        viewPagerAdaptor = new ViewPager2Adaptor(this, mmSlideViewPager);
        mmSlideViewPager.setAdapter(viewPagerAdaptor);

        addDotsIndicator(0);

        mmSlideViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                addDotsIndicator(position);
            }
        });
    }

    private int getitem(int i) {
        return mmSlideViewPager.getCurrentItem() + i;
    }

    public void addDotsIndicator(final int position) {
        dots = new TextView[viewPagerAdaptor.getItemCount()];
        mmDotLayout.removeAllViews();

        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(20);  // Adjusted text size
            dots[i].setTextColor(getResources().getColor(R.color.BCCDBC, getTheme()));
            mmDotLayout.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[position].setTextColor(getResources().getColor(R.color.A3B18A, getTheme()));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Check if ActionBar is not null before invoking methods on it
        if (getSupportActionBar() != null) {
            getSupportActionBar().show();
        }
    }
}
