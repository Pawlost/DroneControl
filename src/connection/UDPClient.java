package connection;

import java.io.IOException;
import java.net.*;

public class UDPClient {
    private DatagramSocket socket;
    private InetAddress address;

    public static final int PORT = 8889;
    public static final String ADDRESS = "192.168.10.1";


    public UDPClient() throws SocketException, UnknownHostException {
        connect();
    }

    public String sendCommand(String msg) throws IOException {
        byte[] buf = msg.getBytes();
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, PORT);
        socket.send(packet);
        buf = new byte[500];
        packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);
        return new String( packet.getData(), 0, packet.getLength(), "UTF-8");
    }

    public void close() {
        socket.close();
        System.out.println("Disconnecting");
    }

    public void connect() throws SocketException, UnknownHostException {
        socket = new DatagramSocket();
        address = InetAddress.getByName(ADDRESS);
    }
}
