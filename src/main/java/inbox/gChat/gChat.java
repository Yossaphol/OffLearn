package inbox.gChat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class gChat extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(gChat.class.getResource("/fxml/inbox/gChat.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);

        gChatController controller = fxmlLoader.getController();
        controller.setPrimaryStage(primaryStage);

        primaryStage.setTitle("Offlearn Client");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
