package Student.courseManage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class courseInfo extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(courseInfo.class.getResource("/fxml/Student/courseManage/courseInfo.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 312);
        primaryStage.setTitle("Offlearn");
        primaryStage.setMaximized(true);
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
