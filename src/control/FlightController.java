package control;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class FlightController {

    @FXML
    public Button connect;
    @FXML
    public Button close;
    @FXML
    private Button pass;
    @FXML
    private Label report;
    @FXML
    private TextField input;

    private ArduinoController controller = new ArduinoController();

    @FXML
    public void initialize(){
        connect.setOnAction(event -> controller.initialize(report));
        pass.setOnAction(event -> passData());
        close.setOnAction(event -> controller.close());
    }

    private void passData(){
        int angel = Integer.valueOf(input.getText());
        input.setText("");
        try {
            controller.passData(angel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
