package Teacher.QuizDetail;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class quiz_detail extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Teacher.QuizDetail.quiz_detail.class.getResource("/fxml/Teacher/Quiz/quiz_detail.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);

        primaryStage.setTitle("Offlearn");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
