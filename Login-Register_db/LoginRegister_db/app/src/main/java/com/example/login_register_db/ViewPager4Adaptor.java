package com.example.login_register_db;

import android.content.Context;
import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class ViewPager4Adaptor extends PagerAdapter {
    Context context;

    int images[] = {
            R.drawable.visitorob1,
            R.drawable.visitorob2,
            R.drawable.visitorob3

    };

    int headings[] = {
            R.string.instrtitle1,
            R.string.instrtitle2,
            R.string.instrtitle3

    };

    int descriptions[] = {
            R.string.instrdesc1,
            R.string.instrdesc2,
            R.string.instrdesc3
    };

    public ViewPager4Adaptor(Context context) {
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
        View view = layoutInflater.inflate(R.layout.instructions_sliderlayout, container, false);

        ImageView slidetitleimage = (ImageView) view.findViewById(R.id.instrimg);
        TextView slideHeading = (TextView) view.findViewById(R.id.txtTitleinstr);
        TextView slideDescription = (TextView) view.findViewById(R.id.txtDescinstr);

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


