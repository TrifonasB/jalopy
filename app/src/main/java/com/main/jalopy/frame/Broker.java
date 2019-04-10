package com.main.jalopy.frame;


import com.main.jalopy.nodePack.NodeServer;

import java.io.IOException;

public class Broker extends NodeServer implements Interfaces.Broker {

    Broker(int portNumber){
        openServer(portNumber);
        //startClient(inetName, portNumber);
    }


    @Override
    public String readFromClient() {
        String message = "";
        try{
            message = (String) getInputStream().readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return  message;
    }

    @Override
    public void sendToClient(String message) {
        try{
            this.getOutputStream().writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
