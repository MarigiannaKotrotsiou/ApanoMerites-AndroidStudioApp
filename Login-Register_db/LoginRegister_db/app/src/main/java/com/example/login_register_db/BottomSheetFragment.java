package com.example.login_register_db;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomSheetFragment extends BottomSheetDialogFragment {

    private static final String ARG_IMAGE_ID = "image_id";


    private void showToast(String s) {
        Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show();
    }

    public static BottomSheetFragment newInstance(int imageId) {
        BottomSheetFragment fragment = new BottomSheetFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_IMAGE_ID, imageId);
        fragment.setArguments(args);

        return fragment;
    }




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom_sheet, container, false);

        // Your additional customization for layout
        ConstraintLayout bottomSheet = view.findViewById(R.id.bottomSheetLayout);



        return view;
    }







    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            int imageId = args.getInt(ARG_IMAGE_ID);

            TextView titleTextView = view.findViewById(R.id.rbtxttitle);
            TextView infoTextView = view.findViewById(R.id.rbtxtinfo);

            // Initialize all buttons
            Button kamposButton = view.findViewById(R.id.rbbtnview);
            Button xalandrianiButton = view.findViewById(R.id.rbbtnview);
            Button papouriButton = view.findViewById(R.id.rbbtnview);

            // Set default states (inactive)
            kamposButton.setEnabled(false);
            xalandrianiButton.setEnabled(false);
            papouriButton.setEnabled(false);

            switch (imageId) {
                case R.id.img_kampos:
                    titleTextView.setText(getString(R.string.slide_heading_1));
                    infoTextView.setText(getString(R.string.Kamposbrief));

                    // Make the Kampos button active
                    kamposButton.setEnabled(true);
                    kamposButton.setEnabled(true);
                    kamposButton.setBackgroundResource(R.drawable.button_background);

                    break;
                case R.id.img_xalandriani:
                    titleTextView.setText(getString(R.string.slide_heading_2));
                    infoTextView.setText(getString(R.string.Xalandrianibrief));
                    // Make the Xalandriani button active
                    xalandrianiButton.setEnabled(false);
                    xalandrianiButton.setBackgroundResource(R.drawable.inactive_button_background);
                    showToast("Μη διαθέσιμη περιοχή: Χαλανδριανή");

                    break;
                case R.id.img_papouri:
                    titleTextView.setText(getString(R.string.slide_heading_3));
                    infoTextView.setText(getString(R.string.Papouribrief));
                    papouriButton.setEnabled(false);
                    papouriButton.setBackgroundResource(R.drawable.inactive_button_background);
                    showToast("Μη διαθέσιμη περιοχή: Παπούρι");
                    break;
                default:
                    titleTextView.setText("Default Title");
                    infoTextView.setText("Default Information");
            }

            // Set click listeners for all buttons
            kamposButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), SliderActivity.class);
                    intent.putExtra("imageId", R.id.img_kampos);
                    startActivity(intent);
                    dismiss();
                }
            });

            xalandrianiButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), SliderActivity.class);
                    intent.putExtra("imageId", R.id.img_xalandriani);

                    startActivity(intent);
                    dismiss();
                }
            });

            papouriButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), SliderActivity.class);
                    intent.putExtra("imageId", R.id.img_papouri);
                    startActivity(intent);
                    dismiss();
                }
            });

        }
    }


}