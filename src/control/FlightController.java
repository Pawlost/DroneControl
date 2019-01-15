package control;

import connection.UDPClient;
import connection.UDPVideoServer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

public class FlightController {
    private UDPClient client;
    private UDPVideoServer videoServer;

    @FXML
    private Label report;

    @FXML
    public void initialize(){
        String[] cmd={"D:\\FFmpeg\\bin\\ffmpeg.exe","-i","udp://0.0.0.0:11111", "-f", "sdl", "Hello"};
        try {
            Runtime.getRuntime().exec(cmd);
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
    private void getup(){
        try {
            client = new UDPClient();
//            videoServer = new UDPVideoServer(report);
            command("command");
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }

        //videoServer.run();
        //command("streamon");
        //command("takeoff");
    }

    @FXML
    private void close(){
        client.close();
        videoServer.close();
    }

    private void command(String msg){
        try {
            System.out.println(client.sendCommand(msg));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}