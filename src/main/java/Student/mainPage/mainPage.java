package Student.mainPage;

import Student.HomeAndNavigation.Home;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class mainPage extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Home.class.getResource("/fxml/Student/mainPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        primaryStage.setTitle("Offlearn");
        primaryStage.setMaximized(true);
        primaryStage.setFullScreen(false);
        primaryStage.setFullScreenExitHint("");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
