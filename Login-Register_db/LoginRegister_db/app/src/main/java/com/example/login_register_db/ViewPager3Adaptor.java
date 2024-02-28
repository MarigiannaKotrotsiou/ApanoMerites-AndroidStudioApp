package com.example.login_register_db;


import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;


public class ViewPager3Adaptor extends PagerAdapter {

    Context context;

    int images[] = {
            R.drawable.onboarding2,
            R.drawable.syrosicon,
            R.drawable.onboarding1

    };

    int headings[] = {
            R.string.obtitle1,
            R.string.obtitle2,
            R.string.obtitle3

    };

    int descriptions[] = {
            R.string.obdesc1,
            R.string.obdesc2,
            R.string.obdesc3
    };

    public ViewPager3Adaptor(Context context) {
        this.context = context;
    }

    public int getCount() {
        return headings.length;
    }

    public boolean isViewFromObject(View view, Object object) {
        return view == (LinearLayout) object;
    }

    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.onboarding_sliderlayout, container, false);

        ImageView slidetitleimage = (ImageView) view.findViewById(R.id.obimg);
        TextView slideHeading = (TextView) view.findViewById(R.id.txtTitleOb);
        TextView slideDescription = (TextView) view.findViewById(R.id.txtDescOb);

        slidetitleimage.setImageResource(images[position]);
        slideHeading.setText(headings[position]);
        slideDescription.setText(descriptions[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }
}
