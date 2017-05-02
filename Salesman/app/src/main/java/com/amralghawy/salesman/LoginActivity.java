package com.amralghawy.salesman;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity implements LocationListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    // Create onClick event for Sign-in button
    public void signInBttnOnClick(View view) {
        // 1- Get current User location
        // Check if GPS is granted or not
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Permission is granted now
            // Get current user location

            //ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, );
            LocationManager locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);

            boolean isGPSEnabled = false;
            boolean isNetworkEnabled = false;

            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            Location currentLocation = null;
            if (isGPSEnabled) {
                // Get current location from GPS
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            }
            else if (isNetworkEnabled) {
                // Get current location from Network
                currentLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
            else {
                // Error
            }

            //EditText text = (EditText) this.findViewById(R.id.editText2);
            //text.setText("Lat: "+ currentLocation.getLatitude() + "Long: "+ currentLocation.getLongitude());
        }
        else {
            // Location permission is required
            // Show Alert to enable location service
        }

        // 2- Prepare JSON request to cloud server

        // 3- Parse JSON response

        // 4- Send response to next activity to display a list of nearby customers

    }

    @Override
    public void onLocationChanged(Location location) {
        EditText text = (EditText) this.findViewById(R.id.editText2);
        text.setText("Lat: "+ location.getLatitude() + "Long: "+ location.getLongitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {
        //Toast.makeText(MainActivity.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }
}
