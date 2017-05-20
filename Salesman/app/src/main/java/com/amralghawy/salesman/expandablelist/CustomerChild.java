package com.amralghawy.salesman.expandablelist;

import java.util.ArrayList;

/**
 * Created by Amr Alghawy on 5/19/2017.
 */

public class CustomerChild {

    private String id;
    private String name;
    private String longitude;
    private String latitude;
    private String zone;
    private String saleman;
    private String status;
    private String lastVisited;
    private String lastInvoice;
    private String lastTrxAmt;
    private String userDefined1;
    private String userDefined2;
    private String userDefined3;

    public CustomerChild() {
        this.id = "";
        this.name = "";
        this.longitude = "";
        this.latitude = "";
        this.zone = "";
        this.saleman = "";
        this.status = "";
        this.lastVisited = "";
        this.lastInvoice = "";
        this.lastTrxAmt = "";
        this.userDefined1 = "";
        this.userDefined2 = "";
        this.userDefined3 = "";

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getSaleman() {
        return saleman;
    }

    public void setSaleman(String saleman) {
        this.saleman = saleman;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLastVisited() {
        return lastVisited;
    }

    public void setLastVisited(String lastVisited) {
        this.lastVisited = lastVisited;
    }

    public String getLastInvoice() {
        return lastInvoice;
    }

    public void setLastInvoice(String lastInvoice) {
        this.lastInvoice = lastInvoice;
    }

    public String getLastTrxAmt() {
        return lastTrxAmt;
    }

    public void setLastTrxAmt(String lastTrxAmt) {
        this.lastTrxAmt = lastTrxAmt;
    }

    public String getUserDefined1() {
        return userDefined1;
    }

    public void setUserDefined1(String userDefined1) {
        this.userDefined1 = userDefined1;
    }

    public String getUserDefined2() {
        return userDefined2;
    }

    public void setUserDefined2(String userDefined2) {
        this.userDefined2 = userDefined2;
    }

    public String getUserDefined3() {
        return userDefined3;
    }

    public void setUserDefined3(String userDefined3) {
        this.userDefined3 = userDefined3;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

}
