package com.main.jalopy.auxilary;

import com.main.jalopy.frame.Interfaces;

public class Value implements Interfaces.Value {

    private double latitude;
    private double longitude;

    Value (double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
