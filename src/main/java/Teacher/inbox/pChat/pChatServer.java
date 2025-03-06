package Teacher.inbox.pChat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class pChatServer extends Application {
    @Override
    public void start(Stage stage) throws Exception {
            FXMLLoader fxmlLoader = new FXMLLoader(pChatServer.class.getResource("/fxml/Teacher/pChat.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1280, 720);

            stage.setTitle("Offlearn");
            stage.setScene(scene);
            stage.show();
    }
}
