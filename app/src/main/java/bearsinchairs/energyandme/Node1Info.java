package bearsinchairs.energyandme;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonReader;
import android.view.View;

import java.io.IOException;
import java.net.*;
import org.json.*;

public class Node1Info extends AppCompatActivity {

    int power1;
    int port;
    InetAddress add;
    byte[] buf;
    String msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node1_info);
        power1 = 0;
        port = 9931;
        try {
            add = InetAddress.getByName("192.168.1.207");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void turnOn (View view) throws IOException {
        msg = "api/turnon/6";
        buf = msg.getBytes();
        DatagramSocket socket = new DatagramSocket();
        DatagramPacket packet = new DatagramPacket(buf, buf.length,
                add, port);
        socket.send(packet);
    }

    public double ping(String msg, int node) throws IOException, JSONException {
        msg = "api/" + msg + "/" + node;
        buf = msg.getBytes();
        DatagramSocket socket = new DatagramSocket();
        DatagramPacket packet = new DatagramPacket(buf, buf.length,
                add, port);
        socket.send(packet);
        packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);
        String received = new String(packet.getData(), 0, packet.getLength());
        JSONObject reader = new JSONObject(received);

    }
}
