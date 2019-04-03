package com.main.jalopy;

public class Interfaces {

    public interface Node {
        void init (int x);
        void connect ();
        void disconnect();
        void updateNodes();
    }

    public interface Broker extends Node{
        void calculateKey();
        Publisher acceptConnection (Publisher pub);
        Subscriber acceptConnection (Subscriber sub);
        void notifyPublisher (String message);
        void pull (Topic t);
    }

    public interface Publisher extends Node{
        void getBrokerList();
        Broker hashTopic (Topic t);
        void push (Topic t, Value v);
        void notifyFailure (Broker br);
    }

    public interface Subscriber extends Node{
        void register (Broker br, Topic t);
        void disconnect (Broker br, Topic t);
        void visualiseData (Topic t, Value v);
    }

    public interface Value {}

    public interface Topic {}

}
