package com.amralghawy.salesman.expandablelist;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amr Alghawy on 5/19/2017.
 */

public class CustomerParent implements ParentObject{


    private String customerName;
    private List<Object> childrenList;

    public CustomerParent() {
        this.customerName = "";
        this.childrenList = new ArrayList<Object>();
    }

    @Override
    public List<Object> getChildObjectList() {
        return childrenList;
    }

    @Override
    public void setChildObjectList(List<Object> list) {
        this.childrenList = list;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
