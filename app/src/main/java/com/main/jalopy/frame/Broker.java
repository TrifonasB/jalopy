package com.main.jalopy.frame;


import com.main.jalopy.auxilary.Topic;
import com.main.jalopy.nodePack.Node;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import static com.main.jalopy.auxilary.Hashing.getIntValue;
import static com.main.jalopy.auxilary.Hashing.hashMD5;

public class Broker implements Node {

    private String ip;
    private int port;

    private ServerSocket provider = null;
    private Socket con = null;
    private ObjectOutputStream out = null;
    private ObjectInputStream in = null;

    public Broker(){}

    public Broker(String ip, int port){
        this.ip = ip;
        this.port = port;
    }

    public void init(){
        try {
            this.con = new Socket(InetAddress.getByName("127.0.0.1"),4231);
            this.out = new ObjectOutputStream(con.getOutputStream());
            this.in = new ObjectInputStream(con.getInputStream());
            //System.out.println("This is instance no: " + i);

            out.writeUTF(ip);
            out.writeUTF(port +"");
            out.flush();

            Thread.sleep(5000);

            for(int j=0; j<b.size();j++)
                System.out.println("found an instance");

            System.out.println("Exiting");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void connect(){

        try {
            provider = new ServerSocket(this.port);
            while (true){
                con = provider.accept();
                out = new ObjectOutputStream(con.getOutputStream());
                in = new ObjectInputStream(con.getInputStream());

                out.writeObject ("Connection Succesfull");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            con.close();
            out.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                provider.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateNodes(){}

    public int hashThis (){
        String stringToHash = this.ip + this.port;
        return getIntValue(hashMD5(stringToHash));
    }

    public void calculateKeys(){}

    public Publisher acceptConnection(Publisher pub){return new Publisher();}

    public Subscriber acceptConnection(Subscriber sub){return new Subscriber();}

    public void notifyPublisher(String str){}

    public void pull(Topic t){}

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

}
