package control;

import com.google.common.io.Resources;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private Stage primaryStage;
    private ArduinoController arduinoController;
    private FXMLLoader resources;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        initializeVariables();
        showStage();
        enableController();

        primaryStage.show();
        System.out.println("Application started");
    }

    private void initializeVariables(){
       arduinoController = new ArduinoController();
       resources = new FXMLLoader(Resources.getResource("flightControl.fxml"));
    }

    private void showStage() throws IOException {
        Parent root = resources.load();
        root.getStylesheets().add(Resources.getResource("style.css").toString());

        primaryStage.setTitle("Drone Control");
        primaryStage.setScene(new Scene(root, 1600, 900));

    }

    //Pass variables
    private void enableController(){
        FlightController flightController = resources.getController();
        flightController.setArduinoController(arduinoController);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
