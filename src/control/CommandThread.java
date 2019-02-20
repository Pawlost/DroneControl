package control;

import connection.UDPClient;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class CommandThread extends Thread {
    private Text text;
    private InetAddress address;
    private DatagramSocket socket;
    private String msg;
    private boolean scrollable;

    public CommandThread(InetAddress address, DatagramSocket socket, String msg, Text text, boolean scrollable)  {
        this.address = address;
        this.msg = msg;
        this.text = text;
        this.socket = socket;
        this.scrollable = scrollable;
    }

    @Override
    public void run() {
        byte[] buf = msg.getBytes();
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, UDPClient.PORT);
        try {

            if(scrollable) {
                text.setText(text.getText() + "\n lol");
            }else{
                text.setText("lol");
            }


            socket.send(packet);
            buf =new byte[500];
            packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);

            if(scrollable) {
                text.setText(text.getText() + "\n" + new String(packet.getData(),
                        0,packet.getLength(), StandardCharsets.UTF_8));
            }else{
                text.setText(new String(packet.getData(),
                        0,packet.getLength(), StandardCharsets.UTF_8));
            }

            Thread.currentThread().join();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
