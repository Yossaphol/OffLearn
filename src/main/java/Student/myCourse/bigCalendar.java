package Student.myCourse;

import Student.HomeAndNavigation.Home;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class bigCalendar extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Home.class.getResource("/fxml/Student/courseManage/customBigCalendar.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("Offlearn Client");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}