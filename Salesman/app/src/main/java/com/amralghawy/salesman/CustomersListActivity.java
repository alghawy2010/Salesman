package com.amralghawy.salesman;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomersListActivity extends AppCompatActivity implements LocationListener {

    private List<Customer> customerList = new ArrayList<>();
    private RecyclerView recyclerView;
    private CustomerAdapter mAdapter;
    static String authToken;
    private Location currentLocation;
    private final String url = "https://murmuring-peak-26751.herokuapp.com/api/sales_men/near_customers.json";

    // For logging purpose
    private final String TAG = CustomersListActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate - Start");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers_list);

        // Toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // Recycler View controller
        this.recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        mAdapter = new CustomerAdapter(customerList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        // Get authentication token
        Intent intent = getIntent();
        authToken = intent.getStringExtra("auth_token");

        // Get current location
        currentLocation = getCurrentLocation();

        // Get list of customer near to you
        if (currentLocation != null)
            makeJsonObjectRequest(url, authToken, currentLocation);

        // Populate customers list

        Log.d(TAG, "onCreate - End");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Log.d(TAG, "onCreateOptionsMenu - Start");

        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem refreshButton = menu.findItem(R.id.action_refresh);
        if (refreshButton != null) {
            refreshButton.setVisible(true);
            refreshButton.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    Log.d(TAG, "onMenuItemClick - Start");

                    // Get current location
                    currentLocation = getCurrentLocation();

                    // Get list of customer near to you
                    if (currentLocation != null)
                        makeJsonObjectRequest(url, authToken, currentLocation);

                    Log.d(TAG, "onMenuItemClick - End");
                    return true;
                }
            });
        }
        Log.d(TAG, "onCreateOptionsMenu - End");
        return true;
    }

    // ---------------------------------------------------------------------------
    // LocationListener Methods
    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "Location changed: "+location.getLatitude()+","+location.getLongitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
    // ---------------------------------------------------------------------------

    private Location getCurrentLocation() {
        Log.d(TAG, "getCurrentLocation - Start");

        Location currentLocation = null;
        // Check if GPS is granted or not
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            // Permission is granted now
            Log.d(TAG, "getCurrentLocation - Permission is granted");

            LocationManager locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
			
            /*
            Criteria criteria = new Criteria();
            String provider = locationManager.getBestProvider(criteria, false);
            Log.d(TAG, "getCurrentLocation - Best Provider: "+provider);
            */
            boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (isGPSEnabled || isNetworkEnabled) {
            /*
            if (isGPSEnabled) {
                Log.d(TAG, "getCurrentLocation - GPS Enabled");
                // Get current location from GPS
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                if (currentLocation != null) {
                    Log.d(TAG, "getCurrentLocation - currentLocation: lat = "+currentLocation.getLatitude()+", long = "+currentLocation.getLongitude());
                    Toast.makeText(getBaseContext(), "currentLocation: lat = "+currentLocation.getLatitude()+", long = "+currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getBaseContext(), "No Location Provider Found Check Your Code", Toast.LENGTH_SHORT).show();
                }
            }
            else if (isNetworkEnabled) {
                Log.d(TAG, "getCurrentLocation - Network Enabled");
                // Get current location from Network
                currentLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                */
                List<String> providers = locationManager.getProviders(true);
                for (String provider : providers) {
                    locationManager.requestLocationUpdates(provider, 0, 0, this);
                    Location l = locationManager.getLastKnownLocation(provider);
                    if (l == null) {
                        continue;
                    }
                    if (currentLocation == null || l.getAccuracy() < currentLocation.getAccuracy()) {
                        // Found best last known location: %s", l);
                        currentLocation = l;
                    }
                }
                if (currentLocation == null) {
                    Log.d(TAG, "getCurrentLocation - CurrentLocation is null");
                    Toast.makeText(getBaseContext(), "Enable GPS and try again", Toast.LENGTH_SHORT).show();
                }

            }
            else {
                // Error
                Log.d(TAG, "getCurrentLocation - GPS and Network are not enabled");

                // Show Alert to enable GPS
                Toast.makeText(getBaseContext(), "Enable GPS and try again", Toast.LENGTH_SHORT).show();

                return null;
            }

        }
        else
        {
            // Location permission is required
            Log.d(TAG, "getCurrentLocation - Permission is not granted");

            // Show Alert to enable location service
            Toast.makeText(getBaseContext(), "Grant location permission to APP", Toast.LENGTH_SHORT).show();

            // TODO: Show dialog to grant permissions and reload data after that
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }

        Log.d(TAG, "getCurrentLocation - End");

        return currentLocation;
    }


    private void makeJsonObjectRequest(String url, final String token, Location currentLocation) {
        Log.d(TAG, "makeJsonObjectRequest - Start");

        double lat = currentLocation.getLatitude();
        double lang = currentLocation.getLongitude();

        url += "?long="+lang+"&lat="+lat;

        Log.d(TAG, "makeJsonObjectRequest - Customer list JSON request: "+ url);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try
                        {
                            // TODO: How to check if your response is successful or not

                            Log.d(TAG, "makeJsonObjectRequest - Sign-In JSON onResponse: "+ response.toString());
                            VolleyLog.v("Response:%n %s", response.toString(4));

                            // Parse JSON Response

                            if (response != null && response.length() != 0) {
                                // to clear list in case of reloading
                                customerList.clear();
                            }

                            for (int i = 0; i < response.length(); i++) {

                                JSONObject jsonCustomer = (JSONObject) response.get(i);

                                Customer customer = new Customer();
                                customer.setId(jsonCustomer.getString("customer_id"));
                                customer.setName(jsonCustomer.getString("name"));
                                customer.setLongitude(jsonCustomer.getString("long"));
                                customer.setLatitude(jsonCustomer.getString("lat"));
                                customer.setZone(jsonCustomer.getString("zone_name"));
                                customer.setSaleman(jsonCustomer.getString("salesman_name"));
                                customer.setStatus(jsonCustomer.getString("status"));
                                customer.setLastVisited(jsonCustomer.getString("last_visited_at"));
                                customer.setLastInvoice(jsonCustomer.getString("last_invoice_at"));
                                customer.setLastTrxAmt(jsonCustomer.getString("last_trx_amount"));
                                customer.setUserDefined1(jsonCustomer.getString("extra1"));
                                customer.setUserDefined2(jsonCustomer.getString("extra2"));
                                customer.setUserDefined3(jsonCustomer.getString("extra3"));

                                customerList.add(customer);
                            }

                            // Display list into recycle view
                            mAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "makeJsonObjectRequest - Sign-In JSON onErrorResponse: "+ error.toString());
                        VolleyLog.e("Error: ", error.getMessage());

                        Toast.makeText(getBaseContext(), "Please try again", Toast.LENGTH_SHORT).show();
                    }
                }

        )
        {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Authorization", token);
                return headers;
            }
        };


        MyVolley.getInstance(this).addToRequestQueue(jsonArrayRequest);

        Log.d(TAG, "makeJsonObjectRequest - End");
    }

    // Create onClick event for Sign-in button
    public void floatingBttnOnClick(View view) {

        Log.d(TAG, "floatingBttnOnClick - Start");

        // Move to next intent - customers list
        Intent mapIntent = new Intent(this, MapActivity.class);
        // Pass customers list to next activity
        mapIntent.putExtra("customerList", (Serializable) this.customerList);
        // Pass current location to next activity
        mapIntent.putExtra("currentLocation", this.currentLocation);

        this.startActivity(mapIntent);

        Log.d(TAG, "floatingBttnOnClick - End");
    }

    public void logoutBttnOnClick(MenuItem menuItem) {

        Log.d(TAG, "logoutBttnOnClick - Start");

        String url = "https://murmuring-peak-26751.herokuapp.com/api/sales_men/sign_out.json";

        final Context context = this;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE, url, null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try
                        {
                            // TODO: How to check if your response is successful or not

                            Log.d(TAG, "logoutBttnOnClick - logout JSON onResponse: "+ response.toString());
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
                        Log.d(TAG, "logoutBttnOnClick - logout JSON onErrorResponse: "+ error.toString());
                        VolleyLog.e("Error: ", error.getMessage());
                    }
                }

        )
        {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Authorization", authToken);
                return headers;
            }
        };

        MyVolley.getInstance(this).addToRequestQueue(jsonObjectRequest);


        Log.d(TAG, "logoutBttnOnClick - End");
    }

}
