package com.amralghawy.salesman.expandablelist;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.amralghawy.salesman.R;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;

/**
 * Created by Amr Alghawy on 5/19/2017.
 */

public class CustomerParentViewHolder extends ParentViewHolder {

    private TextView customerName;
    private ImageButton dropDownArrow;

    public CustomerParentViewHolder(View itemView) {
        super(itemView);

        this.customerName = (TextView) itemView.findViewById(R.id.parent_list_item_customer_name);
        this.dropDownArrow = (ImageButton) itemView.findViewById(R.id.parent_list_item_expand_arrow);
    }

    // Setters and Getters

    public TextView getCustomerName() {
        return customerName;
    }

    public void setCustomerName(TextView customerName) {
        this.customerName = customerName;
    }

    public ImageButton getDropDownArrow() {
        return dropDownArrow;
    }

    public void setDropDownArrow(ImageButton dropDownArrow) {
        this.dropDownArrow = dropDownArrow;
    }
}
