package com.amralghawy.salesman.expandablelist;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amralghawy.salesman.CustomersListActivity;
import com.amralghawy.salesman.R;
import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.List;

/**
 * Created by Amr Alghawy on 5/19/2017.
 */

public class CustomerExpandableAdapter extends ExpandableRecyclerAdapter<CustomerParentViewHolder, CustomerChildViewHolder> {

    private LayoutInflater inflater;
    // For logging purpose
    private final String TAG = CustomerExpandableAdapter.class.getSimpleName();

    public CustomerExpandableAdapter(Context context, List<ParentObject> parentItemList) {
        super(context, parentItemList);

        Log.d(TAG, "CustomerExpandableAdapter - Start");

        inflater = LayoutInflater.from(context);

        Log.d(TAG, "CustomerExpandableAdapter - End");
    }

    @Override
    public CustomerParentViewHolder onCreateParentViewHolder(ViewGroup viewGroup) {
        Log.d(TAG, "onCreateParentViewHolder - Start");

        View view = inflater.inflate(R.layout.list_view_parent, viewGroup, false);

        Log.d(TAG, "onCreateParentViewHolder - End");

        return new CustomerParentViewHolder(view);
    }

    @Override
    public CustomerChildViewHolder onCreateChildViewHolder(ViewGroup viewGroup) {

        Log.d(TAG, "onCreateChildViewHolder - Start");

        View view = inflater.inflate(R.layout.list_view_child, viewGroup, false);

        Log.d(TAG, "onCreateChildViewHolder - End");

        return new CustomerChildViewHolder(view);
    }

    @Override
    public void onBindParentViewHolder(CustomerParentViewHolder customerParentViewHolder, int i, Object o) {
        Log.d(TAG, "onBindParentViewHolder - Start");

        CustomerParent customerParent = (CustomerParent) o;
        customerParentViewHolder.getCustomerName().setText(customerParent.getCustomerName());

        Log.d(TAG, "onBindParentViewHolder - End");
    }

    @Override
    public void onBindChildViewHolder(CustomerChildViewHolder customerChildViewHolder, int i, Object o) {

        Log.d(TAG, "onBindChildViewHolder - Start");

        CustomerChild customerChild = (CustomerChild) o;
        customerChildViewHolder.getCustomerId().setText(customerChild.getId());
        customerChildViewHolder.getCustomerName().setText(customerChild.getName());
        customerChildViewHolder.getLatitude().setText(customerChild.getLatitude());
        customerChildViewHolder.getLongitude().setText(customerChild.getLongitude());
        customerChildViewHolder.getZone().setText(customerChild.getZone());
        customerChildViewHolder.getSaleman().setText(customerChild.getSaleman());
        customerChildViewHolder.getStatus().setText(customerChild.getStatus());
        customerChildViewHolder.getLastVisited().setText(customerChild.getLastVisited());
        customerChildViewHolder.getLastInvoice().setText(customerChild.getLastInvoice());
        customerChildViewHolder.getLastTrxAmt().setText(customerChild.getLastTrxAmt());
        customerChildViewHolder.getUserDefined1().setText(customerChild.getUserDefined1());
        customerChildViewHolder.getUserDefined2().setText(customerChild.getUserDefined2());
        customerChildViewHolder.getUserDefined3().setText(customerChild.getUserDefined3());

        Log.d(TAG, "onBindChildViewHolder - End");
    }
}
