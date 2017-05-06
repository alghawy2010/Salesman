package com.amralghawy.salesman;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    // For logging purpose
    private final String TAG = CustomersListActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate - Start");
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "onMapReady - Start");

        mMap = googleMap;

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        // Enable map zoom
        mMap.getUiSettings().setZoomGesturesEnabled(true);

        // Get List of customer
        Intent intent = getIntent();
        List<Customer> customerList = (List<Customer>) intent.getSerializableExtra("customerList");
        Location currentLocation = intent.getParcelableExtra("currentLocation");

        // Center Map around current position
        LatLng home = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        mMap.addMarker(new MarkerOptions().position(home).title("You are here"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(home, 17));

        AddCustomerInMap(customerList);

        Log.d(TAG, "onMapReady - End");
    }

    private void AddCustomerInMap(List<Customer> customerList) {

        for (int i = 0; i < customerList.size(); i++) {
            Customer currentCustomer = customerList.get(i);

            LatLng latLng = new LatLng(Double.parseDouble(currentCustomer.getLatitude()), Double.parseDouble(currentCustomer.getLongitude()));

            String title = currentCustomer.getName() + "\n"
                    + currentCustomer.getId() + "\n"
                    + currentCustomer.getLastTrxAmt() + "\n"
                    + currentCustomer.getLastInvoice();

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title(title);
            markerOptions.snippet(title);

            if (currentCustomer.getStatus().equals("1")) {
                // Black color
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
            }
            else if (currentCustomer.getStatus().equals("2")) {
                // Gray color
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
            }
            else if (currentCustomer.getStatus().equals("3")) {
                // Red color
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            }


            Marker marker = mMap.addMarker(markerOptions);
            
        }

    }
}
