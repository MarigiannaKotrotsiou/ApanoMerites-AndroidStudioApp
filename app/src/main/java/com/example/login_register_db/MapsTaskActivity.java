package com.example.login_register_db;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MapsTaskActivity extends AppCompatActivity implements OnMapReadyCallback, GeoQueryEventListener {
    // Boolean flags to track whether the dialog has been shown for each circle
    // Boolean για να ελέγξει αν ο διάλογος έχει εμφανιστεί
    private boolean customDialog1Shown = false;
    private boolean customDialog2Shown = false;
    private boolean customDialog3Shown = false;
    private boolean customDialog4Shown = false;
    private boolean customDialog5Shown = false;
    private boolean customDialog6Shown = false;
    private boolean customDialog7Shown = false;
    private boolean customDialog8Shown = false;
    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;
    private GeoFire geoFire;
    private DatabaseReference myLocationRef;
    private Marker currentUser;
    private AlertDialog dialog;
    private Map<LatLng, Boolean> enteredLocations = new HashMap<>();

    private static final int FINE_LOCATION_ACCESS_REQUEST_CODE = 1001;

    // Marker dialogs for each location
    private Map<LatLng, Dialog> markerDialogs = new HashMap<>();
    private Polyline routePolyline = null; // Initialize the polyline


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_task);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        settingGeoFire();
        buildLocationRequest();
        buildLocationCallback();
        startLocationUpdates();

// Add the following code for ToggleButton setup
        Switch toggleSwitch = findViewById(R.id.toggleButton);

        toggleSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // User turned on the toggle, show route
                showRoute();
            } else {
                // User turned off the toggle, hide route
                // You can clear or hide the Polyline here
                if (routePolyline != null) {
                    routePolyline.remove();
                }
            }
        });
    }

    private void showRoute() {
        // Your existing code for showing the route
        PolylineOptions polylineOptions = new PolylineOptions()
                .add(new LatLng(37.493056,24.916389), new LatLng(37.493056,24.916389),
                        new LatLng(37.4925,	24.914444), new LatLng(37.491944,	24.908611),
                        new LatLng(37.49,	24.905833)) // Add more points if needed
                .width(5)
                .color(R.color.army_green);

        routePolyline = mMap.addPolyline(polylineOptions);
    }
    private void settingGeoFire() {
        myLocationRef = FirebaseDatabase.getInstance().getReference("MyLocation");
        geoFire = new GeoFire(myLocationRef);
    }

    private void buildLocationCallback() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                if (mMap != null) {
                    geoFire.setLocation("YOU", new GeoLocation(locationResult.getLastLocation().getLatitude(),
                            locationResult.getLastLocation().getLongitude()), new GeoFire.CompletionListener() {
                        @Override
                        public void onComplete(String key, DatabaseError error) {
                            if (currentUser != null) currentUser.remove();
                            currentUser = mMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(locationResult.getLastLocation().getLatitude(),
                                            locationResult.getLastLocation().getLongitude()))
                                    .title("YOU"));
                            // After adding a marker, move camera
                            mMap.animateCamera(CameraUpdateFactory
                                    .newLatLngZoom(currentUser.getPosition(), 12.0f));
                        }
                    });
                }
            }
        };
    }

    private void buildLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setSmallestDisplacement(10f);
    }

    private void startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
        } else {
            requestFineLocationPermission();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        LatLng myLocation = new LatLng(37.493056,24.916389);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 14));
        enableUserLocation();

        // Assuming you have circles defined like this
        CircleOptions circle1 = new CircleOptions().center(new LatLng(37.493056,24.916389)).radius(2);
        CircleOptions circle2 = new CircleOptions().center(new LatLng(37.4925,	24.914444)).radius(2);
        CircleOptions circle3 = new CircleOptions().center(new LatLng(37.491944,	24.908611)).radius(2);
        CircleOptions circle4 = new CircleOptions().center(new LatLng(37.49,	24.905833)).radius(2);
        CircleOptions circle5 = new CircleOptions().center(new LatLng(37.493056,24.916389)).radius(15);
        CircleOptions circle6 = new CircleOptions().center(new LatLng(37.4925,	24.914444)).radius(15);
        CircleOptions circle7 = new CircleOptions().center(new LatLng(37.491944,	24.908611)).radius(15);
        CircleOptions circle8 = new CircleOptions().center(new LatLng(37.49,	24.905833)).radius(15);




        // Add the circles to the map with custom style
        mMap.addCircle(circle1.strokeColor(Color.argb(0, 0, 0, 0)).fillColor(Color.argb(0, 136, 177, 138)));
        mMap.addCircle(circle2.strokeColor(Color.argb(0, 0, 0, 0)).fillColor(Color.argb(0, 136, 177, 138)));
        mMap.addCircle(circle3.strokeColor(Color.argb(0, 0, 0, 0)).fillColor(Color.argb(0, 136, 177, 138)));
        mMap.addCircle(circle4.strokeColor(Color.argb(0, 0, 0, 0)).fillColor(Color.argb(0, 136, 177, 138)));
        mMap.addCircle(circle5.strokeColor(Color.argb(0, 0, 0, 0)).fillColor(Color.argb(0, 136, 177, 138)));
        mMap.addCircle(circle6.strokeColor(Color.argb(0, 0, 0, 0)).fillColor(Color.argb(0, 136, 177, 138)));
        mMap.addCircle(circle7.strokeColor(Color.argb(0, 0, 0, 0)).fillColor(Color.argb(0, 136, 177, 138)));
        mMap.addCircle(circle8.strokeColor(Color.argb(0, 0, 0, 0)).fillColor(Color.argb(0, 136, 177, 138)));
        LatLng marker1 = new LatLng(37.493056,24.916389);
        LatLng marker2 = new LatLng(37.4925,	24.914444);
        LatLng marker3 = new LatLng(37.491944,	24.908611);
        LatLng marker4 = new LatLng(37.49,	24.905833);

        // Create BitmapDescriptor for custom drawables
        BitmapDescriptor icon1 = BitmapDescriptorFactory.fromResource(R.drawable.ic_location_one);
        BitmapDescriptor icon2 = BitmapDescriptorFactory.fromResource(R.drawable.ic_location_two);
        BitmapDescriptor icon3 = BitmapDescriptorFactory.fromResource(R.drawable.ic_location_three);
        BitmapDescriptor icon4 = BitmapDescriptorFactory.fromResource(R.drawable.ic_location_four);

        // Add markers with custom icons
        mMap.addMarker(new MarkerOptions().position(marker1).title("Marker 1").icon(icon1));
        mMap.addMarker(new MarkerOptions().position(marker2).title("Marker 2").icon(icon2));
        mMap.addMarker(new MarkerOptions().position(marker3).title("Marker 3").icon(icon3));
        mMap.addMarker(new MarkerOptions().position(marker4).title("Marker 4").icon(icon4));




//Με αυτο απο κατω , λειτουργησε στο να μην εμφανιζεται πολλες φορες , to dialog
        // Check if the user is inside a circle and display the corresponding dialog
        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());

                if (isInsideCircle(currentLocation, circle1) && !customDialog1Shown) {
                    // Display dialog for circle 1
                    showCustomDialog1();
                    customDialog1Shown = true; // Set the flag to true
                } else if (!isInsideCircle(currentLocation, circle1)) {
                    // Reset the flag when the user exits circle 1
                    customDialog1Shown = false;
                }

                if (isInsideCircle(currentLocation, circle2) && !customDialog2Shown) {
                    // Display dialog for circle 2
                    showCustomDialog2();
                    customDialog2Shown = true; // Set the flag to true
                } else if (!isInsideCircle(currentLocation, circle2)) {
                    // Reset the flag when the user exits circle 2
                    customDialog2Shown = false;
                }

                if (isInsideCircle(currentLocation, circle3) && !customDialog3Shown) {
                    // Display dialog for circle 3
                    showCustomDialog3();
                    customDialog3Shown = true; // Set the flag to true
                } else if (!isInsideCircle(currentLocation, circle3)) {
                    // Reset the flag when the user exits circle 3
                    customDialog3Shown = false;
                }

                if (isInsideCircle(currentLocation, circle4) && !customDialog4Shown) {
                    // Display dialog for circle 3
                    showCustomDialog4();
                    customDialog4Shown = true; // Set the flag to true
                } else if (!isInsideCircle(currentLocation, circle4)) {
                    // Reset the flag when the user exits circle 3
                    customDialog4Shown = false;
                }

                if (isInsideCircle(currentLocation, circle5) && !customDialog5Shown) {
                    // Display dialog for circle 3
                    showCustomDialog5();
                    customDialog5Shown = true; // Set the flag to true
                } else if (!isInsideCircle(currentLocation, circle5)) {
                    // Reset the flag when the user exits circle 3
                    customDialog5Shown = false;
                }
                if (isInsideCircle(currentLocation, circle6) && !customDialog6Shown) {
                    // Display dialog for circle 3
                    showCustomDialog6();
                    customDialog6Shown = true; // Set the flag to true
                } else if (!isInsideCircle(currentLocation, circle6)) {
                    // Reset the flag when the user exits circle 3
                    customDialog6Shown = false;
                }

                if (isInsideCircle(currentLocation, circle7) && !customDialog7Shown) {
                    // Display dialog for circle 3
                    showCustomDialog7();
                    customDialog7Shown = true; // Set the flag to true
                } else if (!isInsideCircle(currentLocation, circle7)) {
                    // Reset the flag when the user exits circle 3
                    customDialog7Shown = false;
                }

                if (isInsideCircle(currentLocation, circle8) && !customDialog8Shown) {
                    // Display dialog for circle 3
                    showCustomDialog8();
                    customDialog8Shown = true; // Set the flag to true
                } else if (!isInsideCircle(currentLocation, circle8)) {
                    // Reset the flag when the user exits circle 3
                    customDialog8Shown = false;
                }
            }

        });
    }

    // Function to check if a location is inside a circle
    private boolean isInsideCircle(LatLng point, CircleOptions circle) {
        float[] distance = new float[2];
        Location.distanceBetween(point.latitude, point.longitude, circle.getCenter().latitude, circle.getCenter().longitude, distance);
        return distance[0] < circle.getRadius();
    }

    // Function to show the custom dialog for circle 1
    private void showCustomDialog1() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.fragment_custom_task_dialog1, null);
        builder.setView(view);

        Button btn1 = view.findViewById(R.id.btn4);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Use the correct context (MapsTaskActivity.this) and class name in the Intent
                Intent intent = new Intent(MapsTaskActivity.this, playActivity.class);
                startActivity(intent);
                // Σημείωσε ότι ο διάλογος έχει εμφανιστεί
                customDialog1Shown = true;
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }



    private void showCustomDialog5() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.fragment_custom_task_dialog5, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();
        // Retrieve ImageView from the inflated view
        ImageView xButton = view.findViewById(R.id.xbtn5);
        // Set OnClickListener to close the dialog when ImageView is clicked
        xButton.setOnClickListener(v -> dialog.dismiss());
    }

    // Function to show the custom dialog for circle 2
    private void showCustomDialog2() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.fragment_custom_task_dialog2, null);
        builder.setView(view);
        Button btn2 = view.findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the correct activity (SliderActivity)
                Intent intent = new Intent(MapsTaskActivity.this, SliderActivity2.class);
                startActivity(intent);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    // Function to show the custom dialog for circle 3
    private void showCustomDialog3() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.fragment_custom_task_dialog3, null);
        builder.setView(view);
        Button btn3 = view.findViewById(R.id.btn3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Use the correct context (MapsTaskActivity.this) and class name in the Intent
                Intent intent = new Intent(MapsTaskActivity.this, playActivity2.class);
                startActivity(intent);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();


    }

    private void showCustomDialog4() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.fragment_custom_task_dialog4, null);
        builder.setView(view);
        Button btn4 = view.findViewById(R.id.btn4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Use the correct context (MapsTaskActivity.this) and class name in the Intent
                Intent intent = new Intent(MapsTaskActivity.this, TaskComplete.class);
                startActivity(intent);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();


    }

    private void showCustomDialog6() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.fragment_custom_task_dialog5, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();
        // Retrieve ImageView from the inflated view
        ImageView xButton = view.findViewById(R.id.xbtn5);
        // Set OnClickListener to close the dialog when ImageView is clicked
        // Set OnClickListener to close the dialog when ImageView is clicked
        xButton.setOnClickListener(v -> dialog.dismiss());
    }

    private void showCustomDialog7() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.fragment_custom_task_dialog5, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();
        // Retrieve ImageView from the inflated view
        ImageView xButton = view.findViewById(R.id.xbtn5);
        // Set OnClickListener to close the dialog when ImageView is clicked
        // Set OnClickListener to close the dialog when ImageView is clicked
        xButton.setOnClickListener(v -> dialog.dismiss());
    }

    private void showCustomDialog8() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.fragment_custom_task_dialog5, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();
        ImageView xButton = view.findViewById(R.id.xbtn5);
        // Set OnClickListener to close the dialog when ImageView is clicked
        // Set OnClickListener to close the dialog when ImageView is clicked
        xButton.setOnClickListener(v -> dialog.dismiss());
    }




    // Function to create a custom dialog (replace with your own implementation)
    private Dialog createCustomDialog(LatLng location) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Geofence Entry Dialog");
        builder.setMessage("Entered geofence at location: " + location.toString());

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        return builder.create();
    }

    private void enableUserLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            // Ask for permission
            requestFineLocationPermission();
        }
    }

    private void requestFineLocationPermission() {
        // Ask for permission
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_ACCESS_REQUEST_CODE);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_ACCESS_REQUEST_CODE);
        }
    }

    @Override
    protected void onStop() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
        super.onStop();
    }

    // Ανανεωμένη onKeyEntered
    public void onKeyEntered(String key, GeoLocation location) {
        LatLng enteredLocation = new LatLng(location.latitude, location.longitude);

        // Check if the dialog has already been shown for this location
        if (!isDialogShownForLocation(enteredLocation) && !hasEnteredLocation(enteredLocation)) {
            showNotificationDialog(enteredLocation);
            markLocationAsEntered(enteredLocation);
        }
    }

    // Μέθοδος που ελέγχει αν η τοποθεσία έχει ήδη εισαχθεί
    private boolean hasEnteredLocation(LatLng location) {
        return enteredLocations.containsKey(location) && enteredLocations.get(location);
    }

    // Μέθοδος που επισημαίνει την τοποθεσία ως εισαγμένη
    private void markLocationAsEntered(LatLng location) {
        enteredLocations.put(location, true);
    }
    private void showNotificationDialog(LatLng enteredLocation) {
        // Check if the dialog has already been shown for this location
        if (!isDialogShownForLocation(enteredLocation)) {
            Dialog customDialog = createCustomDialog(enteredLocation);

            // Ελέγξτε αν ο διάλογος είναι ήδη ορατός
            if (!customDialog.isShowing()) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        customDialog.show();
                    }
                });
            }
        }
    }

    private boolean isDialogShownForLocation(LatLng location) {
        // Check if the dialog has already been shown for this location
        return markerDialogs.containsKey(location) && markerDialogs.get(location).isShowing();
    }


    @Override
    public void onKeyExited(String key) {
        sendNotification("", String.format("%s exited the geofence", key));
    }

    @Override
    public void onKeyMoved(String key, GeoLocation location) {
        sendNotification("", String.format("%s moved within the geofence", key));
    }

    @Override
    public void onGeoQueryReady() {
        // Handle when the initial geo-query is completed
    }

    @Override
    public void onGeoQueryError(DatabaseError error) {
        Toast.makeText(this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    private void sendNotification(String title, String content) {
        Toast.makeText(this, "" + content, Toast.LENGTH_SHORT).show();
        String NOTIFICATION_CHANNEL_ID = "multiple_location";
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notification",
                    NotificationManager.IMPORTANCE_DEFAULT);
            //config
            notificationChannel.setDescription("Channel description");
            notificationChannel.enableVibration(true);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0, 100, 500, 1000});
            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        builder.setContentTitle(title)
                .setContentText(content)
                .setAutoCancel(false)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        Notification notification = builder.build();
        notificationManager.notify(new Random().nextInt(), notification);
    }

    private void addMarker(LatLng position, int drawableResource) {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(drawableResource);
        Bitmap bitmap = bitmapDrawable.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(bitmap, 50, 50, false);

        Marker marker = mMap.addMarker(new MarkerOptions().position(position).icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker clickedMarker) {
                if (clickedMarker.equals(marker)) {
                    Dialog existingDialog = markerDialogs.get(position);
                    if (existingDialog != null && existingDialog.isShowing()) {
                        return true;
                    }

                    Dialog customDialog = createCustomDialog(position);
                    markerDialogs.put(position, customDialog);
                    customDialog.show();

                    return true;
                }
                return false;
            }
        });
    }
}
