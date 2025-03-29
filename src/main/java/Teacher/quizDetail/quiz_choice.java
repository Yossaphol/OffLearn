package Teacher.quizDetail;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import java.io.IOException;

public class quiz_choice extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Teacher/quiz/quiz_choice.fxml"));
        VBox root = fxmlLoader.load();

        // ดึง Controller และอัปเดตข้อมูล
        quiz_choiceController controller = fxmlLoader.getController();
        controller.updateStatistics("ข้อที่ 1 สิ่งที่อยู่ในรูปนี้คืออะไร", "แมว", "ยีราฟ", 35.0, 65.0);

        // สร้าง Scene
        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setTitle("Offlearn");
        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
