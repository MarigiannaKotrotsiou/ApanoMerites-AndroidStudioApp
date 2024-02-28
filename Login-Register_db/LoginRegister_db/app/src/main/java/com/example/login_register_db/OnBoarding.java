package com.example.login_register_db;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OnBoarding extends AppCompatActivity {

    ViewPager SlideViewPager;
    LinearLayout DotLayout;
    Button skipbtn;
    ImageView nextbtn;
    TextView[] dots;
    ViewPager3Adaptor viewPager3Adaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);

        skipbtn = findViewById(R.id.btnskipob);
        nextbtn = findViewById(R.id.btnnextob);


        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getitem(0) < 2) {
                    SlideViewPager.setCurrentItem(getitem(1), true);
                } else {
                    Intent i = new Intent(OnBoarding.this, Recycler.class);
                    startActivity(i);
                    finish();
                }
            }
        });

        skipbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(OnBoarding.this, Recycler.class);
                startActivity(i);
                finish();

            }
        });

        SlideViewPager = (ViewPager) findViewById(R.id.slideViewPagerOB);
        DotLayout = (LinearLayout) findViewById(R.id.indicatorob);
        viewPager3Adaptor = new ViewPager3Adaptor(this);
        SlideViewPager.setAdapter(viewPager3Adaptor);
        setUpindicator(0);
        SlideViewPager.addOnPageChangeListener(viewListener);
    }

    public void setUpindicator(int position) {

        dots = new TextView[3];
        DotLayout.removeAllViews();

        for (int i = 0; i < dots.length; i++) {

            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.light_green, getApplicationContext().getTheme()));
            DotLayout.addView(dots[i]);
        }
        dots[position].setTextColor(getResources().getColor(R.color.dark_green, getApplicationContext().getTheme()));
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            // Your implementation here
        }

        @Override
        public void onPageSelected(int position) {
            // This method is required to be implemented
            setUpindicator(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            // Your implementation here
        }
    };

    private int getitem(int i) {
        return SlideViewPager.getCurrentItem() + i;
    }

}