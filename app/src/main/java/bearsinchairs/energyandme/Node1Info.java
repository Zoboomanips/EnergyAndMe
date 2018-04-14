package bearsinchairs.energyandme;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

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
        port = 9331;
        try {
            add = InetAddress.getByName("192.168.1.207");
        } catch (UnknownHostException e) {

        }
    }

    protected void onStart(Bundle savedInstanceState) throws IOException{
        msg = "api/getdcpower/0";
        buf = msg.getBytes();
        DatagramSocket socket = new DatagramSocket();
        DatagramPacket packet = new DatagramPacket(buf, buf.length,
                add, port);
        socket.send(packet);
        

    }
}
