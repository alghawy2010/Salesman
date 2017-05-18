package com.amralghawy.salesman;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private List<Customer> customerList;

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

        // Toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar2);
        setSupportActionBar(myToolbar);

        Log.d(TAG, "onCreate - End");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
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

        if (intent.hasExtra("currentLocation")) {

            Location currentLocation = intent.getParcelableExtra("currentLocation");

            // Center Map around current position
            if (currentLocation != null) {
                LatLng home = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                mMap.addMarker(new MarkerOptions().position(home).title("-1"));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(home, 17));
            }
        }

        if (intent.hasExtra("customerList"))
            customerList = (List<Customer>) intent.getSerializableExtra("customerList");

        if (customerList != null || customerList.size() != 0)
            AddCustomerInMap(customerList);


        // Add Info window for markers
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                                      @Override
                                      public View getInfoWindow(Marker marker) {
                                          Log.d(TAG, "getInfoWindow - Start");


                                          Log.d(TAG, "getInfoWindow - End");
                                          return null;
                                      }

                                      @Override
                                      public View getInfoContents(Marker marker) {
                                          Log.d(TAG, "getInfoContents - Start");

                                          View view = getLayoutInflater().inflate(R.layout.map_custom_marker, null);

                                          TextView customerName = (TextView) view.findViewById(R.id.popupCustomerName);
                                          TextView customerLastTrxAmt = (TextView) view.findViewById(R.id.popupCustomerLastTrxAmt);
                                          TextView customerLastInvoice = (TextView) view.findViewById(R.id.popupCustomerLastInvoice);

                                          if (marker.getTitle() != null && marker.getTitle().equals("-1")) {
                                              // Salesman Address
                                              customerName.setText("You are here");
                                              ((ViewGroup)customerLastInvoice.getParent()).removeView(customerLastInvoice);
                                              ((ViewGroup)customerLastTrxAmt.getParent()).removeView(customerLastTrxAmt);
                                          }
                                          else {
                                              int customerIndex = Integer.parseInt(marker.getTitle());
                                              Customer selectedCustomer = customerList.get(customerIndex);

                                              customerName.setText(selectedCustomer.getName());
                                              if (selectedCustomer.getLastTrxAmt() != null && !selectedCustomer.getLastTrxAmt().equalsIgnoreCase("null"))
                                                  customerLastTrxAmt.setText(selectedCustomer.getLastTrxAmt());
                                              else
                                                  ((ViewGroup)customerLastTrxAmt.getParent()).removeView(customerLastTrxAmt);

                                              if (selectedCustomer.getLastInvoice() != null && !selectedCustomer.getLastInvoice().equalsIgnoreCase("null"))
                                                  customerLastInvoice.setText(selectedCustomer.getLastInvoice());
                                              else
                                                  ((ViewGroup)customerLastInvoice.getParent()).removeView(customerLastInvoice);
                                          }

                                          Log.d(TAG, "getInfoContents - End");
                                          return view;
                                      }
                                  }

        );


        Log.d(TAG, "onMapReady - End");
    }

    private void AddCustomerInMap(List<Customer> customerList) {

        for (int i = 0; i < customerList.size(); i++) {
            Customer currentCustomer = customerList.get(i);

            LatLng latLng = new LatLng(Double.parseDouble(currentCustomer.getLatitude()), Double.parseDouble(currentCustomer.getLongitude()));

            String title = i + "";

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title(title);


            if (currentCustomer.getStatus().equals("1")) {
                // Black color
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
            } else if (currentCustomer.getStatus().equals("2")) {
                // Gray color
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
            } else if (currentCustomer.getStatus().equals("3")) {
                // Red color
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            }


            Marker marker = mMap.addMarker(markerOptions);

        }

    }

    public void logoutBttnOnClick(MenuItem menuItem) {

        Log.d(TAG, "logoutBttnOnClick - Start");

        String url = "https://murmuring-peak-26751.herokuapp.com/api/sales_men/sign_out.json";

        final Context context = this;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE, url, null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // TODO: How to check if your response is successful or not

                            Log.d(TAG, "logoutBttnOnClick - logout JSON onResponse: " + response.toString());
                            VolleyLog.v("Response:%n %s", response.toString(4));

                            // Parse JSON Response
                            String responseMessage = response.getString("message");

                            // Move to original intent - login
                            Intent loginIntent = new Intent(context, LoginActivity.class);
                            context.startActivity(loginIntent);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "logoutBttnOnClick - logout JSON onErrorResponse: " + error.toString());
                        VolleyLog.e("Error: ", error.getMessage());
                    }
                }

        ) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Authorization", CustomersListActivity.authToken);
                return headers;
            }
        };

        MyVolley.getInstance(this).addToRequestQueue(jsonObjectRequest);


        Log.d(TAG, "logoutBttnOnClick - End");
    }
}
