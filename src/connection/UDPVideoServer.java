package connection;

import javafx.scene.image.WritableImage;
import org.jcodec.codecs.h264.H264Decoder;
import org.jcodec.common.model.ColorSpace;
import org.jcodec.common.model.Picture;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class UDPVideoServer {

    private DatagramSocket socket;
    private DatagramChannel channel;
    public static final int PORT = 11111;
    public static final String ADDRESS = "0.0.0.0";

    public UDPVideoServer() {
        try {
            socket = new DatagramSocket(null);
            InetSocketAddress address = new InetSocketAddress(ADDRESS, PORT);
            socket.bind(address);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public WritableImage run() {
        try {
            byte[] buf = new byte[2048];

            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            System.out.println("Listening");
            socket.receive(packet);
            System.out.println("recieved");
            byte[] data = packet.getData();
            System.out.println(data.length);


            ByteBuffer buffer = ByteBuffer.wrap(data, 0, data.length);
            System.out.println(buffer);
            H264Decoder decoder = new H264Decoder();
// assume that sps and pps are set on the decoder
            Picture out = Picture.create(320, 240, ColorSpace.YUV420);
            org.jcodec.codecs.h264.io.model.Frame real = decoder.decodeFrame(buffer, out.getData());

            System.out.println(real);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}