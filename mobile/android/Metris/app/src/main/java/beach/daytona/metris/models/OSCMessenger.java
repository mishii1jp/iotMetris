package beach.daytona.metris.models;

import android.util.Log;

import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCPortOut;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import beach.daytona.metris.Utils.Const;

/**
 * Created by sakemotoshinya on 15/07/25.
 */
public class OSCMessenger {

    // This is used to send messages
    private OSCPortOut oscPortOut = null;
    private String address = "/osc/message";

    public OSCMessenger() {
        setupPort();
    }

    private void setupPort() {
        try {
            oscPortOut = new OSCPortOut(InetAddress.getByName(Const.MYIP), Const.MYPORT);
        } catch(UnknownHostException e) {
            Log.d("MemeData", "UnknownHostException: " + e.getMessage());
            e.printStackTrace();
        } catch(Exception e) {
            Log.d("MemeData", "Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void send(final MEMEData data) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                ArrayList<Object> objects = new ArrayList<>();
                objects.add(data.toJsonString());
                OSCMessage message = new OSCMessage(address, objects);
                try {
                    oscPortOut.send(message);
//                    Log.d("MemeData", "data send to node");
                } catch (IOException e) {
                    Log.d("MemeData", "IOException: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }
}
