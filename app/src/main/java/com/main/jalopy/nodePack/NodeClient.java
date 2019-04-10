package com.main.jalopy.nodePack;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public abstract class NodeClient extends Node {

    private Socket requestSocket = null;
    private ObjectOutputStream outputStream = null;
    private ObjectInputStream inputStream = null;

    public void startClient(String inetName, int portNumber){

        try{
            requestSocket = new Socket(InetAddress.getByName(inetName), portNumber); //loopback IP
            outputStream = new ObjectOutputStream(requestSocket.getOutputStream());
            inputStream = new ObjectInputStream((requestSocket.getInputStream()));

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopClient () {
        try {
            inputStream.close();
            outputStream.close();
            requestSocket.close();
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public ObjectInputStream getInputStream() {
        return inputStream;
    }

    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }

    public abstract String readFromServer ();
    public abstract void sendToServer (String message);

}
