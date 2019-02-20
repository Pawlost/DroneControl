package control;

import connection.UDPClient;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

import java.io.IOException;

public class FlightController{
    private UDPClient client;
    private Thread videostream;

    @FXML
    public Text showBattery;

    @FXML
    public Text showTime;

    @FXML
    public Text showSpeed;

    @FXML
    public volatile Text commandline;

    @FXML
    public TextField speedField;

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
        client.sendCommand("command", commandline);

        new Thread(() -> {
            while (Thread.currentThread().isAlive()) {
                client.sendCommand("battery?", showBattery, false);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            while (Thread.currentThread().isAlive()) {
                client.sendCommand("speed?", showSpeed, false);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            while (Thread.currentThread().isAlive()) {
                client.sendCommand("time?", showTime, false);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @FXML
    private void handleonkeytyped(KeyEvent event){
      if(event.getCode().equals(KeyCode.W)) {
            client.sendCommand("forward 100", commandline);
      }else if(event.getCode().equals(KeyCode.S)) {
          client.sendCommand("back 100", commandline);
      }else if(event.getCode().equals(KeyCode.A)) {
          client.sendCommand("left 100", commandline);
      }else if(event.getCode().equals(KeyCode.D)) {
          client.sendCommand("right 100", commandline);
      }else if(event.getCode().equals(KeyCode.E)) {
          client.sendCommand("cw 30", commandline);
      }else if(event.getCode().equals(KeyCode.Q)) {
          client.sendCommand("ccw 30", commandline);
      }else if(event.getCode().equals(KeyCode.CONTROL)) {
          client.sendCommand("down 40", commandline);
      }else if(event.getCode().equals(KeyCode.SHIFT)) {
          client.sendCommand("up 40", commandline);
      }
    }

    @FXML
    private void frontFlip(){
        client.sendCommand("flip f", commandline);
    }

    @FXML
    private void backFlip(){
        client.sendCommand("flip b", commandline);
    }

    @FXML
    private void leftFlip(){
        client.sendCommand("flip l", commandline);
    }

    @FXML
    private void rightFlip(){
        client.sendCommand("flip r", commandline);
    }

    @FXML
    private void setupSpeed(){
        Integer newSpeed = Integer.valueOf(speedField.getText());
        client.sendCommand("speed "+newSpeed.toString(), commandline);
    }

    @FXML
    private void emergency(){
        client.sendCommand("emergency", commandline);
    }

    @FXML
    private void land(){
        client.sendCommand("land", commandline);
    }

    @FXML
    private void startVideo() {
        System.out.println("Started Listening");
        videostream = new Thread(() -> {
           client.sendCommand("streamon", commandline);
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
        client.sendCommand("takeoff", commandline);
    }

    @FXML
    private void close(){
        try {
            if(videostream != null) {
                client.sendCommand("streamoff", commandline);
                client.close();
                videostream.join();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}