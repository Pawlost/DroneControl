package control;

import connection.UDPClient;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

public class FlightController {
    private UDPClient client;

    @FXML
    public Button connect;
    @FXML
    public Button close;
    @FXML
    private Label report;

    @FXML
    private void initialize() {
        try {
            client = new UDPClient();
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void setConnect(){
        try {
            System.out.println(client.sendEcho("command"));
            System.out.println(client.sendEcho("takeoff"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void land(){
        try {
            System.out.println(client.sendEcho("land"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}