package com.main.jalopy.frame;

import com.main.jalopy.nodePack.NodeClient;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Publisher extends NodeClient implements Interfaces.Publisher {

    Publisher (String inetName, int portNumber){
        startClient(inetName, portNumber);
    }


    @Override
    public String readFromServer() {
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
    public void getBrokerList() {

    }

    @Override
    public Interfaces.Broker hashTopic(Interfaces.Topic t) {
        return null;
    }

    @Override
    public void push(Interfaces.Topic t, Interfaces.Value v) {

    }

    @Override
    public void notifyFailure(Interfaces.Broker br) {

    }

    public void readSource (String path, List<ArrayList<String>> contents){
        try {
            File f = new File(path);
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            String[] tokens;
            ArrayList<String> arrayTok = new ArrayList<>();
            int i = 0;
            do {
                line = br.readLine();
                if (line == null)
                    break;
                tokens = line.split(",");
                for (String tok: tokens){
                    arrayTok.add(tok);
                }
                contents.add(arrayTok);
            } while (true);

        }catch (FileNotFoundException e){
            System.err.println("Sorry, we couldn't find the file in the given path. Please try again.");
        }catch (Exception e) {
            System.err.println("Something went wrong :-(");
        }

    }
}
