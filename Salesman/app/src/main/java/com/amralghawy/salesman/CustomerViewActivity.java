package com.amralghawy.salesman;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class CustomerViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_view);

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
    }

}
