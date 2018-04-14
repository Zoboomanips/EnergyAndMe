package bearsinchairs.energyandme;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Node2 extends AppCompatActivity {

    double power1;
    double energy1;
    double current1;
    double voltage1;
    double charge1;
    int port;
    InetAddress add;
    byte[] buf;
    int nod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void turnOn (View view) throws IOException {
        String msg = "api/turnon/" + nod;
        buf = msg.getBytes();
        DatagramSocket socket = new DatagramSocket();
        DatagramPacket packet = new DatagramPacket(buf, buf.length,
                add, port);
        socket.send(packet);
    }

    public void turnOff (View view) throws IOException {
        String msg = "api/turnoff/" + nod;
        buf = msg.getBytes();
        DatagramSocket socket = new DatagramSocket();
        DatagramPacket packet = new DatagramPacket(buf, buf.length,
                add, port);
        socket.send(packet);
    }

    public double ping(String ins, int node) throws IOException, JSONException {
        String msg = "api/" + ins + "/" + node;
        buf = msg.getBytes();
        DatagramSocket socket = new DatagramSocket();
        DatagramPacket packet = new DatagramPacket(buf, buf.length,
                add, port);
        socket.send(packet);
        buf = new byte[256];
        packet = new DatagramPacket(buf, buf.length);
        socket.setSoTimeout(1000);
        socket.receive(packet);
        String received = new String(packet.getData(), 0, packet.getLength());
        JSONObject reader = new JSONObject(received);
        double val = reader.getDouble("VALUE");
        return val;
    }

}
