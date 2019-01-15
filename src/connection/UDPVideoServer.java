package connection;

import javafx.scene.control.Label;

import java.io.IOException;
import java.net.*;

public class UDPVideoServer {

    private DatagramSocket socket;
    private volatile boolean running;
    private volatile String received;
    private Label info;
    private static final int port = 11111;
    private byte[] buf = new byte[1024];

    public UDPVideoServer(Label info) throws SocketException {
        try {
            socket = new DatagramSocket(port, InetAddress.getByName("0.0.0.0"));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        this.info = info;
    }

    public void run() {
        new Thread(() -> {
            running = true;
            while (running)
            {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                try {
                    socket.receive(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                packet = new DatagramPacket(buf, buf.length, address, port);
                received = new String(packet.getData(), 0, packet.getLength());
                info.setText(info.getText() + "\n" + received);
            }
        });
    }

    public void close(){
        running = false;
        socket.close();
    }
}
