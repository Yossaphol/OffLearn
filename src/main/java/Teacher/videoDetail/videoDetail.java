package Teacher.videoDetail;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class videoDetail extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Student.myCourse.myCourse.class.getResource("/fxml/Teacher/statistics/videoDetail.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1440, 720);
        primaryStage.setTitle("Offlearn");
        //primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
