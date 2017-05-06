package com.amralghawy.salesman;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Amr Alghawy on 5/6/2017.
 */

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.MyViewHolder> {


    private List<Customer> customersList;

    // For logging purpose
    private final String TAG = CustomerAdapter.class.getSimpleName();

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView customerId;
        public TextView customerName;

        public MyViewHolder(View view) {
            super(view);
            customerId = (TextView) view.findViewById(R.id.customerId);
            customerName = (TextView) view.findViewById(R.id.customerName);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "MyViewHolder - onClick");
                    int itemPosition = getPosition();
                    Customer customer = customersList.get(itemPosition);
                    Toast.makeText(v.getContext(), "Name = " + customer.getName(), Toast.LENGTH_SHORT).show();

                    // Move to next intent - customers list
                    Intent customersListIntent = new Intent(v.getContext(), CustomerViewActivity.class);
                    customersListIntent.putExtra("customer", customer);
                    v.getContext().startActivity(customersListIntent);
                }
            });
        }

    }

    public CustomerAdapter(List<Customer> customersList) {
        this.customersList = customersList;
    }

    //----------------------------------------------------------------------------------------------
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customer_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Customer customer = customersList.get(position);
        holder.customerId.setText(customer.getId());
        holder.customerName.setText(customer.getName());
    }

    @Override
    public int getItemCount() {
        return customersList.size();
    }
    //----------------------------------------------------------------------------------------------

}
