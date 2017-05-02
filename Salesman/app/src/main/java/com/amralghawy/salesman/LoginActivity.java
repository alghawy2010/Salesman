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
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements LocationListener{

    private final String TAG = LoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate - Start");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Log.d(TAG, "onCreate - End");
    }

    // Create onClick event for Sign-in button
    public void signInBttnOnClick(View view) {
        Log.d(TAG, "signInBttnOnClick - Start");
        // 1- Get current User location
        // Check if GPS is granted or not
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Permission is granted now
            Log.d(TAG, "signInBttnOnClick - Permission is granted");
            // Get current user location

            //ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, );
            LocationManager locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);

            boolean isGPSEnabled = false;
            boolean isNetworkEnabled = false;

            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            Location currentLocation = null;
            if (isGPSEnabled) {
                Log.d(TAG, "signInBttnOnClick - GPS Enabled");
                // Get current location from GPS
                currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, this);

                if (currentLocation != null) {
                    Log.d(TAG, "signInBttnOnClick - Call onLocationChanged method");
                    onLocationChanged(currentLocation);
                }
                else {
                    Toast.makeText(getBaseContext(), "No Location Provider Found Check Your Code", Toast.LENGTH_SHORT).show();
                }
            }
            else if (isNetworkEnabled) {
                Log.d(TAG, "signInBttnOnClick - Network Enabled");
                // Get current location from Network
                currentLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
            else {
                // Error
                Log.d(TAG, "signInBttnOnClick - GPS and Network are not enabled");
                return;
            }

            // Get User name and Password
            EditText userName = (EditText) this.findViewById(R.id.userNameEditText);
            EditText password = (EditText) this.findViewById(R.id.passwordEditText);

            // 2- Prepare JSON request to cloud server
            
            // 3- Parse JSON response

            // 4- Send response to next activity to display a list of nearby customers
        }
        else {
            // Location permission is required
            Log.d(TAG, "signInBttnOnClick - Permission is not granted");
            // Show Alert to enable location service
            Toast.makeText(getBaseContext(), "No Service Provider Available", Toast.LENGTH_SHORT).show();

            // Ask for permissions
            // ToDo: What shoud i do if user reject to grant below permission? is below permission enough or internet access also required
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 4);

            /*
            // Show dialog to enable network and GPS
            final Context mContext = getBaseContext();
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
            alertDialog.setTitle("GPS Not Enabled");
            alertDialog.setMessage("Do you wants to turn On GPS");


            alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    mContext.startActivity(intent);
                }
            });


            alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
             */
        }

        Log.d(TAG, "signInBttnOnClick - End");
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "onLocationChanged - Start");

        EditText text = (EditText) this.findViewById(R.id.userNameEditText);
        text.setText("Lat: "+ location.getLatitude() + "Long: "+ location.getLongitude());

        Log.d(TAG, "onLocationChanged - End");
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
