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
    public void start() {
        command("command");
    }

    @FXML
    private void handleonkeytyped(KeyEvent event){
      if(event.getCode().equals(KeyCode.W)) {
            command("forward 40");
      }else if(event.getCode().equals(KeyCode.S)) {
          command("back 40");
      }else if(event.getCode().equals(KeyCode.A)) {
          command("left 40");
      }else if(event.getCode().equals(KeyCode.D)) {
          command("right 40");
      }else if(event.getCode().equals(KeyCode.E)) {
          command("cw 30");
      }else if(event.getCode().equals(KeyCode.Q)) {
          command("ccw 30");
      }else if(event.getCode().equals(KeyCode.CONTROL)) {
          command("down 25");
      }else if(event.getCode().equals(KeyCode.SHIFT)) {
          command("up 25");
      }
    }

    @FXML
    private void agree(){
        command("flip b");
    }

    @FXML
    private void emergency(){
        command("emergency");
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

    @FXML
    private void getup(){
        command("takeoff");
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

        new Thread(() -> {
            try {
            System.out.println(client.sendCommand(msg));
            Thread.currentThread().join();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        }).start();

    }
}