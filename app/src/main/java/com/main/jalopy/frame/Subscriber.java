package com.main.jalopy.frame;

import com.main.jalopy.nodePack.NodeClient;
import java.io.IOException;

public class Subscriber extends NodeClient implements Interfaces.Subscriber {

    Subscriber (String inetName, int portNumber){
        this.startClient(inetName, portNumber);
    }

    @Override
    public String readFromServer () {
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
    public void sendToServer(String message) {
        try{
            this.getOutputStream().writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
