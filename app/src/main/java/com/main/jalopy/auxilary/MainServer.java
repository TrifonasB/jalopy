package com.main.jalopy.auxilary;

import com.main.jalopy.frame.Broker;
import com.main.jalopy.frame.Publisher;
import com.main.jalopy.frame.Subscriber;
import com.main.jalopy.nodePack.Node;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;


public class MainServer extends Thread{

    Node n;

    public MainServer(){}

    public static void main (String args[]){


        Console con1 = System.console();

        System.out.println("1");


        //Node broker02 = new Broker(4322);
        //Node broker03 = new Broker(4323);
        System.out.println("2");

        //((Broker) broker02).readFromClient();
       // ((Broker) broker02).sendToClient("Hi, from broker!");


        //Node pub01 = new Publisher("127.0.0.1", 4322);
        //Node sub01 = new Subscriber("127.0.0.1", 4321);

        /*
        //Thread broker01 = new MainServer();
        //Thread pub01 = new MainServer();
        broker01.start();
        ((MainServer) broker01).n = new Broker(4321);
        pub01.start();
        ((MainServer) pub01).n = new Publisher("127.0.0.1", 4321);
        */

        System.out.println("3");
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        readSource ("D:\\CodePlayground_(aka Workspace)\\AndroidStudio\\jalopy\\app\\src\\main\\java\\com\\main\\jalopy\\auxilary\\busPositionsNew.txt", data);


        /*
        System.out.println("4");
        for (int i = 0; i < 2; i++){
            for (int j = 0; j <= data.get(i).size(); j++){
                System.out.println(data.get(i).get(j));
            }
            System.out.println(data.get(i));
        }
        */

    }

    public static void readSource (String path, ArrayList<ArrayList<String>> contents){
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
