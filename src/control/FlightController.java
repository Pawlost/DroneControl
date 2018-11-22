package control;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class FlightController {

    @FXML
    public Button connect;
    @FXML
    public Button close;
    @FXML
    private Button pass;
    @FXML
    private Text report;

    @FXML
    public void initialize(){
        ArduinoController controller = new ArduinoController();

        connect.setOnAction(event -> {controller.initialize();

            Thread t = new Thread(){
                @Override
                public void run() {
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    while (this.isAlive()) {
                        if (controller.isConnected()) {
                            report.setText(report.getText() + "\n" + controller.getOutput());
                            System.out.println("here");
                        }
                    }
                }
            };
            t.start();
        });
        close.setOnAction(event -> controller.close());
    }
}
