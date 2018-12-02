package control;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;

import java.io.IOException;

public class FlightController {
    @FXML
    public Button connect;
    @FXML
    public Button close;
    @FXML
    private Label report;

    private ArduinoController controller = new ArduinoController();

    private Action action;

    @FXML
    private void initialize() {
        action = new Action(controller);
    }

    public void addListeners(Scene scene){
        connect.setOnAction(event -> {
            report.setText("");
            controller.initialize(report);
        });

        close.setOnAction(event -> controller.close());
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SHIFT) {
                startThread();
            }
        });

        scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.SHIFT) {
                stopThread();
            }
        });
    }

    public synchronized void startThread(){
        action = new Action(controller);
        action.start();
    }

    public synchronized void stopThread(){
        action.interrupt();
        try {
            action.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            controller.passData(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Action extends Thread {

    private ArduinoController controller;

    public Action(ArduinoController controller){
        this.controller = controller;
    }

    public void run() {
        while (!interrupted()) {
            try {
                controller.passData(240);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}