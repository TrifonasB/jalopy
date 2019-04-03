package com.main.jalopy;

public class Bus {

    private String lineNumber;
    private String routeCode;
    private String vehicled;
    private String lineName;
    private String busLineId;
    private String info;

    Bus (String lineNumber, String routeCode, String vehicled, String lineName, String busLineId, String info){
        this.lineNumber = lineNumber;
        this.routeCode = routeCode;
        this.vehicled = vehicled;
        this.lineName = lineName;
        this.busLineId = busLineId;
        this.info = info;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }

    public String getVehicled() {
        return vehicled;
    }

    public void setVehicled(String vehicled) {
        this.vehicled = vehicled;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getBusLineId() {
        return busLineId;
    }

    public void setBusLineId(String busLineId) {
        this.busLineId = busLineId;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
