package Student.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class pre_test extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(pre_test.class.getResource("/fxml/Student/test/pre_test.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        primaryStage.setTitle("Offlearn");
        primaryStage.setMaximized(true);
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
