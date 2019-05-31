package com.main.jalopy;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;


import com.main.jalopy.Infra.Hashing;
import com.main.jalopy.Infra.NodeInfo;
import com.main.jalopy.Infra.Topic;
import com.main.jalopy.Infra.Value;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.main.jalopy.Infra.Node.b;
import static com.main.jalopy.Infra.Node.info;

public class MainActivity extends AppCompatActivity {
    private Spinner toSend;
    private TextView update;
    private TextView update2;

    private Button btnConnect;

    private HashMap<Integer, NodeInfo> brokerTopics;
    private Map<String,Boolean> currentlySubscribed;
    private Hashing h;

    private int upCount = 1;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_client);

        currentlySubscribed = new HashMap<>();
        initializeData();

        b.add(new NodeInfo(0, "10.0.2.2", 5050));

        brokerTopics = new HashMap<>();
        h = new Hashing();

        toSend = ((Spinner) findViewById(R.id.spinner));
        update = ((TextView) findViewById(R.id.txtUpdate));
        update2 = ((TextView) findViewById(R.id.txtUpdate2));
        update.setTextSize(10);
        update2.setTextSize(10);
        btnConnect = ((Button) findViewById(R.id.btnConnect));


        btnConnect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TA UPCOUNT KAI COUNT EINAI MONO GIA DEBUGGING - TAUTOXRONA UPDATES APO 2 BROKERS GIA 2 TOPICS
                String answer = toSend.getSelectedItem().toString();
                if(!currentlySubscribed.get(answer)) {
                    currentlySubscribed.put(answer,true);
                    AsyncTaskReceiver runner = new AsyncTaskReceiver();
                    runner.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, upCount + "");
                    upCount++;
                }else{
                    currentlySubscribed.put(answer,false);
                }
            }
        });


    }


    private void errorMessage(String message){
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage(message);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert.show();
    }


    private class AsyncTaskReceiver extends AsyncTask<String, String, Integer> {
        private String resp;
        Socket con = null;
        ObjectOutputStream out = null;
        ObjectInputStream in = null;

        private int maxUpdates = 3;
        int hashedTopic;
        String answer = toSend.getSelectedItem().toString();
        int count;
        int result = 0;

        protected Integer doInBackground(String... params) {

            count = Integer.parseInt(params[0]);

            if (brokerTopics.isEmpty()) {
                register(b.get(0), new Topic(answer));
            } else {
                Topic t = new Topic(answer);
                hashedTopic = h.getIntValue(h.hashMD5(t.getBusLine())) % 32;
                register(brokerTopics.get(hashedTopic), t);
            }


            return result;

        }

        protected void onPreExecute() {

        }

        protected void onPostExecute(Integer result) {
            if (result == 1){
                errorMessage("Updates for topic " + answer + " have timed out.");
            }else if(result==2){
                errorMessage("Sensor tracking topic " + answer + " is currently down. Try again later.");
            }
        }

        protected void onProgressUpdate(String... text) {
            if (count == 1)
                update.setText(text[0] + "\n" + text[1]);
            else
                update2.setText(text[0] + "\n" + text[1]);
        }

        public void connect() {
        }

        public void disconnect() {
            try {
                in.close();
                out.close();
                con.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        private void register(NodeInfo broker, Topic t) {
            String response;
            ArrayList<NodeInfo> infos;
            Hashing h = new Hashing();
            int hashedTopic = h.getIntValue(h.hashMD5(t.getBusLine())) % 32;

            try {
                con = new Socket(broker.getIP(), broker.getPort());
                out = new ObjectOutputStream(con.getOutputStream());
                in = new ObjectInputStream(con.getInputStream());

                out.writeUTF("sub");
                out.writeUTF(info.getID() + "");
                out.writeUTF("This is the topic I want to register for: ");
                out.writeObject(t);
                out.flush();


                if (in.readUTF().compareTo("entry found") != 0) {
                    infos = (ArrayList<NodeInfo>) in.readUnshared();

                    for (int i = 0; i < infos.size(); i++)
                        infos.get(i).setIP("10.0.2.2");

                    int id;

                    for (int i = 0; i < infos.size(); i++) {
                        id = infos.get(i).getID();

                        if (id != b.get(0).getID()) { //If it's not the info of the subscriber's first contact
                            b.add(infos.get(i));
                        } else {
                            b.get(i).setFirst(infos.get(i).getFirst());
                            b.get(i).setLast(infos.get(i).getLast());
                        }

                    }

                    setHashMap();

                    System.out.println("Broker information has been acquired.");
                }

                response = in.readUTF();

                System.out.println(con.getInetAddress().getHostAddress() + " > " + response);
                System.out.println();

                if (response.compareTo("Topic located. Standby for updates.") == 0) {
                    while (currentlySubscribed.get(answer)) {
                        response = in.readUTF();
                        System.out.println(con.getInetAddress().getHostAddress() + " > " + response);

                        if (response.compareTo("TIMEOUT") == 0) {
                            currentlySubscribed.put(answer,false);
                            result = 1;
                            return;
                        }

                        if (response.compareTo("Sensor is down.") == 0) {
                            currentlySubscribed.put(answer,false);
                            result = 2;
                            return;
                        }

                        Value v = (Value) in.readUnshared();

                        visualiseData(t, v);

                        out.writeUTF("renew");
                        out.flush();

                    }

                    out.writeUTF("disconnect");
                    out.flush();

                    disconnect();
                } else if (response.compareTo("Sensor is down.") == 0) {
                    disconnect();
                } else {
                    disconnect();
                    register(brokerTopics.get(hashedTopic), t);
                }


            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        private void visualiseData(Topic t, Value v) {
            //Currently a simple print of the Value item
            publishProgress("Update for topic: " + t.getBusLine(), v.toString());
        }

        private void setHashMap() {
            brokerTopics.clear();
            for (int i = 0; i < b.size(); i++) {
                if (b.get(i).getFirst() > b.get(i).getLast()) {
                    for (int j = b.get(i).getFirst(); j < 32; j++)
                        brokerTopics.put(j, b.get(i));
                    for (int j = b.get(i).getLast(); j >= 0; j--)
                        brokerTopics.put(j, b.get(i));
                } else {
                    for (int j = b.get(i).getFirst(); j <= b.get(i).getLast(); j++) {
                        brokerTopics.put(j, b.get(i));
                    }
                }
            }
        }
    }

    public void initializeData(){
        currentlySubscribed.put("021",false);
        currentlySubscribed.put("022",false);
        currentlySubscribed.put("024",false);
        currentlySubscribed.put("025",false);
        currentlySubscribed.put("026",false);
        currentlySubscribed.put("027",false);
        currentlySubscribed.put("032",false);
        currentlySubscribed.put("036",false);
        currentlySubscribed.put("040",false);
        currentlySubscribed.put("046",false);
        currentlySubscribed.put("049",false);
        currentlySubscribed.put("051",false);
        currentlySubscribed.put("054",false);
        currentlySubscribed.put("057",false);
        currentlySubscribed.put("060",false);
        currentlySubscribed.put("1",false);
        currentlySubscribed.put("10",false);
    }


}