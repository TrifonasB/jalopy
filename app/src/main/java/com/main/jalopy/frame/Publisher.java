package com.main.jalopy.frame;

import com.main.jalopy.auxilary.Value;
import com.main.jalopy.frame.Interfaces.Node;
import com.main.jalopy.auxilary.Topic;
import com.main.jalopy.nodePack.NodeInfo;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;


import static com.main.jalopy.auxilary.Hashing.getIntValue;
import static com.main.jalopy.auxilary.Hashing.hashMD5;
import static com.main.jalopy.nodePack.Node.*;


public class Publisher implements Node{

    Socket request = null;
    ObjectInputStream in = null;
    ObjectOutputStream out = null;

    ArrayList<Topic> myTopics = null;
    HashMap topics = new HashMap<Topic, Integer>();

    public Publisher(){
        b.add(new NodeInfo(0,"127.0.0.1",5050));
        b.add(new NodeInfo(1,"127.0.0.1",5051));
        b.add(new NodeInfo(2,"127.0.0.1",5052));
        b.add(new NodeInfo(3,"127.0.0.1",5053));


        myTopics = new ArrayList<>();
    }

    public Publisher(String ip, int port){
        info.setIP(ip);
        info.setPort(port);

        b.add(new NodeInfo(0,"127.0.0.1",5050));
        b.add(new NodeInfo(1,"127.0.0.1",5051));
        b.add(new NodeInfo(2,"127.0.0.1",5052));
        b.add(new NodeInfo(3,"127.0.0.1",5053));


        myTopics = new ArrayList<>();
    }


    public void init(int test){
            loadTopics("./textFiles/busLinesNew.txt");
            System.out.println("Acquiring update from brokers...");
            getBrokerList();
            System.out.println("Exiting");
    }

    public void connect(){
        try {
            request = new Socket(InetAddress.getByName("127.0.0.1"), 4321);
            out = new ObjectOutputStream(request.getOutputStream());
            in = new ObjectInputStream(request.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void connect(Broker broker){
        try{
            request = new Socket(InetAddress.getByName(broker.getIp()), broker.getPort());
            out = new ObjectOutputStream(request.getOutputStream());
            in = new ObjectInputStream((request.getInputStream()));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void disconnect(){
        try {
            out.close();
            in.close();
            request.close();;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void updateNodes(){}

    public void getBrokerList(){

        for(int i =0; i<1;i++) {
            try {
                Socket connection = new Socket(InetAddress.getByName(b.get(i).getIP()),b.get(i).getPort());
                ObjectOutputStream out = new ObjectOutputStream(connection.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(connection.getInputStream());

                out.writeUTF("pub");
                out.writeUTF("registration");
                out.flush();

                int firstTopic = Integer.parseInt(in.readUTF());
                int lastTopic = Integer.parseInt(in.readUTF());

                System.out.println("Broker " + b.get(i).getIP() + " > " + in.readUTF());

                out.close();
                in.close();
                connection.close();

                b.get(i).setFirst(firstTopic);
                b.get(i).setLast(lastTopic);

                System.out.println("Broker number " + b.get(i).getID() + " " + b.get(i).getFirst() + " " + b.get(i).getLast());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public HashMap<Integer, Broker> getBrokers () {
        Broker temp;
        HashMap brokersMap = new HashMap<Integer, Broker>();
        for (NodeInfo node: b){
            temp = new Broker(node.getIP(), node.getPort());
            brokersMap.put(temp.hashThis(), temp);
        }
        return brokersMap;
    }

    public Broker hashTopic(Topic t){return new Broker();}

    public void push(Topic t, Value v){}

    public void notifyFailure(Broker b){}


    public void loadTopics(String f){
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            String str;
            String[] tokens;
            Topic t;
            while((str=br.readLine())!=null){
                tokens = str.split(",");
                t = new Topic(tokens[1]);
                if (!duplicateTopic(myTopics,t))
                    myTopics.add(t);
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadTopics(){
        ArrayList<ArrayList<String>> lines = new ArrayList<>();
        readSource("BusLines.txt", lines);

        ArrayList<ArrayList<String>> data = new ArrayList<>();
        readSource("BusPositionsNew.txt", data);

        String code;
        Topic t = null;
        Boolean found = false;
        for (ArrayList<String> entry: data){
            code = entry.get(0);
            //find lineId in lines
            for (ArrayList<String> line: lines){
                if (line.get(0).compareToIgnoreCase(code) == 0){
                    t = new Topic(line.get(1));
                    found = true;
                    break;
                }
            }
            if (found && !topics.containsKey(t))
                topics.put(t, getIntValue(hashMD5(t.getBusLine())));
        }
    }

    //reads a comma separated file into an ArrayList
    public static void readSource (String path, ArrayList<ArrayList<String>> contents){
        try {
            File f = new File(path);
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            String[] tokens;
            ArrayList<String> arrayTok = new ArrayList<>();
            do {
                line = br.readLine();
                if (line == null)
                    break;
                tokens = line.split(",");
                for (String tok: tokens)
                    arrayTok.add(tok);
                contents.add(arrayTok);
            } while (true);
        }catch (FileNotFoundException e){
            System.err.println("Sorry, we couldn't find the file in the given path. Please try again.");
        }catch (Exception e) {
            System.err.println("Something went wrong :-(");
        }
    }

    private boolean duplicateTopic(ArrayList<Topic> list, Topic t){
        if (list.indexOf(t) != -1)
            return true;
        //for(int i=0; i<list.size();i++){
        //    if(list.get(i).compareTo(t)==0) return true;
        //}
        return false;
    }

}
