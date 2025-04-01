
package Student.Offline;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class OfflineTest extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Student/Offline/MainPageOffline.fxml"));
        HBox root = loader.load(); // <- Main container with navbar, searchbar, and content
        Scene scene = new Scene(root, 1280, 720);

        stage.setTitle("Offline MyCourse - Test");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
