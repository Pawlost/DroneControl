package control;

import connection.UDPClient;
import connection.UDPVideoServer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import processing.core.PApplet;
import processing.core.PImage;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

public class FlightController{
    private UDPClient client;
    private UDPVideoServer videoServer;

    @FXML
    private Label report;

    @FXML
    private ImageView video;

    @FXML
    public void initialize() {
    }

    @FXML
    private void handleonkeytyped(KeyEvent event){
      if(event.getCode().equals(KeyCode.W)) {
            command("forward 30");
        }
    }

    @FXML
    private void land(){
        command("land");
    }
    @FXML
    private void startVideo() {
        System.out.println("Started Listening");
        new Thread(() -> {
            PApplet applet = new PApplet();
            PImage image = new PImage();
            try
            {
                UDPVideoServer server = new UDPVideoServer(applet, 100, 100, image);
                while (server.isRunning()) {
                   Image send = server.run();
                   updateImage(send);
                   Thread.sleep(10);
                }
            } catch (SocketException | InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private synchronized void updateImage(Image image){
        new Thread(() -> {
            System.out.println("Set Image");
            video.setImage(image);
        }).start();
    }

    @FXML
    private void getup(){
        try {
            client = new UDPClient();
            command("command");
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void close(){
        client.close();
    }

    private void command(String msg){
        try {
            System.out.println(client.sendCommand(msg));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}