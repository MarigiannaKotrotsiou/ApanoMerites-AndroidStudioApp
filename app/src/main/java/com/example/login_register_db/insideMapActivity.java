package com.example.login_register_db;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import java.util.Objects;

public class insideMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;
    private final int MY_PERMISSIONS_REQUEST_LOCATION = 1;
    private GoogleMap googleMap;
    Toolbar toolbar;

    ImageView imgbck;

    private ArrayList<MapItem> mapItems;
    DialogInterface dialog;
    private MapItem currentTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside_map);


        toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);


        imgbck = findViewById(R.id.imgback1);
        imgbck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(insideMapActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        setupMapItems();


        ImageView infoIcon = findViewById(R.id.infoIcon);
        infoIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an instance of DialogInformationMap
                DialogInformationMap dialog = new DialogInformationMap(insideMapActivity.this);
                dialog.show();
            }
        });


    }

    private void showInfoDialog() {
        // Create and show your custom dialog or pop-up with additional information
        // You can use AlertDialog, BottomSheetDialog, or any other UI element
        // Example:
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
        } else {
            // Request location permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
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
                getString(R.string.task1description),
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
                getString(R.string.task2description),
                R.drawable.ic_plant, // Use the default marker color
                "350",
                "1:30 ώρα",
                "2:30 ώρες",
                "Μέτρια",
                "Σακίδιο ή τσάντα",
                37.492297,
                24.912368,
                R.drawable.pinlightgreen));

        mapItems.add(new MapItem(
                getString(R.string.task2title),
                getString(R.string.task2description),
                R.drawable.ic_plant, // Use the default marker color
                "350",
                "1:30 ώρα",
                "2:30 ώρες",
                "Μέτρια",
                "Σακίδιο ή τσάντα",
                37.491838,
                24.909106,
                R.drawable.pinlightgreen));


        mapItems.add(new MapItem(
                getString(R.string.task2title),
                getString(R.string.task2description),
                R.drawable.ic_plant, // Use the default marker color
                "350",
                "1:30 ώρα",
                "2:30 ώρες",
                "Μέτρια",
                "Σακίδιο ή τσάντα",
                37.490952,
                24.905266,
                R.drawable.pinlightgreen));

        mapItems.add(new MapItem(
                getString(R.string.task2title),
                getString(R.string.task2description),
                R.drawable.ic_plant, // Use the default marker color
                "350",
                "1:30 ώρα",
                "2:30 ώρες",
                "Μέτρια",
                "Σακίδιο ή τσάντα",
                37.489216,
                24.901596,
                R.drawable.pinlightgreen));

        mapItems.add(new MapItem(
                getString(R.string.task2title),
                getString(R.string.task2description),
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View customLayout = getLayoutInflater().inflate(R.layout.dialog_layout, null);
        builder.setView(customLayout);

        // Find and handle the views inside the customLayout
        TextView txtDialogTitle = customLayout.findViewById(R.id.txttitledialog);
        TextView txtDialogInformation = customLayout.findViewById(R.id.txtinformation);
        TextView txtDialogTimeNeeded = customLayout.findViewById(R.id.time1txt);
        TextView txtDialogDifficulty = customLayout.findViewById(R.id.txtdiff);
        TextView txtDialogTimeBack = customLayout.findViewById(R.id.esttimereturntxt);
        TextView txtDialogPoints = customLayout.findViewById(R.id.txtpoints2);
        TextView txtDialogEquipment = customLayout.findViewById(R.id.txteq2);
        ImageView imgEmblem = customLayout.findViewById(R.id.imgemblem);
        Button button = customLayout.findViewById(R.id.btnstarttask);

        // Remove the declaration of the local dialog variable
        // AlertDialog dialog = builder.create();

        if (mapItem != null) {
            // Set values from the MapItem to the views
            txtDialogTitle.setText(mapItem.getTitle());
            txtDialogInformation.setText(mapItem.getInformation());
            txtDialogTimeNeeded.setText(mapItem.getFinishingtime());
            txtDialogDifficulty.setText(mapItem.getDifficulty());
            txtDialogTimeBack.setText(mapItem.getTimeback());
            txtDialogPoints.setText(mapItem.getPoints());
            txtDialogEquipment.setText(mapItem.getEquipment());
            imgEmblem.setImageResource(mapItem.getIconID());

            // Update the current task
            currentTask = mapItem;

            // Enable or disable the button based on the current task
            updateButtonState(button);
        } else {
            // Set default or placeholder values when no MapItem is provided
            txtDialogTitle.setText("Default Title");
            txtDialogInformation.setText("Default Information");
            // Set other default values as needed
            button.setEnabled(false);
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
                        // Intent to beforeTaskBottomSheet activity
                        Intent intent = new Intent(insideMapActivity.this, beforeTaskBottomSheet.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }

                    dialog.dismiss();
                }
            });
        } else {
            // If it's not ic_bee, disable the "Start Task" button
            button.setEnabled(false);
            Toast.makeText(insideMapActivity.this, "Η δραστηριότητα δεν είναι διαθέσιμη", Toast.LENGTH_SHORT).show();
            button.setBackgroundResource(R.drawable.inactive_button_background); // Set the inactive color
        }

    }


    private void requestPermissionsIfNecessary(String[] permissions) {
        // ... (unchanged)
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // ... (unchanged)
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onResume() {
        // ... (unchanged)
        super.onResume();
    }

    @Override
    protected void onPause() {
        // ... (unchanged)
        super.onPause();
    }
}
