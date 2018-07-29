/**
 * This is the activity for the map.
 * The map will show all the locations available in the app.
 * This activity initializes the map, gets location permission, and request permission result.
 *
 * @author Aqil
 * @author Arazoo
 * @author Chris
 * @author Scott
 * @author Jacob
 * @author Jaewoong
 */
package com.example.scott.dalmapproject;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity implements GoogleMap.OnMyLocationButtonClickListener,
        OnMapReadyCallback {

    private GoogleMap mMap;
    public static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 99;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        //check if the location permission has already been granted
        checkLocationPermission();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        killamFloorPlanButtonFunctionality();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Show zoom buttons
        mMap.getUiSettings().setZoomControlsEnabled(true);

        // Remove all labels from the map
        mMap.setMapStyle(new MapStyleOptions(getResources()
                .getString(R.string.style_json)));

        // Check for location permission
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_REQUEST_FINE_LOCATION);
            }
        } else {
            // Permission has already been granted
            mMap.setMyLocationEnabled(true);
        }

        // Show the My Location button
        mMap.setOnMyLocationButtonClickListener(this);

        // Add a marker at the killam
        LatLng killam = new LatLng(44.637391666666666, -63.591011111111115);
        mMap.addMarker(new MarkerOptions().position(killam).title("Killam Memorial Library"));

        // Add a marker at the goldberg
        LatLng goldberg = new LatLng(44.63741111111111, -63.58735277777778);
        mMap.addMarker(new MarkerOptions().position(goldberg).title("Goldberg Computer Science Building"));

        // Add a marker at the henry hicks
        LatLng henryHicks = new LatLng(44.636175, -63.59314166666667);
        mMap.addMarker(new MarkerOptions().position(henryHicks).title("Henry Hicks Building"));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(killam));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(killam,15.5f));

    }

    /**
     * Functionality for the killam floor button
     */
    private void killamFloorPlanButtonFunctionality(){
        Button killamFloorPlanButton = (Button) findViewById(R.id.exterior_to_killam_button);
        killamFloorPlanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), KillamFloorPlanActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * Functionality for when the location button is clicked
     *
     * @return - false so event callback animates the camera to the users location
     */
    @Override
    public boolean onMyLocationButtonClick() {
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    /**
     * checks for and requests location permission
     *
     * @return - returns based on whether of not the permission was requested
     */
    public boolean checkLocationPermission() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_FINE_LOCATION);

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_FINE_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }


    /**
     * Checks if the permission was granted or denied
     *
     * @param requestCode - Specific code for the permission being requested
     * @param permissions - list of permissions being requested
     * @param grantResults - results of the permission request
     */
    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        if (requestCode == MY_PERMISSIONS_REQUEST_FINE_LOCATION) {
            if (permissions.length == 1 &&
                    permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission granted
                mMap.setMyLocationEnabled(true);
            } else {
                // permission denied
            }
        }
    }
}
