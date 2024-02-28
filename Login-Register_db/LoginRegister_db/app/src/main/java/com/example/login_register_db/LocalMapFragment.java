package com.example.login_register_db;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class LocalMapFragment extends Fragment implements OnMapReadyCallback {

    private final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;
    private final int MY_PERMISSIONS_REQUEST_LOCATION = 1;
    private GoogleMap googleMap;
    private ArrayList<MapItem> mapItems;
    DialogInterface dialog;
    private MapItem currentTask;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_local_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.maplocal);
        mapFragment.getMapAsync(this);

        setupMapItems();
    }

    private void showInfoDialog() {
        // Create and show your custom dialog or pop-up with additional information
        // You can use AlertDialog, BottomSheetDialog, or any other UI element
        // Example:
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Additional Information");
        builder.setMessage("This is additional information about the map.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle OK button click if needed
            }
        });
        builder.show();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;

        // Move the camera to the specified location
        LatLng location = new LatLng(37.4934, 24.9160);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 14));

        // Enable My Location if permission is granted
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
        } else {
            // Request location permission
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
        }

        // Add markers for map items
        addMarkers();

        // Set a marker click listener to show the custom dialog
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                // Assuming you have a method to get MapItem for a given LatLng
                MapItem selectedMapItem = findMapItemByLocation(marker.getPosition());

                // Show the custom dialog with MapItem information
                showCustomDialog(selectedMapItem);

                // Return true to indicate that the click event has been consumed
                return true;
            }
        });

    }

    // Add markers for map items
    private void addMarkers() {
        if (googleMap != null && mapItems != null) {
            for (MapItem mapItem : mapItems) {
                LatLng location = new LatLng(mapItem.getLatitude(), mapItem.getLongitude());

                // Get the pin resource ID
                int pinResourceID = mapItem.getPinResourceID();

// Load the pin bitmap from the resource
                Bitmap pinBitmap = BitmapFactory.decodeResource(getResources(), pinResourceID);

// Define the desired width and height for the pin (e.g., 50x50 pixels)
                int desiredWidth = 70;
                int desiredHeight = 70;

// Resize the pin bitmap
                Bitmap resizedPinBitmap = Bitmap.createScaledBitmap(pinBitmap, desiredWidth, desiredHeight, false);

// Create a BitmapDescriptor from the resized pin bitmap
                BitmapDescriptor markerIcon = BitmapDescriptorFactory.fromBitmap(resizedPinBitmap);

// Use the custom marker icon
                googleMap.addMarker(new MarkerOptions()
                        .position(location)
                        .title(mapItem.getTitle())
                        .snippet(mapItem.getInformation())
                        .icon(markerIcon));

            }
        }
    }

    private MapItem findMapItemByLocation(LatLng location) {
        for (MapItem mapItem : mapItems) {
            if (mapItem.getLatitude() == location.latitude && mapItem.getLongitude() == location.longitude) {
                return mapItem;
            }
        }
        return null;
    }

    private void setupMapItems() {
        mapItems = createMapItems();
    }

    private ArrayList<MapItem> createMapItems() {
        ArrayList<MapItem> mapItems = new ArrayList<>();

        // Use Google Maps marker resources and coordinates
        mapItems.add(new MapItem(
                getString(R.string.task1title),
                getString(R.string.mytaskrowdesc1),
                R.drawable.ic_bee, // Use the default marker color
                "350",
                "1:30 ώρα",
                "2:30 ώρες",
                "Μέτρια",
                "Σακίδιο ή τσάντα",
                37.492689,
                24.914600,
                R.drawable.pindarkgreen));

        mapItems.add(new MapItem(
                getString(R.string.task2title),
                getString(R.string.mytaskrowdesc2),
                R.drawable.ic_plant, // Use the default marker color
                "350",
                "1:30 ώρα",
                "2:30 ώρες",
                "Μέτρια",
                "Σακίδιο ή τσάντα",
                37.488483,
                24.901060,
                R.drawable.pinlightgreen));


        // Add more map items as needed

        Log.d("MapItems", "Number of MapItems: " + mapItems.size()); // Add this line to log the size

        return mapItems;
    }

    private void showCustomDialog(MapItem mapItem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View customLayout = getLayoutInflater().inflate(R.layout.local_dialog_task, null);
        builder.setView(customLayout);

        // Find and handle the views inside the customLayout
        TextView txtDialogTitle = customLayout.findViewById(R.id.txttitlerowmytask2);
        TextView txtDialogInformation = customLayout.findViewById(R.id.txtdecrrowmytask2);
        ImageView imgIcon = customLayout.findViewById(R.id.mytaskimg2);

        // Remove the declaration of the local dialog variable
        // AlertDialog dialog = builder.create();

        if (mapItem != null) {
            // Set values from the MapItem to the views
            txtDialogTitle.setText(mapItem.getTitle());
            txtDialogInformation.setText(mapItem.getInformation());

            imgIcon.setImageResource(mapItem.getIconID());

            // Update the current task
            currentTask = mapItem;

            // Depending on the selected pin, you can customize the dialog content here
            if (mapItem.getIconID() == R.drawable.ic_bee) {
                // Customize content for the bee pin
                // You can set different text or images based on your requirements
            } else if (mapItem.getIconID() == R.drawable.ic_plant) {
                // Customize content for the plant pin
                // You can set different text or images based on your requirements
            }
        } else {
            // Set default or placeholder values when no MapItem is provided
            txtDialogTitle.setText("Default Title");
            txtDialogInformation.setText("Default Information");
        }

        // Move the declaration and initialization of dialog here
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void updateButtonState(Button button) {
        // Check if the current task is the first task (ic_bee)
        if (currentTask != null && currentTask.getIconID() == R.drawable.ic_bee) {
            // If it's ic_bee, enable the "Start Task" button
            button.setEnabled(true);
            button.setBackgroundResource(R.drawable.button_background); // Set the active color
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle the click for ic_bee (Start Task)
                    Log.d("LMAO", "Start Task Button Clicked for ic_bee");

                    // Check if you're on ic_bee task
                    if (currentTask != null && currentTask.getIconID() == R.drawable.ic_bee) {
                        // Declare the Intent here
//                        Intent intent = new Intent(getActivity(), YourActivity.class); // Replace YourActivity with the actual class
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(intent);
                    }

                    dialog.dismiss();
                }
            });
        } else {
            // If it's not ic_bee, disable the "Start Task" button
            button.setEnabled(false);
            Toast.makeText(requireContext(), "Η δραστηριότητα δεν είναι διαθέσιμη", Toast.LENGTH_SHORT).show();
        }
    }


    private void requestPermissionsIfNecessary(String[] permissions) {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
