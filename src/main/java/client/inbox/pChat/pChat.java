package client.inbox.pChat;

import client.HomeAndNavigation.Home;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class pChat extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Home.class.getResource("/fxml/client/inbox/pChat.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        primaryStage.setTitle("Offlearn");
        primaryStage.setMaximized(true);
        primaryStage.setFullScreen(true);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
