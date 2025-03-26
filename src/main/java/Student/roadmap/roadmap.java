package Student.roadmap;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class roadmap extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    //smth
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(roadmap.class.getResource("/fxml/Student/courseManage/roadmap.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        primaryStage.setTitle("Offlearn");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
