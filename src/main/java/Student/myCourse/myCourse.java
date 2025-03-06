package Student.myCourse;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class myCourse extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(myCourse.class.getResource("/fxml/Student/courseManage/myCourse.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1500, 720);
        primaryStage.setTitle("Offlearn");
//        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
