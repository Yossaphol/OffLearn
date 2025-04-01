package Student.learningPage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class learningPageOffline extends Application {

    private learningPageOfflineController controller;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(learningPageOffline.class.getResource("/fxml/Student/learningPage/learningPageOffline.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        primaryStage.setTitle("Offlearn");
        primaryStage.setMaximized(true);
//        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("");
        primaryStage.setScene(scene);
        primaryStage.show();

        controller = fxmlLoader.getController();
    }

    @Override
    public void stop() {
        System.out.println("App is closing. Cleanup here.");
        if (controller != null && controller.getVideoManager() != null) {
            controller.getVideoManager().disposePlayer();
        }
    }
}
