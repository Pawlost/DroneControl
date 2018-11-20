package control;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.google.common.io.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Resources.getResource("flightControl.fxml"));
        root.getStylesheets().add(Resources.getResource("style.css").toString());
        primaryStage.setTitle("Drone Control");
        primaryStage.setScene(new Scene(root, 1600, 900));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
