package control;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class FlightController {
    @FXML
    public Button close;
    @FXML
    private Label title;
    @FXML
    private Button addAngle;
    @FXML
    private Label report;

    ArduinoController controller;

    @FXML
    public void initialize(){
        ArduinoController controller = new ArduinoController();

        addAngle.setOnAction(event -> controller.initialize());
        close.setOnAction(event -> controller.close());
        System.out.print("Connected to arduino");
    }

    public void setArduinoController(ArduinoController controller){
        this.controller = controller;
    }
}
