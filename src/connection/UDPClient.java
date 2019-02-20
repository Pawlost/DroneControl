package connection;

import control.CommandThread;
import javafx.scene.text.Text;

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

    public void sendCommand(String msg, Text text) {
        sendCommand(msg, text, true);
    }

    public void sendCommand(String msg, Text text, boolean scrollable) {
        CommandThread commandThread = new CommandThread(address, socket, msg, text, scrollable);
        commandThread.start();
    }

    public void close() {
        if(!socket.isClosed()) {
            socket.close();
            System.out.println("Disconnecting");
        }
    }

    public void connect() throws SocketException, UnknownHostException {
        socket = new DatagramSocket();
        address = InetAddress.getByName(ADDRESS);
    }
}
