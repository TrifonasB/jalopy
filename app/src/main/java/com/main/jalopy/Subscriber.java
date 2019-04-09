package com.main.jalopy;

public class Subscriber extends Node implements Interfaces.Subscriber {

    Subscriber (String inetName, int portNumber){
        this.startClient(inetName, portNumber);
    }

    @Override
    public void register(Interfaces.Broker br, Interfaces.Topic t) {

    }

    @Override
    public void disconnect(Interfaces.Broker br, Interfaces.Topic t) {

    }

    @Override
    public void visualiseData(Interfaces.Topic t, Interfaces.Value v) {

    }
}
