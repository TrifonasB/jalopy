package com.main.jalopy.oldies;

import com.main.jalopy.nodePack.Node;

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
        System.out.println("1.1.1       (aka openServer)");

        try{
            providerSocket = new ServerSocket(portNumber);
            connection = providerSocket.accept();
            outputStream = new ObjectOutputStream(connection.getOutputStream());
            inputStream = new ObjectInputStream(connection.getInputStream());
            System.out.println("1.1.2       (aka in the try)");

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("1.1.3       (aka finished)");

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
