package com.main.jalopy.auxilary;

import com.main.jalopy.frame.Interfaces;

public class Topic implements Interfaces.Topic {

    private String busLine;
    //private Bus bus;

    public Topic(String busLine) { this.busLine = busLine; }

    public void setBusLine(String busLine) { this.busLine = busLine; }

    public String getBusLine() {
        return busLine;
    }

}
