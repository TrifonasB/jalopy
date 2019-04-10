package com.main.jalopy.auxilary;

import com.main.jalopy.frame.Broker;
import com.main.jalopy.frame.Publisher;
import com.main.jalopy.frame.Subscriber;
import com.main.jalopy.nodePack.Node;

public class MainClass {

    public static void main (String args[]){
        Node pub01 = new Publisher("127.0.0.1", 4321);

        Node broker01 = new Broker("127.0.0.1", 4321);

        Node sub01 = new Subscriber("127.0.0.1", 4321);
    }

}
