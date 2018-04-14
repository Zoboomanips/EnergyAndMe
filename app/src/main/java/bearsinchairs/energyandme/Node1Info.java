package bearsinchairs.energyandme;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Node1Info extends AppCompatActivity {

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
        setContentView(R.layout.activity_node1_info);
        port = 9931;
        nod = 6;
        try {
            add = InetAddress.getByName("192.168.1.207");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        update();
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
        String valStu = String.valueOf(reader.get("VALUE"));
        valStu = valStu.substring(1, valStu.length() - 1);
        double val = Double.valueOf(valStu);
        return val;
    }

    public void update(){
        try {
            power1 = ping("getdcpower", nod);
        } catch (IOException e) { } catch (JSONException e) { power1 = 69; }
        TextView powerV = (TextView) findViewById(R.id.powerView);
        powerV.setText("Power: " + power1);
        try {
            energy1 = ping("getdcenergy", nod);
        } catch (IOException e) { } catch (JSONException e) { }
        TextView energyV = (TextView) findViewById(R.id.energyView);
        energyV.setText("Energy: " + energy1);
        try {
            current1 = ping("getdccurrent", nod);
        } catch (IOException e) { } catch (JSONException e) { }
        TextView currV = (TextView) findViewById(R.id.currentView);
        currV.setText("Current: " + current1);
        try {
            charge1 = ping("getdccharge", nod);
        } catch (IOException e) { } catch (JSONException e) { }
        TextView chargeV = (TextView) findViewById(R.id.chargeView);
        chargeV.setText("Charge: " + charge1);
        try {
            voltage1 = ping("getdcvoltage", nod);
        } catch (IOException e) { } catch (JSONException e) { }
        TextView volV = (TextView) findViewById(R.id.voltageView);
        volV.setText("Voltage: " + voltage1);
    }
}
