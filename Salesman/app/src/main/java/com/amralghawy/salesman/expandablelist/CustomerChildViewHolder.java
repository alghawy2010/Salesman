package com.amralghawy.salesman.expandablelist;

import android.view.View;
import android.widget.TextView;

import com.amralghawy.salesman.R;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;

/**
 * Created by Amr Alghawy on 5/19/2017.
 */

public class CustomerChildViewHolder extends ChildViewHolder {

    private TextView customerId;
    private TextView customerName;
    private TextView latitude;
    private TextView longitude;
    private TextView zone;
    private TextView saleman;
    private TextView status;
    private TextView lastVisited;
    private TextView lastInvoice;
    private TextView lastTrxAmt;
    private TextView userDefined1;
    private TextView userDefined2;
    private TextView userDefined3;


    public CustomerChildViewHolder(View itemView) {
        super(itemView);

        this.customerId = (TextView) itemView.findViewById(R.id.child_list_item_customer_id);
        this.customerName = (TextView) itemView.findViewById(R.id.child_list_item_customer_name);
        this.latitude = (TextView) itemView.findViewById(R.id.child_list_item_customer_latitude);
        this.longitude = (TextView) itemView.findViewById(R.id.child_list_item_customer_longitude);
        this.zone = (TextView) itemView.findViewById(R.id.child_list_item_customer_zone);
        this.saleman = (TextView) itemView.findViewById(R.id.child_list_item_customer_saleman);
        this.status = (TextView) itemView.findViewById(R.id.child_list_item_customer_status);
        this.lastVisited = (TextView) itemView.findViewById(R.id.child_list_item_customer_lastVisited);
        this.lastInvoice = (TextView) itemView.findViewById(R.id.child_list_item_customer_lastInvoice);
        this.lastTrxAmt = (TextView) itemView.findViewById(R.id.child_list_item_customer_lastTrxAmt);
        this.userDefined1 = (TextView) itemView.findViewById(R.id.child_list_item_customer_userDefined1);
        this.userDefined2 = (TextView) itemView.findViewById(R.id.child_list_item_customer_userDefined2);
        this.userDefined3 = (TextView) itemView.findViewById(R.id.child_list_item_customer_userDefined3);
    }

    public TextView getCustomerId() {
        return customerId;
    }

    public void setCustomerId(TextView customerId) {
        this.customerId = customerId;
    }

    public TextView getCustomerName() {
        return customerName;
    }

    public void setCustomerName(TextView customerName) {
        this.customerName = customerName;
    }

    public TextView getLatitude() {
        return latitude;
    }

    public void setLatitude(TextView latitude) {
        this.latitude = latitude;
    }

    public TextView getLongitude() {
        return longitude;
    }

    public void setLongitude(TextView longitude) {
        this.longitude = longitude;
    }

    public TextView getZone() {
        return zone;
    }

    public void setZone(TextView zone) {
        this.zone = zone;
    }

    public TextView getSaleman() {
        return saleman;
    }

    public void setSaleman(TextView saleman) {
        this.saleman = saleman;
    }

    public TextView getStatus() {
        return status;
    }

    public void setStatus(TextView status) {
        this.status = status;
    }

    public TextView getLastVisited() {
        return lastVisited;
    }

    public void setLastVisited(TextView lastVisited) {
        this.lastVisited = lastVisited;
    }

    public TextView getLastInvoice() {
        return lastInvoice;
    }

    public void setLastInvoice(TextView lastInvoice) {
        this.lastInvoice = lastInvoice;
    }

    public TextView getLastTrxAmt() {
        return lastTrxAmt;
    }

    public void setLastTrxAmt(TextView lastTrxAmt) {
        this.lastTrxAmt = lastTrxAmt;
    }

    public TextView getUserDefined1() {
        return userDefined1;
    }

    public void setUserDefined1(TextView userDefined1) {
        this.userDefined1 = userDefined1;
    }

    public TextView getUserDefined2() {
        return userDefined2;
    }

    public void setUserDefined2(TextView userDefined2) {
        this.userDefined2 = userDefined2;
    }

    public TextView getUserDefined3() {
        return userDefined3;
    }

    public void setUserDefined3(TextView userDefined3) {
        this.userDefined3 = userDefined3;
    }
}
