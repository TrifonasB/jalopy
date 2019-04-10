package com.main.jalopy.nodePack;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class NodeServer extends Node {

    ServerSocket providerSocket = null;
    Socket connection = null;
    private ObjectOutputStream outputStream = null;
    private ObjectInputStream inputStream = null;

    public void openServer(int portNumber){

        try{
            providerSocket = new ServerSocket(portNumber);
            outputStream = new ObjectOutputStream(connection.getOutputStream());
            inputStream = new ObjectInputStream(connection.getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void closeServer () {
        try{
            inputStream.close();
            outputStream.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try{
                providerSocket.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public abstract String readFromClient ();
    public abstract void sendToClient (String message);

    public ObjectInputStream getInputStream() {
        return inputStream;
    }

    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }
}
