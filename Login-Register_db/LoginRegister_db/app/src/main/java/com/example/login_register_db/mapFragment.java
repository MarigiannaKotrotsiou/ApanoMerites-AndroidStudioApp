package com.example.login_register_db;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.view.MotionEvent;
import androidx.fragment.app.Fragment;

public class mapFragment extends Fragment {

    ImageView kampos;
    ImageView xalandriani;
    ImageView papouri;

    ImageView selectedImageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        kampos = view.findViewById(R.id.img_kampos);
        xalandriani = view.findViewById(R.id.img_xalandriani);
        papouri = view.findViewById(R.id.img_papouri);

        // Set the initial alpha
        setInitialAlpha(kampos);
        setInitialAlpha(xalandriani);
        setInitialAlpha(papouri);

        // Set a TouchListener to handle the touch events
        kampos.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                handleTouchEvent(kampos, event);
                return true;
            }
        });

        xalandriani.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                handleTouchEvent(xalandriani, event);
                return true;
            }
        });

        papouri.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                handleTouchEvent(papouri, event);
                return true;
            }
        });

        return view;
    }

    // Ρυθμίζει την αρχική διαφάνεια για μια εικόνα
    private void setInitialAlpha(ImageView imageView) {
        imageView.setAlpha(0f);
    }

    // Χειρίζεται το συμβάν αφής για μια εικόνα
    private void handleTouchEvent(ImageView imageView, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // Όταν πατιέται, ορίζετε τη διαφάνεια όλων των εικόνων σε 0f
                setInitialAlpha(kampos);
                setInitialAlpha(xalandriani);
                setInitialAlpha(papouri);

                // Αυξάνει τη διαφάνεια της επιλεγμένης εικόνας
                imageView.setAlpha(1.0f);

                // Ορίζει την τρέχουσα επιλεγμένη εικόνα
                selectedImageView = imageView;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                // Όταν αφήνετε τον χώρο, διατηρεί τη διαφάνεια της επιλεγμένης εικόνας
                imageView.setAlpha(1.0f);

                // Καλέστε τον OnClickListener για να εκτελεστούν ενέργειες κατά το πάτημα
                if (imageView == selectedImageView) {
                    commonClickListener.onClick(imageView);
                }
                break;
        }
    }

    // Κοινός OnClickListener
    View.OnClickListener commonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            BottomSheetFragment bottomSheetFragment = BottomSheetFragment.newInstance(view.getId());
            bottomSheetFragment.show(getParentFragmentManager(), bottomSheetFragment.getTag());
        }
    };

}