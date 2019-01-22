package control;

import connection.UDPClient;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;

public class FlightController{
    private UDPClient client;
    private Thread videostream;

    @FXML
    private Label report;

    @FXML
    private ImageView video;

    @FXML
    public void initialize() {
        try {
            client = new UDPClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
       videostream = new Thread(() -> {
           command("streamon");
           try {
               Runtime.getRuntime().exec("ffmpeg -i udp://0.0.0.0:11111 -f sdl Tello");
           } catch (IOException e) {
               e.printStackTrace();
           }
       });
        videostream.start();
    }

    private synchronized void updateImage(Image image){
        new Thread(() -> {
            System.out.println("Set Image");
            video.setImage(image);
        }).start();
    }

    @FXML
    private void getup(){
        command("command");
       // command("takeoff");
    }

    @FXML
    private void close(){
        try {
            videostream.join();
            command("streamoff");
            client.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void command(String msg){
        try {
            System.out.println(client.sendCommand(msg));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}