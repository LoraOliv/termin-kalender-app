package com.terminkalender;

import com.terminkalender.model.Eintrag;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 512, 512);
        stage.setTitle("Termin-Kalender 1.0");
        stage.setScene(scene);
        stage.show();

        //Eintrag eintrag = new Eintrag("ola", "sup", "13:30", "15:00", "03:07", "Berlin");
        //System.out.println(eintrag);



    }


    public static void main(String[] args) {
        launch();
    }
}