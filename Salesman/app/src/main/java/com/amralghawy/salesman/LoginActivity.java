package com.amralghawy.salesman;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity{

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

        // Get User name and Password
        EditText userName = (EditText) this.findViewById(R.id.userNameEditText);
        EditText password = (EditText) this.findViewById(R.id.passwordEditText);

        if (! userName.getText().toString().isEmpty() && ! password.getText().toString().isEmpty()) {

            // 2- Prepare JSON request to cloud server and parse response
            String url = "https://murmuring-peak-26751.herokuapp.com/api/sales_men/sign_in.json";

            makeJsonObjectRequest(this, url, userName.getText().toString(), password.getText().toString());

        }
        else {
            // Ask user to enter user name and password to sign-in
            Log.d(TAG, "signInBttnOnClick - Ask user to enter username and password to sing-in");

            Toast.makeText(getBaseContext(), "Please enter username and password", Toast.LENGTH_SHORT).show();
        }


        // 3- Parse JSON response

        // 4- Send response to next activity to display a list of nearby customer

        Log.d(TAG, "signInBttnOnClick - End");
    }

    private void makeJsonObjectRequest(final Context context, String url, String userName, String password) {
        Log.d(TAG, "makeJsonObjectRequest - Start");
        try
        {
            final JSONObject signInRq = new JSONObject();
            JSONObject api_sales_man = new JSONObject();

            api_sales_man.put("username", userName);
            api_sales_man.put("password", password);


            signInRq.put("api_sales_man", api_sales_man);

            Log.d(TAG, "makeJsonObjectRequest - Sign-In JSON Request: "+ signInRq.toString());

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, signInRq,

                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try
                            {
                                // TODO: How to check if your response is successful or not

                                Log.d(TAG, "makeJsonObjectRequest - Sign-In JSON onResponse: "+ response.toString());
//                                VolleyLog.v("Response:%n %s", response.toString(4));

                                // Parse JSON Response
                                String auth_token = response.getString("auth_token");

                                // Move to next intent - customers list
                                Toast.makeText(getBaseContext(), "Before Intent call", Toast.LENGTH_SHORT).show();
                                /*
                                Intent CustomersListIntent = new Intent(context, CustomersListActivity.class);
                                CustomersListIntent.putExtra("auth_token", auth_token);
                                context.startActivity(CustomersListIntent);
                                */
                                moveToCustomerListActivity(auth_token);

                                Toast.makeText(getBaseContext(), "After Intent call", Toast.LENGTH_SHORT).show();

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
                        }
                    }

            );


            MyVolley.getInstance(this).addToRequestQueue(jsonObjectRequest);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "makeJsonObjectRequest - Start");
    }

    public void moveToCustomerListActivity(String auth_token) {
        Intent CustomersListIntent = new Intent(this, CustomersListActivity.class);
        CustomersListIntent.putExtra("auth_token", auth_token);
        this.startActivity(CustomersListIntent);
    }

}
