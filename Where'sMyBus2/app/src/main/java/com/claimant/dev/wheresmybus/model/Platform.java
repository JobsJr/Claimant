package com.claimant.dev.wheresmybus.model;

/**
 * POJO that defines the data model
 *
 * Created by rajeevkr on 4/24/16.
 */
public class Platform {
    private int mPlatformNum;
    private String mBusNum;
    private String mRouteAddress;

    public String getRouteAddress() {
        return mRouteAddress;
    }

    public void setRouteAddress(String mRouteAddress) {
        this.mRouteAddress = mRouteAddress;
    }

    public int getPlatformNum() {
        return mPlatformNum;
    }

    public void setPlatformNum(int mPlatformNum) {
        this.mPlatformNum = mPlatformNum;
    }

    public String getBusNum() {
        return mBusNum;
    }

    public void setBusNum(String mBusNum) {
        this.mBusNum = mBusNum;
    }
}
