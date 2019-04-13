package com.main.jalopy.nodePack;

import java.util.ArrayList;

public interface  Node {
    NodeInfo info = new NodeInfo();
    ArrayList<NodeInfo> b = new ArrayList();

    void init();
    void connect();
    void disconnect();
    void updateNodes();
}
