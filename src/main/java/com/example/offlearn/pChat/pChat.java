package com.example.offlearn.pChat;

import com.example.offlearn.pChat.DataBase.StudentsDBConnect;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class pChat extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(pChat.class.getResource("/fxml/pChat.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);

        StudentsDBConnect std = new StudentsDBConnect();
        std.addStudent("students 1");

        primaryStage.setTitle("Offlearn Client");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
