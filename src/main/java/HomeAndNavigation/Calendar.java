package HomeAndNavigation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Calendar extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(Home.class.getResource("/fxml/HomePage/CustomCalendar.fxml"));

            Scene scene = new Scene(fxmlLoader.load());
            primaryStage.setTitle("Offlearn Client");
            primaryStage.setScene(scene);
            primaryStage.show();

    }
}
