package com.main.jalopy;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public abstract class Node implements Interfaces.Node {
    @Override
    public void init(int x) {

    }

    @Override
    public void connect() {

    }

    @Override
    public void disconnect() {

    }

    @Override
    public void updateNodes() {

    }

    public abstract void startClient(String inetName, int portNumber);

    public abstract void openServer(int portNumber);

}


    /*
    public void openServer(int portNumber) {
        ServerSocket providerSocket = null;
        Socket connection = null;
        String message;

        try{
            providerSocket = new ServerSocket(portNumber);
            ObjectOutputStream outputStream = new ObjectOutputStream(connection.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(connection.getInputStream());

            outputStream.writeObject("something");  //send message to client
            message = (String) inputStream.readObject();    //read from Client

            inputStream.close();
            outputStream.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            try{
                providerSocket.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }*/

        /*Socket requestSocket = null;
        ObjectOutputStream outputStream = null;
        ObjectInputStream inputStream = null;
        //String message;

        try{
            requestSocket = new Socket(InetAddress.getByName(inetName), portNumber); //loopback IP
            outputStream = new ObjectOutputStream(requestSocket.getOutputStream());
            inputStream = new ObjectInputStream((requestSocket.getInputStream()));

            //message = (String) inputStream.readObject();   //read from Server
            outputStream.writeObject("something");  //send to Server
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                inputStream.close();
                outputStream.close();
                requestSocket.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }*/