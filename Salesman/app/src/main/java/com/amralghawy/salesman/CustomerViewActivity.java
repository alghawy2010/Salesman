package com.amralghawy.salesman;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CustomerViewActivity extends AppCompatActivity {

    // For logging purpose
    private final String TAG = CustomersListActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate - Start");

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_customer_view);

        // Toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar1);
        setSupportActionBar(myToolbar);

        // Get authentication token
        Intent intent = getIntent();
        Customer customer = (Customer) intent.getSerializableExtra("customer");

        ((TextView)this.findViewById(R.id.customerView_customerId)).setText(customer.getId());
        ((TextView)this.findViewById(R.id.customerView_customerName)).setText(customer.getName());
        ((TextView)this.findViewById(R.id.customerView_customerLong)).setText(customer.getLongitude());
        ((TextView)this.findViewById(R.id.customerView_customerLat)).setText(customer.getLatitude());
        ((TextView)this.findViewById(R.id.customerView_customerZone)).setText(customer.getZone());
        ((TextView)this.findViewById(R.id.customerView_customerSalesman)).setText(customer.getSaleman());
        ((TextView)this.findViewById(R.id.customerView_customerStatus)).setText(customer.getStatus());
        ((TextView)this.findViewById(R.id.customerView_customerLastVisit)).setText(customer.getLastVisited());
        ((TextView)this.findViewById(R.id.customerView_customerLastInvoice)).setText(customer.getLastInvoice());
        ((TextView)this.findViewById(R.id.customerView_customerLastTrxAmt)).setText(customer.getLastTrxAmt());
        ((TextView)this.findViewById(R.id.customerView_customerUserDefined1)).setText(customer.getUserDefined1());
        ((TextView)this.findViewById(R.id.customerView_customerUserDefined2)).setText(customer.getUserDefined2());
        ((TextView)this.findViewById(R.id.customerView_customerUserDefined3)).setText(customer.getUserDefined3());

        Log.d(TAG, "onCreate - Start");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
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
                headers.put("Authorization", CustomersListActivity.authToken);
                return headers;
            }
        };

        MyVolley.getInstance(this).addToRequestQueue(jsonObjectRequest);


        Log.d(TAG, "logoutBttnOnClick - End");
    }

}
