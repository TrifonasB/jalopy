package com.main.jalopy;

public class Value implements Interfaces.Value {

    private Bus bus;
    private double latitude;
    private double longitude;

    Value (Bus bus, double latitute, double longitude){
        this.bus = bus;
        this.latitude = latitute;
        this.longitude = longitude;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
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
