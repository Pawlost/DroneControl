package connection;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;
import processing.core.PApplet;
import processing.core.PImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.*;

public class UDPVideoServer {

    private DatagramSocket socket;
    private volatile boolean running;
    private volatile String received;
    public static final int PORT = 11111;
    public static final String ADDRESS = "192.168.43.57";
    private PApplet theParent;
    private PImage img;
    private byte[] buf = new byte[65536];

    public UDPVideoServer(PApplet theParent, int w, int h, PImage img) throws SocketException {
        running = true;
        this.theParent = theParent;
        this.img = img;
    }

    public WritableImage run() {
        running = true;
        try {
            socket = new DatagramSocket(PORT, InetAddress.getByName(ADDRESS));
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }

        System.out.println("Listening");
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }

            InetAddress address = packet.getAddress();
            int port = packet.getPort();
            packet = new DatagramPacket(buf, buf.length, address, port);
            try {
                socket.receive(packet);
                System.out.println("recieved");
            } catch (IOException e) {
                e.printStackTrace();
            }
            byte[] data = packet.getData();

            ByteArrayInputStream bais = new ByteArrayInputStream(data);

            img.loadPixels();
            try {
                BufferedImage bimg = ImageIO.read(bais);
                bimg.getRGB(0, 0, img.width, img.height, img.pixels, 0, img.width);
                img.updatePixels();
                socket.close();
                return SwingFXUtils.toFXImage(bimg, null);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
    }

    public boolean isRunning() {
        return running;
    }
}