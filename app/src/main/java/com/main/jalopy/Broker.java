package com.main.jalopy;


public class Broker extends Node implements Interfaces.Broker {

    Broker(String inetName, int portNumber){
        this.openServer(portNumber);
        this.startClient(inetName, portNumber);
    }

    @Override
    public void calculateKey() {
        
    }

    @Override
    public Interfaces.Publisher acceptConnection(Interfaces.Publisher pub) {
        return null;
    }

    @Override
    public Interfaces.Subscriber acceptConnection(Interfaces.Subscriber sub) {
        return null;
    }

    @Override
    public void notifyPublisher(String message) {

    }

    @Override
    public void pull(Interfaces.Topic t) {

    }
}
